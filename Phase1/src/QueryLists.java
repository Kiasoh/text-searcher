import java.util.ArrayList;

public class QueryLists {
    public ArrayList<String> essential;
    public ArrayList<String> optional;
    public ArrayList<String> forbidden;
    public QueryLists()
    {
        essential = new ArrayList<>();
        optional = new ArrayList<>();
        forbidden = new ArrayList<>();
    }
    public void Catagorization(String[] query)
    {
        for (String word : query) {
            word = WordManipulation.normalize(word);
            if(word.equals(""))
                continue;
            if (word.charAt(0) == '+')
                optional.add(word.substring(1));
            else if (word.charAt(0) == '-')
                forbidden.add(word.substring(1));
            else
                essential.add(word);
        }
    }
}
