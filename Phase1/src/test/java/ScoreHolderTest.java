import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreHolderTest {

    ScoreHolder doc1 = ScoreHolder.createNewDoc(new Document("doc1", 100));
    ScoreHolder doc2 = ScoreHolder.createNewDoc(new Document("doc2", 1000));

    @Test
    public void createNewDocTest() {
        ScoreHolder scoreHolder = ScoreHolder.createNewDoc(new Document("doc1", 200));
        double expected = (Math.log10(1.0 / 200));
        assertEquals(scoreHolder.getScore(), expected);
    }

    @Test
    public void testEquals() {
        assertNotEquals(doc1, doc2);
    }

    @Test
    public void testDeepCopyIsDone() {
        Set<ScoreHolder> set = new HashSet<>();
        set.add(doc1);
        set.add(doc2);
        assertNotEquals(set, ScoreHolder.copyScoreHolders(set));
    }

    @Test
    public void containsTest() {
        Set<ScoreHolder> scoreHolders = new HashSet<>();
        scoreHolders.add(doc1);
        scoreHolders.add(new ScoreHolder(new Document("doc3", 50)));
        assertEquals(ScoreHolder.contains(scoreHolders, doc1), doc1);
        assertNotEquals(ScoreHolder.contains(scoreHolders, doc2), doc2);
    }

    @Test
    public void sumScoreTest() {
        Set<ScoreHolder> scoreHolders = new HashSet<>();
        scoreHolders.add(doc1);
        scoreHolders.add(doc2);
        scoreHolders.add(ScoreHolder.createNewDoc(new Document("doc1", 10)));
        Set<ScoreHolder> expected = ScoreHolder.sumScores(scoreHolders);
        ScoreHolder temp = ScoreHolder.contains(expected, doc1);
        assertNotEquals(temp.getClass(), NullScoreHolder.class);
        //log(1/100) + log(1/10)
        assertEquals(temp.getScore(), -3.0);
    }

    @Test
    public void giveScoreTest() {
        doc1.giveScore();
        assertEquals(doc2.getScore(), -3.0);
    }

}