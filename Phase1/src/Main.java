import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        IFileReader fileReader = new TxtFileReader("./books/");

        //use from code
        ReadPrinciple readPrinciple = new ReadPrinciple();
        readPrinciple.setSplitMarks(" ");
        readPrinciple.normalization = new Stemmer();
        System.out.print(Execute.run("kiarash" , readPrinciple, fileReader).toString().replaceAll(",","\n"));

        //use from console
//        Scanner in = new Scanner(System.in);
//        System.out.println("\u001B[36mwrite the query then split marks\nExample: get help +illness +disease -cough /sm  ,# !.\nto quit type :q");
//        ReadPrinciple readPrinciple = new ReadPrinciple();
//        System.out.println(Execute.run(in, readPrinciple, fileReader).toString().replaceAll(",","\n"));
    }
}
