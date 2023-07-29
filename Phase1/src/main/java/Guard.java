import java.util.List;

/**
 * The Guard interface represents a guard responsible for scanning words, managing inverted index,
 * and adding words to the index.
 */
public interface Guard {

    /**
     * Scans the given text and updates the associated document's scoreHolder with relevant information.
     * @param text The text to be scanned.
     * @param document The Document object representing the associated document.
     */
    void scanWords(String text, Document document);

    /**
     * Retrieves the read principle of the Guard.
     * @return The ReadPrinciple object representing the read principle.
     */
    ReadPrinciple getReadPrinciple();

    /**
     * Sets the inverted index to be used by the Guard.
     * @param invertedIndex The InvertedIndex object representing the inverted index.
     */
    void setInvertedIndex(InvertedIndex invertedIndex);

    /**
     * Adds a list of words to the inverted index along with the corresponding document information.
     * @param words The list of words to be added to the inverted index.
     * @param documentInfo The Document object representing the corresponding document.
     */
    void addToMap(List<String> words, Document documentInfo);

    /**
     * Adds a single word to the inverted index along with the corresponding document information.
     * @param word The word to be added to the inverted index.
     * @param documentInfo The Document object representing the corresponding document.
     */
    void addToMap(String word, Document documentInfo);
}
