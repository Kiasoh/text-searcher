import java.util.ArrayList;

public class QueryLists {
    private ArrayList<String> essential;
    private ArrayList<String> optional;
    private ArrayList<String> forbidden;
    public QueryLists()
    {
        essential = new ArrayList<>();
        optional = new ArrayList<>();
        forbidden = new ArrayList<>();
    }
    public void Categorization(String[] query)
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

    public ArrayList<String> getEssential() {
        return essential;
    }

    public ArrayList<String> getForbidden() {
        return forbidden;
    }

    public ArrayList<String> getOptional() {
        return optional;
    }
}