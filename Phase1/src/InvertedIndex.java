import java.io.File;
import java.util.*;

public class InvertedIndex {
    Navigator navigator;
    private QueryLists queryLists = new QueryLists();
    private Set<String> ans;

    public InvertedIndex(String query ,Navigator navigator) {
        this.navigator = navigator;
        queryLists.Catagorization(query.split(" "));
    }
        public Set<String> getAns(){
            Search();
            return ans;
        }
        public boolean IsThereFiles (String[] fileNames)
        {
            return (fileNames == null || fileNames.length == 0);
        }

        private Iterator<String> SetIterator()
        {
            return ans.iterator();
        }
        private void CheckEssentials()
        {
            Iterator<String> it = SetIterator();
            while (it.hasNext()){
                String doc = it.next();
                for(String word : queryLists.essential) {
                    if (navigator.map.get(word) == null || !navigator.map.get(word).contains(doc)){
                        it.remove();
                        break;
                    }
                }
            }
        }
        private void CheckOptional()
        {
            Iterator<String> it = SetIterator();
            boolean flag;
            while (it.hasNext()){
                flag = false;
                String doc = it.next();
                for(String word : queryLists.optional){
                    if(navigator.map.get(word) == null)
                        continue;
                    else if(navigator.map.get(word).contains(doc)) {
                        flag = true;
                        break;
                    }
                }
                if(!flag && queryLists.optional.size()!=0){
                    it.remove();
                }
            }
        }
        private void CheckForbidden()
        {
            Iterator<String> it = SetIterator();
            while (it.hasNext()){

                String doc = it.next();
                for(String word : queryLists.forbidden){
                    if(navigator.map.get(word) != null && navigator.map.get(word).contains(doc)) {
                        it.remove();
                        break;
                    }
                }
            }
        }
        private void Search(){
            String[] fileNames = FileReaderClass.GetFilesName();
            if(IsThereFiles(fileNames))
                ans.add("THERE IS NO FILE");
            ans = new HashSet<>(Arrays.asList(fileNames));
            CheckEssentials();
            CheckOptional();
            CheckForbidden();
        }
    }

