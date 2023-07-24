import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * create the hashmap in which each word is mapped to a list of document names that the word is in that
 */
@Getter
public class InvertedIndex {

    public HashMap<String, HashSet<String>> map;
    private final ReadPrinciple readPrinciple;

    public InvertedIndex(ReadPrinciple readPrinciple) {
        this.readPrinciple = readPrinciple;
        map = new HashMap<>();
    }

    public void addToMap(String word, String fileName){
        word = readPrinciple.getNormalization().normalize(word);
        enterToMap(word, fileName);
    }

    public void addToMap(List<String> words, String fileName){
        words.forEach(word ->enterToMap(word,fileName));
    }

    private void enterToMap(String word, String fileName){
        if (map.containsKey(word))
            map.get(word).add(fileName);
        else
            map.put(word, new HashSet<>(List.of(fileName)));
    }
}
