import java.util.ArrayList;
import java.util.Arrays;

/**
 * split and normalization
 */
public class ReadPrinciple {
    private ArrayList<String> splitMarks;
    public INormalize normalization;


    public ReadPrinciple(){
        splitMarks = new ArrayList<>();
        normalization = new Stemmer();
    }

    public char[] prepareForScan(String line) {
        line = line.toLowerCase();
        return line.toCharArray();
    }
    public boolean issplitMark(Character c){
        if(splitMarks.isEmpty())
            return isAlphabetic(c);
        return !splitMarks.contains(c.toString());
    }
    private boolean isAlphabetic(char c) {
        return c >= 'a' && c <= 'z';
    }
    public void setSplitMarks(String splitMarks) {
        this.splitMarks = new ArrayList<>(Arrays.asList(splitMarks.split("")));
    }
}
