import lombok.*;
import java.util.List;

/**
 * The GateKeeper class implements the Guard interface and represents a gatekeeper responsible for scanning words
 * and adding them to an inverted index.
 */
@RequiredArgsConstructor
@Getter
public class GateKeeper implements Guard {
    @Setter
    private InvertedIndex invertedIndex;
    private final ReadPrinciple readPrinciple;

    /**
     * Scans the given text and adds the words to the inverted index associated with the specified document.
     *
     * @param text     the text to scan
     * @param document the document to associate the words with
     */
    public void scanWords(String text, Document document) {
        String[] words = readPrinciple.splitText(text);
        ScoreHolder scoreHolder = new ScoreHolder(document);
        document.setNumWords(words.length);
        for (String word : words) {
            scoreHolder.giveScore();
            if(readPrinciple.isUseNGram())
                addToMap(readPrinciple.getChainsaw().EdgeNGram(word) , document);
            addToMap(word, document);
        }
    }

    /**
     * Adds the specified word to the inverted index associated with the given document.
     * The word is first normalized using the read principle's normalization method.
     *
     * @param word     the word to add to the inverted index
     * @param document the document to associate the word with
     */
    public void addToMap(String word, Document document) {
        word = readPrinciple.getNormalization().normalize(word);
        invertedIndex.enterToMap(word, document);
    }

    /**
     * Adds the list of words to the inverted index associated with the given document.
     * Each word in the list is converted to lowercase before adding to the inverted index.
     *
     * @param words    the list of words to add to the inverted index
     * @param document the document to associate the words with
     */
    public void addToMap(List<String> words, Document document) {
        words.forEach(word ->invertedIndex.enterToMap(word.toLowerCase(), document));
    }
}
