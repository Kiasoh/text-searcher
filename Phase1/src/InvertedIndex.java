import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InvertedIndex {
    private String query;
    private ArrayList<String> essentialQuery;
    private ArrayList<String> optionalQuery;
    private ArrayList<String> forbiddenQuery;

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
        Set<String> ans = new HashSet<>();
        for(String word : essentialQuery){
            ans.addAll(map.get(word));
        }
        for(String word : optionalQuery) {

        }
    }

}
