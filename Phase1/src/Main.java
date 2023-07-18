import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static void GetInput(Scanner in, FileReaderClass fileReaderClass) throws IOException {
        String query = "";
        while (!query.equals(":q")){
            query = in.nextLine();
            int temp = query.lastIndexOf("/sm");
            ReadPrinciple readPrinciple = new ReadPrinciple();
            if(temp > 0){
                readPrinciple.setSplitMarks(query.substring(temp+4));
            }
            SearchProcess searchProcess = new SearchProcess(query , fileReaderClass.createMap(readPrinciple));
            for (String s : searchProcess.getAns()) {
                System.out.println(s);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        FileReaderClass f = new FileReaderClass();
        Scanner in = new Scanner(System.in);
        GetInput(in ,f);
    }
}
