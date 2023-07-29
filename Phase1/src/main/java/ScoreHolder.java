import lombok.*;
import java.util.*;

/**
 * Represents a holder for scores and associated documents.
 * Implements the ScoreHandler interface.
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ScoreHolder implements ScoreHandler {

    private final Document document;
    private double score;
    private int numTarget = 0;

    /**
     * Creates a new ScoreHolder object with the given document, calculates the score, and returns it.
     * @param document The document associated with the ScoreHolder.
     * @return A new ScoreHolder object with the given document and calculated score.
     */
    public static ScoreHolder createNewDoc(Document document) {
        ScoreHolder doc = new ScoreHolder(document);
        doc.giveScore();
        return doc;
    }

    /**
     * Checks if this ScoreHolder is equal to the specified object based on its name.
     * @param o The object to compare.
     * @return true if the ScoreHolder is equal to the specified object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreHolder scoreHolder = (ScoreHolder) o;
        return Objects.equals(document.getName(), scoreHolder.document.getName());
    }

    /**
     * Creates a new set of ScoreHolder objects by copying the contents of the given set.
     * @param docs The set of ScoreHolder objects to copy.
     * @return A new set of ScoreHolder objects with the same contents as the given set.
     */
    public static Set<ScoreHolder> copyScoreHolders(Set<ScoreHolder> docs) {
        Set<ScoreHolder> scoreHolders = new HashSet<>();
        docs.forEach(doc -> scoreHolders.add(new ScoreHolder(doc.getDocument(), doc.getScore(),
                doc.getNumTarget())));
        return scoreHolders;
    }

    /**
     * Checks if the given set of ScoreHolder objects contains the specified target.
     * @param docs The set of ScoreHolder objects to search in.
     * @param target The ScoreHolder object to search for.
     * @return The ScoreHolder object from the set that is equal to the target, or a NullScoreHolder if not found.
     */
    public static ScoreHolder contains(Set<ScoreHolder> docs, ScoreHolder target) {
        ArrayList<ScoreHolder> orderedDocs = new ArrayList<>(docs);
        for (ScoreHolder doc : orderedDocs)
            if (doc.equals(target))
                return doc;
        return new NullScoreHolder();
    }

    /**
     * Sums up the scores of ScoreHolder objects in the given set and returns a new set with the summed scores.
     * Duplicate ScoreHolder objects are merged into a single object with the combined score.
     * @param docs The set of ScoreHolder objects to sum the scores for.
     * @return A new set of ScoreHolder objects with the summed scores.
     */
    public static Set<ScoreHolder> sumScores(Set<ScoreHolder> docs) {
        Set<ScoreHolder> result = new HashSet<>();
        for (ScoreHolder doc : docs) {
            ScoreHolder mergedDoc = null;
            for (ScoreHolder existingDoc : result) {
                if (doc.equals(existingDoc)) {
                    mergedDoc = existingDoc;
                    break;
                }
            }
            if (mergedDoc != null)
                mergedDoc.addScore(doc.getScore());
            else
                result.add(doc);
        }
        return result;
    }

    /**
     * Increments the target count and recalculates the score based on the number of words in the associated document.
     */
    @Override
    public void giveScore() {
        numTarget++;
        this.setScore((Math.log10(numTarget * 1.0 / document.getNumWords())));
    }

    /**
     * Increases the score by the specified amount.
     * @param amount The amount to add to the current score.
     */
    public void addScore(double amount) {
        this.score += amount;
    }
}

/**
 * Represents a null ScoreHolder object.
 * Extends the ScoreHolder class and uses a NullDocument as the associated document.
 */
class NullScoreHolder extends ScoreHolder {

    /**
     * Creates a new NullScoreHolder object with a NullDocument as the associated document.
     */
    public NullScoreHolder() {
        super(new NullDocument());
    }
}

