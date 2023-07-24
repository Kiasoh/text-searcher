import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Execute class
 * get query and sop marks and do search
 */
public class Execute{

    private final ReadPrinciple readPrinciple;
    private final InvertedIndex invertedIndex;
    private final FileScanner fileReader;

    public Execute(ReadPrinciple readPrinciple, FileScanner fileReader){
        this.readPrinciple = readPrinciple;
        this.fileReader = fileReader;
        invertedIndex = fileReader.readFiles(readPrinciple);
    }

    public List<Document> run(String query) throws IOException {
        SearchProcess searchProcess = new SearchProcess(trimQuery(query),
                invertedIndex, readPrinciple, fileReader);
        return sort(searchProcess.getResult());
    }

    public List<Document> run(Scanner in) throws IOException {
        String query = in.nextLine();
        return run(query);
    }

    private List<Document> sort(Set<Document> result){
        List<Document> list = new ArrayList<>(result);
        Collections.sort(list, new Comparator<Document>() {
            @Override
            public int compare(Document o1, Document o2) {
                if(o1.getScore() > o2.getScore())
                    return 1;
                if(o1.getScore() < o2.getScore())
                    return -1;
                return 0;
            }
        });
        return list;
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
