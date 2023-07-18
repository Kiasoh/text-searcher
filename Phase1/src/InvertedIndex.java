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


    public void addToMapByLine(String Line , String fileName)
    {
        String word = "";
        for (Character c : readPrinciple.prepareForScan(Line)) {
            if (readPrinciple.splitBy(c)) {
                word += c.toString();
                continue;
            }
            else {
                WordManipulation wordManipulation = new WordManipulation();
                word = wordManipulation.normalize(word);
            }
            if (map.containsKey(word)) {
                if (map.get(word).contains(fileName)) {
                    word = "";
                    continue;
                }
                else {
                    map.get(word).add(fileName);
                }
            }
            else {
                map.put(word, new ArrayList<String>(List.of(fileName)));
            }
            word = "";
        }
    }
}
