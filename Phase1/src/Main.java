import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        FileReaderClass f = new FileReaderClass();
        InvertedIndex in = new InvertedIndex("book");
        for (String s : InvertedIndex.ans) {
            System.out.println(s);
        }
    }
}
