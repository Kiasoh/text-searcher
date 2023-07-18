import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Navigator {
    public HashMap<String, ArrayList<String>> map;
    public Navigator ()
    {
        map = new HashMap<>();
    }
    public char[] PrepareForScan(String Line)
    {
        Line = Line.toLowerCase();
        Line += '/';
        return Line.toCharArray();
    }
    private boolean isAlphabetic(char c) {
        return c >= 'a' && c <= 'z';
    }
    public void AddToMapByLine(String Line , String fileName)
    {
        String word = "";
        for (Character c : PrepareForScan(Line)) {
            if (isAlphabetic(c)) {
                word += c.toString();
                continue;
            }
            else
                word = WordManipulation.normalize(word);
            if (map.containsKey(word)) {
                ;
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
