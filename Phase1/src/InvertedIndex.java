import java.io.File;
import java.util.*;

public class InvertedIndex {
    private String[] query;
    private QueryLists queryLists = new QueryLists();
    private Set<String> ans;

    public InvertedIndex(String query) {

        this.query = query.split(" ");
    }
        public Set<String> getAns(){
            Search();
            return ans;
        }

        private void Search(){
            for (String word : this.query) {
                word = WordManipulation.normalize(word);
                if(word.equals(""))
                    continue;
                if (word.charAt(0) == '+')
                    queryLists.optional.add(word.substring(1));
                else if (word.charAt(0) == '-')
                    queryLists.forbidden.add(word.substring(1));
                else
                    queryLists.essential.add(word);
            }
            HashMap<String , ArrayList<String>> map = FileReaderClass.map;
            File file = new File ("./books/");
            String[] fileNames = file.list();
            if(fileNames == null || fileNames.length == 0)
                ans.add("THERE IS NO FILE");
            ans = new HashSet<>(Arrays.asList(fileNames));
            Iterator<String> it = ans.iterator();
            while (it.hasNext()){
                String doc = it.next();
                for(String word : queryLists.essential) {
                    if (map.get(word) == null || !map.get(word).contains(doc)){
                        it.remove();
                        break;
                    }
                }
            }
            boolean flag;
            it = ans.iterator();
            while (it.hasNext()){
                flag = false;
                String doc = it.next();
                for(String word : queryLists.optional){
                    if(map.get(word) == null)
                        continue;
                    else if(map.get(word).contains(doc)) {
                        flag = true;
                        break;
                    }
                }
                if(!flag && queryLists.optional.size()!=0){
                    it.remove();
                }
            }

            it = ans.iterator();

            while (it.hasNext()){

                String doc = it.next();
                for(String word : queryLists.forbidden){
                    if(map.get(word) != null && map.get(word).contains(doc)) {
                        it.remove();
                        break;
                    }
                }
            }
        }
    }

