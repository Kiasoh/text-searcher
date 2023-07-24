import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FileScanner fileReader = new TxtFileReader("./books/");
        ReadPrinciple readPrinciple = ReadPrinciple.builder().build();
        readPrinciple.setSplitMarks(" -");
        Execute execute = new Execute(readPrinciple, fileReader);

        //use from code

        readPrinciple.normalization = new Stemmer();
        System.out.print(execute.run("word dog").toString().replaceAll(",","\n"));

        //use from console
//        Scanner in = new Scanner(System.in);
//        System.out.println("\u001B[36mwrite the query then split marks\nExample: get help +illness +disease -cough /sm  ,# !.\nto quit type :q");
//        System.out.println(execute.run(in).toString().replaceAll(",","\n"));
//        Stemmer stemmer = new Stemmer();
//        System.out.println(stemmer.normalize("gathered."));
    }
}
