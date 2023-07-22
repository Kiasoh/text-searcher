import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

/**
 * Execute class
 * get query and sop marks and do search
 */
public class Execute {
    public static Set<String> run(String query, ReadPrinciple readPrinciple, IFileReader fileReader) throws IOException {
        SearchProcess searchProcess = new SearchProcess(trimQuery(query, readPrinciple),
                fileReader.createMap(readPrinciple), readPrinciple, fileReader);
        return searchProcess.getAns();
    }
    public static Set<String> run(Scanner in, ReadPrinciple readPrinciple, IFileReader fileReader) throws IOException {
        String query = in.nextLine();
        return run(query, readPrinciple, fileReader);
    }
    private static String trimQuery(String query, ReadPrinciple readPrinciple) {
        int splitMarkQueryIndex = query.lastIndexOf("/sm");
        if (splitMarkQueryIndex != -1) {
            readPrinciple.setSplitMarks(query.substring(splitMarkQueryIndex + 4));
            return query.substring(0, splitMarkQueryIndex);
        } else
            return query;
    }
}
