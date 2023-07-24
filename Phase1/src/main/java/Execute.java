import java.io.IOException;
import java.util.*;

/**
 * Execute class
 * get query and sop marks and do search
 */
public class Execute implements Comparator{

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
//        List<Document> list = result.stream().toList();
//        list.sort(Comparator.comparing(Document::getScore));
//        list.sort((Document d1, Document d2) -> compare(d1,d2));
        return result.stream().toList();
    }

    private String trimQuery(String query) {
        int splitMarkQueryIndex = query.lastIndexOf("/sm");
        if (splitMarkQueryIndex != -1) {
            readPrinciple.setSplitMarks(query.substring(splitMarkQueryIndex + 4));
            return query.substring(0, splitMarkQueryIndex);
        } else
            return query;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if(((Document)o1).getScore() > ((Document)o2).getScore())
            return 1;
        if(((Document)o1).getScore() < ((Document)o2).getScore())
            return -1;
        return 0;
    }
}
