import java.io.IOException;
import java.util.Scanner;

public class Execute {
    public static void run(String query, FileReaderClass fileReaderClass, ReadPrinciple readPrinciple) throws IOException {
        if (query.equals(":q"))
            return;
        SearchProcess searchProcess = new SearchProcess(trimQuery(query, readPrinciple), fileReaderClass.createMap(readPrinciple), readPrinciple);
        for (String s : searchProcess.getAns()) {
            System.out.println("\u001B[32m" + s);
        }
    }
    public static void run(Scanner in, FileReaderClass fileReaderClass , ReadPrinciple readPrinciple) throws IOException {
        String query = in.nextLine();
        run(query,fileReaderClass , readPrinciple);
    }
    private static String trimQuery(String query, ReadPrinciple readPrinciple) {
        int temp = query.lastIndexOf("/sm");
        if (temp > 0) {
            readPrinciple.setSplitMarks(query.substring(temp + 4));
            return query.substring(0, temp);
        } else
            return query;
    }
}
