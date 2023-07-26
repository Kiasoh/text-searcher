import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvertedIndexTest {
    private final InvertedIndex invertedIndex = new InvertedIndex();
    ScoreHolder scoreHolder1 = new ScoreHolder(new Document("doc1",5));
    ScoreHolder scoreHolder2 = new ScoreHolder(new Document("doc2",10));

    @BeforeEach
    public void setup() {

    }
    @Test
    public void enterToMapTest() {
        String word = "word";
        Document document1 = new Document("doc1",5);
        Document document2 = new Document("doc2",5);
        invertedIndex.enterToMap(word, document1);
        invertedIndex.enterToMap(word, document2);

        ScoreHolder checkContains = ScoreHolder.contains(invertedIndex.getMap().get("word"), scoreHolder1);
        assertEquals(checkContains.getDocument().getName(),"doc1");

        checkContains = ScoreHolder.contains(invertedIndex.getMap().get("word"), scoreHolder2);
        assertEquals(checkContains.getDocument().getName(),"doc2");
    }
}
