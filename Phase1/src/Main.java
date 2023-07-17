import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        FileReaderClass f = new FileReaderClass();
        f.createMap();
        Scanner in = new Scanner(System.in);
        while (true){
            String query = in.nextLine();
            InvertedIndex invertedIndex = new InvertedIndex(query);
            for (String ss : invertedIndex.getAns()) {
                System.out.println(ss);
            }
        }

    }
}
