import java.io.File;
import java.util.*;

public class InvertedIndex {
    private String query;
    private ArrayList<String> essentialQuery;
    private ArrayList<String> optionalQuery;
    private ArrayList<String> forbiddenQuery;
    private Set<String> ans;

    public InvertedIndex(String query) {
        essentialQuery = new ArrayList<>();
        optionalQuery = new ArrayList<>();
        forbiddenQuery = new ArrayList<>();
        this.query = query;
        String[] temp;
        temp = query.split(" ");
        for (String word : temp) {
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
        ans =new HashSet<>(Arrays.asList(fileNames));
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
        //System.out.println(ans);
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

        public Set<String> getAns(){
            return ans;
        }
    }

