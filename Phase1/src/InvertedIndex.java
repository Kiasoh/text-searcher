import java.io.File;
import java.util.*;

public class InvertedIndex {
    private String[] query;
    private ArrayList<String> essentialQuery;
    private ArrayList<String> optionalQuery;
    private ArrayList<String> forbiddenQuery;
    private Set<String> ans;

    public InvertedIndex(String query) {
        essentialQuery = new ArrayList<>();
        optionalQuery = new ArrayList<>();
        forbiddenQuery = new ArrayList<>();
        this.query = query.split(" ");
    }

        public Set<String> getAns(){
            search();
            return ans;
        }

        private void search(){
            for (String word : this.query) {
                word = FileReaderClass.normalize(word);
                if(word.equals(""))
                    continue;
                if (word.charAt(0) == '+')
                    optionalQuery.add(word.substring(1));
                else if (word.charAt(0) == '-')
                    forbiddenQuery.add(word.substring(1));
                else
                    essentialQuery.add(word);
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
                for(String word : essentialQuery) {
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
                for(String word : optionalQuery){
                    if(map.get(word) == null)
                        continue;
                    else if(map.get(word).contains(doc)) {
                        flag = true;
                        break;
                    }
                }
                if(!flag && optionalQuery.size()!=0){
                    it.remove();
                }
            }

            it = ans.iterator();

            while (it.hasNext()){

                String doc = it.next();
                for(String word : forbiddenQuery){
                    if(map.get(word) != null && map.get(word).contains(doc)) {
                        it.remove();
                        break;
                    }
                }
            }
        }
    }

