import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        ArrayList<FileScanner> fileReaders = new ArrayList<>();
        ArrayList<Guard> guards = new ArrayList<>();

        Chainsaw chainsaw = new Chainsaw(3,5);
        Normalizable normalizable = new Stemmer();
        String splitMarks = "\\r\\n\\s-,.";
        boolean useNGram = false;

        ReadPrinciple readPrinciple = ReadPrinciple.builder()
                .useNGram(useNGram).splitMarks(splitMarks)
                .normalization(normalizable).chainsaw(chainsaw)
                .build();

        Guard guard = new GateKeeper(readPrinciple);
        guards.add(guard);

        FileScanner fileReader = new TxtFileReader("./temp_tests/", guard);
        fileReaders.add(fileReader);

        //use from code
//        Execute execute = new Execute(guards, fileReaders);
//        List<ScoreHolder> answer = execute.run("kimia");
//        for (int i = 0; i <answer.size() ; i++) {
//            System.out.println("\u001B[32m" + (i+1) + ". \u001B[34m" + answer.get(i).getDocument().getName()
//                    + " \u001B[31mscore:" + String.format("%.2f", (answer.get(i).getScore())));
//        }

        //use from console
        System.out.println("\u001B[36mwrite the query then split marks\nExample: get help +illness +disease -cough /sm \\s,#\\r\\n!.");
        String query = Execute.giveQueryFromConsole(new Scanner(System.in), guards);
        Execute execute = new Execute(guards, fileReaders);
        List<ScoreHolder> answer = execute.run(query);
        for (int i = 0; i <answer.size() ; i++) {
            System.out.println("\u001B[32m" + (i+1) + ". \u001B[34m" + answer.get(i).getDocument().getName()
                    + " \u001B[31mscore:" + String.format("%.2f", (answer.get(i).getScore())));
        }
    }
}
