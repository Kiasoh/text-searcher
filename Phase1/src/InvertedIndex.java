import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * create the hashmap in which each word is mapped to a list of document names that the word is in that
 */
public class InvertedIndex {
    public HashMap<String, HashSet<String>> map;
    private ReadPrinciple readPrinciple;

    public InvertedIndex(ReadPrinciple readPrinciple)
    {
        this.readPrinciple = readPrinciple;
        map = new HashMap<>();
    }


    public void addToMapByLine(String line , String fileName)
    {
        String word = "";
        int count = 0;
        for (Character c : readPrinciple.prepareForScan(line)) {
            if (readPrinciple.splitBy(c)) {
                word += c.toString();
                count++;
                if(count == line.length())
                    addToMap(word,fileName);
                continue;
            }

            addToMap(word, fileName);
            word = "";
        }
    }

    private void addToMap(String word, String fileName){
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
