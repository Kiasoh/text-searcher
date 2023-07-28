import java.io.IOException;
import java.util.*;

/**
 * Execute class
 * get query and stop marks and do search
 */
public class Execute{

    private final InvertedIndex invertedIndex;
    private final ArrayList<FileScanner> fileReaders;
    private final ArrayList<Guard> guards;

    private boolean CheckRunnable() {
        return fileReaders.isEmpty() || guards.isEmpty();
    }

    public Execute(ArrayList<Guard> guards ,ArrayList<FileScanner> fileReaders) throws Exception {
        this.guards = guards;
        this.fileReaders = fileReaders;
        if (CheckRunnable())
            throw new Exception("invalid");
        invertedIndex = new InvertedIndex();
        guards.forEach(guard -> guard.setInvertedIndex(invertedIndex));
        fileReaders.forEach(FileScanner::readFiles);
    }

    public List<ScoreHolder> run(String query) throws IOException {
        QueryLists queryLists= new QueryLists();
        queryLists.categorization(query.split("\s+"), guards.get(0).getReadPrinciple());
        SearchProcess searchProcess = new SearchProcess(queryLists, invertedIndex );
        return sort(searchProcess.getResult());
    }

    private List<ScoreHolder> sort(Set<ScoreHolder> result){
        List<ScoreHolder> list = new ArrayList<>(result);
        list.sort((o1, o2) -> Double.compare(o2.getScore(), o1.getScore()));
        return list;
    }

    public static String giveQueryFromConsole(Scanner in, ArrayList<Guard> guards) {
        String query = in.nextLine();
        int splitMarkQueryIndex = query.lastIndexOf("/sm");
        System.out.println(splitMarkQueryIndex);
        ReadPrinciple readPrinciple = guards.get(0).getReadPrinciple();
        if (splitMarkQueryIndex != -1)
            readPrinciple.setSplitMarks(query.substring(splitMarkQueryIndex + 4));
        else
            readPrinciple.setSplitMarks("");
        return query.substring(0, splitMarkQueryIndex);
    }
}
