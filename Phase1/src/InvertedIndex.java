import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * create the hashmap in which each word is mapped to a list of document names that the word is in that
 */
public class InvertedIndex {
    public HashMap<String, HashSet<String>> map;
    private final ReadPrinciple readPrinciple;

    public ReadPrinciple getReadPrinciple() {
        return readPrinciple;
    }

    public InvertedIndex(ReadPrinciple readPrinciple)
    {
        this.readPrinciple = readPrinciple;
        map = new HashMap<>();
    }
    public void addToMap(String word, String fileName){
        word = readPrinciple.normalization.normalize(word);
        if (map.containsKey(word)) {
            if (map.get(word).contains(fileName))
                return;
            map.get(word).add(fileName);
        }
        else
            map.put(word, new HashSet<>(List.of(fileName)));
    }
}
