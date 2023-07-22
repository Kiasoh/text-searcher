import java.util.ArrayList;
import java.util.Arrays;

/**
 * split and normalization
 */
public class ReadPrinciple {

    private String splitMarks;
    public Normalizable normalization;

    public ReadPrinciple(){
        normalization = new Stemmer();
    }

    public boolean isSplitMark(Character c){
        if(splitMarks.equals(""))
            return isAlphabetic(c);
        return !splitMarks.contains(c.toString());
    }

    private boolean isAlphabetic(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public void setSplitMarks(String splitMarks) {
        this.splitMarks = splitMarks;
    }

    public String getSplitMarks() {
        return splitMarks;
    }
}
