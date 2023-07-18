import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvertedIndex {
    public HashMap<String, ArrayList<String>> map;
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
            count++;
            if (readPrinciple.splitBy(c)) {
                if(count == line.length())
                    addToMap(word,fileName);
                word += c.toString();
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
            map.put(word, new ArrayList<String>(List.of(fileName)));
    }
}
