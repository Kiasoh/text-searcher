import lombok.Getter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The InvertedIndex class represents an inverted index data structure used for text retrieval.
 * It maps words to a set of ScoreHolder objects representing documents that contain the word
 */
@Getter
public class InvertedIndex {

    private final HashMap<String, Set<ScoreHolder>> map;

    /**
     * constructs an empty map
     */
    public InvertedIndex() {
        map = new HashMap<>();
    }

    /**
     * Adds a document to the inverted index for the given word.
     * If the word already exists in the index, it updates the score of the document.
     * If the word does not exist in the index, it creates a new entry with the document and its score.
     * @param word The word to be added or updated in the index.
     * @param document The document associated with the word.
     */
    public void enterToMap(String word, Document document) {
        //computeIfAbsent to ensure that the word is present in the map with an empty set if it doesn't exist.
        map.computeIfAbsent(word, k -> new HashSet<>()).stream()
                .filter(doc -> doc.getDocument().equals(document))
                .findFirst()
                //If a matching document is found, we call giveScore() on the ScoreHolder object.
                //If no matching document is found (findFirst() returns an empty optional),
                //we add a new ScoreHolder object for the document to the set.
                .ifPresentOrElse(ScoreHolder::giveScore,
                        () -> map.get(word).add(ScoreHolder.createNewDoc(document)));
    }
}
