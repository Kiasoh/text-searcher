import java.util.ArrayList;

/**
 * categorize query based on their prefixes( '+' , '-' ro nothing)
 */
public class QueryLists {

    private final ArrayList<String> essential;
    private final ArrayList<String> optional;
    private final ArrayList<String> forbidden;

    public QueryLists() {
        essential = new ArrayList<>();
        optional = new ArrayList<>();
        forbidden = new ArrayList<>();
    }

    public void categorization(String[] query, ReadPrinciple readPrinciple) {
        for (String word : query) {
            if(word.equals(""))
                continue;
            if (word.charAt(0) == '+')
                optional.add(readPrinciple.getNormalization().normalize(word.substring(1)));
            else if (word.charAt(0) == '-')
                forbidden.add(readPrinciple.getNormalization().normalize(word.substring(1)));
            else
                essential.add(readPrinciple.getNormalization().normalize(word));
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
