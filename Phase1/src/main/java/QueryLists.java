import lombok.Getter;
import java.util.ArrayList;

/**
 * This class represents a query categorization based on their prefixes.
 * The prefixes can be '+' for optional queries, '-' for forbidden queries, or no prefix for essential queries.
 */
@Getter
public class QueryLists {

    private final ArrayList<String> essential;
    private final ArrayList<String> optional;
    private final ArrayList<String> forbidden;

    /**
     * Constructs an instance of QueryLists.
     * Initializes the lists of essential, optional, and forbidden queries.
     */
    public QueryLists() {
        essential = new ArrayList<>();
        optional = new ArrayList<>();
        forbidden = new ArrayList<>();
    }

    /**
     * Categorizes the given array of queries based on their prefixes.
     * Adds each query to the appropriate list (essential, optional, or forbidden).
     * @param query the array of queries to be categorized
     * @param readPrinciple the ReadPrinciple object used for normalization
     */
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
}
