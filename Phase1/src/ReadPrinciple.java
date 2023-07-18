import java.util.ArrayList;
import java.util.Arrays;

public class ReadPrinciple {
    private ArrayList<String> splitMarks;

    public ReadPrinciple(){
        splitMarks = new ArrayList<>();
    }

    public char[] prepareForScan(String Line)
    {
        Line = Line.toLowerCase();
        Line += '/';
        return Line.toCharArray();
    }
    public boolean splitBy(Character c){
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
