import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static void GetInput(Scanner in , InvertedIndex invertedIndex)
    {
        String query = "";
        while (!query.equals(":q")){
            query = in.nextLine();
            SearchProcess searchProcess = new SearchProcess(query , invertedIndex);
            for (String s : searchProcess.getAns()) {
                System.out.println(s);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        FileReaderClass f = new FileReaderClass(new InvertedIndex());
        Scanner in = new Scanner(System.in);
        GetInput(in , f.createMap());
    }
}
