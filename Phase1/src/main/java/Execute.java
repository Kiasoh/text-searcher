import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

/**
 * Execute class
 * get query and sop marks and do search
 */
public class Execute {

    private final ReadPrinciple readPrinciple;
    private final InvertedIndex invertedIndex;
    private final FileScanner fileReader;

    public Execute(ReadPrinciple readPrinciple, FileScanner fileReader){
        this.readPrinciple = readPrinciple;
        this.fileReader = fileReader;
        invertedIndex = fileReader.readFiles(readPrinciple);
    }

    public Set<String> run(String query) throws IOException {
        SearchProcess searchProcess = new SearchProcess(trimQuery(query),
                invertedIndex, readPrinciple, fileReader);
        return searchProcess.getResult();
    }

    public Set<String> run(Scanner in) throws IOException {
        String query = in.nextLine();
        return run(query);
    }

    private String trimQuery(String query) {
        int splitMarkQueryIndex = query.lastIndexOf("/sm");
        if (splitMarkQueryIndex != -1) {
            readPrinciple.setSplitMarks(query.substring(splitMarkQueryIndex + 4));
            return query.substring(0, splitMarkQueryIndex);
        } else
            return query;
    }
}
