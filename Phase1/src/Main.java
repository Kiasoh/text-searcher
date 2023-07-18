import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static void getInput(Scanner in, FileReaderClass fileReaderClass) throws IOException {
        do {
            String query = in.nextLine();
            if(query.equals(":q"))
                return;
            ReadPrinciple readPrinciple = new ReadPrinciple();
            SearchProcess searchProcess = new SearchProcess(trimQuery(query, readPrinciple) , fileReaderClass.createMap(readPrinciple));
            for (String s : searchProcess.getAns()) {
                System.out.println("\u001B[32m" + s);
            }
        }while (true);
    }

    private static String trimQuery(String query, ReadPrinciple readPrinciple){
        int temp = query.lastIndexOf("/sm");
        if(temp > 0){
            readPrinciple.setSplitMarks(query.substring(temp+4));
            return query.substring(0,temp);
        }
        else
            return query;
    }

    public static void main(String[] args) throws IOException {
        FileReaderClass f = new FileReaderClass();
        Scanner in = new Scanner(System.in);
        System.out.println("\u001B[36mwrite the query then split marks\nExample: get help +illness +disease -cough /sm  ,# !.\nto quit type :q");
        getInput(in ,f);
    }
}
