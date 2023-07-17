import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static void GetInput(Scanner in)
    {
        String query = "";
        while (query != ":q"){
            query = in.nextLine();
            InvertedIndex invertedIndex = new InvertedIndex(query);
            for (String s : invertedIndex.getAns()) {
                System.out.println(s);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        FileReaderClass f = new FileReaderClass(new Map());
        f.createMap();
        Scanner in = new Scanner(System.in);
        GetInput(in);
    }
}
