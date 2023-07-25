import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        FileScanner fileReader = new TxtFileReader("./books/");
        Chainsaw chainsaw = new Chainsaw(2,10);
        ReadPrinciple readPrinciple = ReadPrinciple.builder().useNGram(false).splitMarks("\\n\\r\\s-")
                .normalization(new Stemmer()).chainsaw(chainsaw).build();
        Execute execute = new Execute(readPrinciple, fileReader);

        //use from code

        List<Document> answer = execute.run("kiarash -dog");
        for (int i = 0; i <answer.size() ; i++) {

            System.out.println(i+1 + ". " + answer.get(i).getName() + " " + (-1 * answer.get(i).getScore()));
        }
//        System.out.print();

        //use from console
//        Scanner in = new Scanner(System.in);
//        System.out.println("\u001B[36mwrite the query then split marks\nExample: get help +illness +disease -cough /sm  ,# !.\nto quit type :q");
//        System.out.println(execute.run(in).toString().replaceAll(",","\n"));
//        Stemmer stemmer = new Stemmer();
//        System.out.println(stemmer.normalize("gathered."));
    }
}
