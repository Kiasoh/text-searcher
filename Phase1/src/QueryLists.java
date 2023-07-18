import java.util.ArrayList;

/**
 * categorize query based on their prefixes( '+' , '-' ro nothing)
 */
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
    public void categorization(String[] query, ReadPrinciple readPrinciple)
    {
        for (String word : query) {
            word = readPrinciple.normalization.normalize(word);
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
