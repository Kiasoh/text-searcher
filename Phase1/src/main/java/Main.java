import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        ArrayList<FileScanner> fileReaders = new ArrayList<>();
        ArrayList<Guard> guards = new ArrayList<>();
        Chainsaw chainsaw = new Chainsaw(3,5);
        ReadPrinciple readPrinciple = ReadPrinciple.builder()
                .useNGram(false).splitMarks("\\r\\n\\s-,.")
                .normalization(new Stemmer()).chainsaw(chainsaw)
                .build();

        Guard guard = new GateKeeper(readPrinciple);
        guards.add(guard);
        FileScanner fileReader = new TxtFileReader("./books/", guard);
        fileReaders.add(fileReader);
        Execute execute = new Execute(guards, fileReaders);

        //use from code

        List<DocumentInfo> answer = execute.run("kiarash -dog +data");
        for (int i = 0; i <answer.size() ; i++) {

//            System.out.println(i+1 + ". " + answer.get(i).getDocument().getName() + " " + (-1 * answer.get(i).getScore()));
            System.out.println(i+1 + ". " + answer.get(i).getDocument().getName());
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
