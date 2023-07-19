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
//        System.out.println(Execute.run(in, readPrinciple).toString().replaceAll(",","\n"));
        ReadPrinciple readPrinciple = new ReadPrinciple();
        readPrinciple.setSplitMarks(" ");
        readPrinciple.normalization = new Stemmer();



        long start1 = System.nanoTime();
        System.out.print(Execute.run("learn" , readPrinciple).toString().replaceAll(",","\n"));
        long end1 = System.nanoTime();
        System.out.println(end1-start1);
    }
}
