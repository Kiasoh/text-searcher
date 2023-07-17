import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        FileReaderClass f = new FileReaderClass();
        Stemmer stemmer = new Stemmer();
        Scanner in = new Scanner(System.in);
        String query = in.nextLine();
        char[] queryCharArray = query.toCharArray();
        stemmer.add(queryCharArray, queryCharArray.length);
        stemmer.stem();
        InvertedIndex invertedIndex = new InvertedIndex(stemmer.toString());
        for (String ss : invertedIndex.getAns()) {
            System.out.println(ss);
        }
    }
}
