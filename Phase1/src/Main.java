import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        FileReaderClass.setPath("./books/");
        FileReaderClass.addValidFormat(".txt");
//        Scanner in = new Scanner(System.in);
//        System.out.println("\u001B[36mwrite the query then split marks\nExample: get help +illness +disease -cough /sm  ,# !.\nto quit type :q");
//        ReadPrinciple readPrinciple = new ReadPrinciple();
//        printResult(Execute.run(in, readPrinciple));
        ReadPrinciple readPrinciple = new ReadPrinciple();
        readPrinciple.setSplitMarks(" ");
        readPrinciple.normalization = new Stemmer();
        printResult(Execute.run("learn +kiarash +small -dog" , readPrinciple));
    }

    private static void printResult(Set<String> res){
        System.out.println(res.toString().replaceAll(",","\n"));
    }
}
