import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
//        Scanner in = new Scanner(System.in);
//        System.out.println("\u001B[36mwrite the query then split marks\nExample: get help +illness +disease -cough /sm  ,# !.\nto quit type :q");
//        ReadPrinciple readPrinciple = new ReadPrinciple();
//        Execute.run(in,new FileReaderClass() , readPrinciple);
        ReadPrinciple readPrinciple = new ReadPrinciple();
        readPrinciple.setSplitMarks(" s");
        readPrinciple.normalization = new Stemmer();
        Execute execute = new Execute();
        Execute.run("mall" , new FileReaderClass() , readPrinciple);
    }
}
