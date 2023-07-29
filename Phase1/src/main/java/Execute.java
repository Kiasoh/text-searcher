import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.*;

/**
 * The Execute class represents a utility for executing search operations on an inverted index.
 * It manages a collection of guards and file readers and provides methods for running queries
 * and retrieving sorted search results.
 */
public class Execute{

    private final InvertedIndex invertedIndex;
    private final ArrayList<FileScanner> fileReaders;
    private final ArrayList<Guard> guards;

    /**
     * Checks if the Execute object is runnable, i.e., if it has both guards and file readers.
     *
     * @return true if the Execute object is runnable, false otherwise.
     */
    public Execute() {
        invertedIndex = new InvertedIndex();
        guards = new ArrayList<>();
        fileReaders = new ArrayList<>();
    }
    public boolean checkRunnable() {
        return !fileReaders.isEmpty() && !guards.isEmpty();
    }

    /**
     * Constructs a new Execute object with the specified guards and file readers.
     *
     * @param guards      The guards to be used for searching.
     * @param fileReaders The file readers to be used for reading files.
     * @throws Exception if the Execute object is not runnable (guards or file readers are empty).
     */
    public Execute(ArrayList<Guard> guards ,ArrayList<FileScanner> fileReaders) throws Exception {
        this.guards = guards;
        this.fileReaders = fileReaders;
        if (!checkRunnable())
            throw new Exception("invalid");
        invertedIndex = new InvertedIndex();
        guards.forEach(guard -> guard.setInvertedIndex(invertedIndex));
        fileReaders.forEach(FileScanner::readFiles);
    }
    /**
     * Runs a search query on the Execute object using the specified query string.
     *
     * @param query The search query string.
     * @return A list of ScoreHolder objects representing the sorted search results.
     * @throws IOException if an I/O error occurs during the search process.
     */
    public List<ScoreHolder> run(String query) throws IOException {
        QueryLists queryLists= new QueryLists();
        queryLists.categorization(query.split("\s+"), guards.get(0).getReadPrinciple());
        SearchProcess searchProcess = new SearchProcess(queryLists, invertedIndex );
        return sort(searchProcess.runSearch());
    }

    /**
     * Sorts the given set of ScoreHolder objects in descending order based on their scores.
     *
     * @param result The set of ScoreHolder objects to be sorted.
     * @return A list of ScoreHolder objects representing the sorted results.
     */
    private List<ScoreHolder> sort(Set<ScoreHolder> result) {
        List<ScoreHolder> list = new ArrayList<>(result);
        list.sort((o1, o2) -> Double.compare(o2.getScore(), o1.getScore()));
        return list;
    }

    /**
     * Retrieves a search query from the console input and sets the split marks in the ReadPrinciple object.
     *
     * @param in     The scanner object used for reading console input.
     * @param guards The list of guards.
     * @return The search query string.
     */
    public static String giveQueryFromConsole(Scanner in, ArrayList<Guard> guards) {
        String query = in.nextLine();
        int splitMarkQueryIndex = query.lastIndexOf("/sm");
        ReadPrinciple readPrinciple = guards.get(0).getReadPrinciple();
        if (splitMarkQueryIndex != -1)
            readPrinciple.setSplitMarks(query.substring(splitMarkQueryIndex + 4));
        else
            readPrinciple.setSplitMarks("");
        return query.substring(0, splitMarkQueryIndex);
    }
}
