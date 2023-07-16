import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        HashMap<String , ArrayList<String >> map = FileReaderClass.map;
        for (String s: map.keySet()) {
            System.out.println( s);
            for (String ss : map.get(s)) {
                System.out.println(ss);
            }
        }
    }
}
