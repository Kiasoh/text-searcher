import java.util.ArrayList;

/**
 * The FileScanner interface represents a component that scans files and retrieves information about them.
 */
public interface FileScanner {

    /**
     * Reads files and processes them.
     */
    void readFiles();

    /**
     * Retrieves the list of scanned files.
     * @return An ArrayList containing the scanned Document objects.
     */
    ArrayList<Document> getFiles();
}
