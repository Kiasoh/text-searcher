import java.io.IOException;
import java.util.*;

/**
 * Execute class
 * get query and sop marks and do search
 */
public class Execute{
    private final InvertedIndex invertedIndex;
    private final ArrayList<FileScanner> fileReaders;
    private final ArrayList<Guard> guards;
    private boolean CheckRunable() {
        return fileReaders.isEmpty() || guards.isEmpty();
    }
    public Execute(ArrayList<Guard> guards ,ArrayList<FileScanner> fileReaders) throws Exception {
        this.guards = guards;
        this.fileReaders = fileReaders;
        if (CheckRunable())
            throw new Exception("Bro you suck!");
        invertedIndex = new InvertedIndex();
        guards.stream().forEach(guard -> guard.setInvertedIndex(invertedIndex));
        fileReaders.stream().forEach(fileReader -> fileReader.readFiles());
    }

    public List<ScoreHolder> run(String query) throws IOException {
        QueryLists queryLists= new QueryLists();
        queryLists.categorization(query.split("\s+"), guards.get(0).getReadPrinciple());
        SearchProcess searchProcess = new SearchProcess(queryLists, invertedIndex );
        return sort(searchProcess.getResult());
    }

    public List<ScoreHolder> run(Scanner in) throws IOException {
        String query = in.nextLine();
        return run(query);
    }

    private List<ScoreHolder> sort(Set<ScoreHolder> result){
        List<ScoreHolder> list = new ArrayList<>(result);
        Collections.sort(list, new Comparator<ScoreHolder>() {
            @Override
            public int compare(ScoreHolder o1, ScoreHolder o2) {
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
            guards.get(0).getReadPrinciple().setSplitMarks(query.substring(splitMarkQueryIndex + 4));
            return query.substring(0, splitMarkQueryIndex);
        } else
            return query;
    }

}
