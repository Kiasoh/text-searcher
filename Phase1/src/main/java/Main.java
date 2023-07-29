import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the main entry point for executing a search algorithm on text documents.
 * It uses various components such as Chainsaw, Normalizable, ReadPrinciple,
 * and Guard to process and analyze the input files.
 *
 * to customize your test :
 * set min and max length for edge n-gram in line 32
 * set your normalization in line 33
 * set your split marks for tokenizing texts in line 34
 * set your useNGram true or false in line 35
 *
 * after building readPrinciple you can again set different values for those
 * and build a new readPrinciple a new Guard and add it to guards
 *
 * set the path of your documents in line 45
 * you can also make another FileScanners and add them to fileReaders
 *
 * Now it's time to search :)
 * if u want to run inside the code just write your query in line 50, and it's done
 * if u want to give your query and splitMarks from console comment lines 49-54 and uncomment lines 57-64
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ArrayList<FileScanner> fileReaders = new ArrayList<>();
        ArrayList<Guard> guards = new ArrayList<>();

        Chainsaw chainsaw = new Chainsaw(3,5);
        Normalization normalization = new Stemmer();
        String splitMarks = "\\r\\n\\s-,.";
        boolean useNGram = false;

        ReadPrinciple readPrinciple = ReadPrinciple.builder()
                .useNGram(useNGram).splitMarks(splitMarks)
                .normalization(normalization).chainsaw(chainsaw)
                .build();

        Guard guard = new GateKeeper(readPrinciple);
        guards.add(guard);

        FileScanner fileReader = new TxtFileReader("./books/", guard);
        fileReaders.add(fileReader);

        //use from code
        Execute execute = new Execute(guards, fileReaders);
        List<ScoreHolder> answer = execute.run("code");
        for (int i = 0; i <answer.size() ; i++) {
            System.out.println("\u001B[32m" + (i+1) + ". \u001B[34m" + answer.get(i).getDocument().getName()
                    + " \u001B[31mscore:" + String.format("%.2f", (answer.get(i).getScore())));
        }

        //use from console
//        System.out.println("\u001B[36mwrite the query then split marks\nExample: get help +illness +disease -cough /sm \\s,#\\r\\n!.");
//        String query = Execute.giveQueryFromConsole(new Scanner(System.in), guards);
//        Execute execute = new Execute(guards, fileReaders);
//        List<ScoreHolder> answer = execute.run(query);
//        for (int i = 0; i <answer.size() ; i++) {
//            System.out.println("\u001B[32m" + (i+1) + ". \u001B[34m" + answer.get(i).getDocument().getName()
//                    + " \u001B[31mscore:" + String.format("%.2f", (answer.get(i).getScore())));
//        }
    }
}
