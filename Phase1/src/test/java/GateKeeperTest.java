import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GateKeeperTest {

    @Spy
    ReadPrinciple readPrinciple;
    GateKeeper gateKeeper;
    ScoreHolder scoreHolder1 = new ScoreHolder(new Document("doc1",5));
    ScoreHolder scoreHolder2 = new ScoreHolder(new Document("doc2",10));

    @BeforeEach
    public void setup(){
        readPrinciple = spy(ReadPrinciple.class);
        gateKeeper = new GateKeeper(readPrinciple);
        gateKeeper.setInvertedIndex(new InvertedIndex());
    }

    @Test
    public void addToMapWithListTest(){
        ArrayList<String> words = new ArrayList<>();
        words.add("word1");
        words.add("word2");
        gateKeeper.addToMap(words, scoreHolder1.getDocument());
        gateKeeper.addToMap(words, scoreHolder2.getDocument());
        assertEquals(ScoreHolder.contains(gateKeeper.getInvertedIndex().getMap().get("word1"), scoreHolder1), scoreHolder1);
    }

    @Test
    public void addToMapTest(){
        String word = "word";
        when (readPrinciple.getNormalization()).thenReturn(new NullNormalization());
        gateKeeper.addToMap(word, scoreHolder1.getDocument());
        gateKeeper.addToMap(word, scoreHolder2.getDocument());
        assertEquals(ScoreHolder.contains(gateKeeper.getInvertedIndex().getMap().get("word"), scoreHolder1), scoreHolder1);
    }

    @Test
    public void scanWordsTest(){
        String text1 = "code,star -bootcamp -\nhello";
        String text2 = "word1   word2.word3 ";

        readPrinciple.setSplitMarks("\\n\\s.-");
        when (readPrinciple.getNormalization()).thenReturn(new NullNormalization());
        gateKeeper.scanWords(text1, scoreHolder1.getDocument());
        gateKeeper.scanWords(text2, scoreHolder1.getDocument());

        assertNull(gateKeeper.getInvertedIndex().getMap().get("code"));
        assertNull(gateKeeper.getInvertedIndex().getMap().get("star"));
        assertEquals(ScoreHolder.contains(gateKeeper.getInvertedIndex().getMap().get("bootcamp"), scoreHolder1), scoreHolder1);
        assertEquals(ScoreHolder.contains(gateKeeper.getInvertedIndex().getMap().get("word1"), scoreHolder1), scoreHolder1);
        assertEquals(ScoreHolder.contains(gateKeeper.getInvertedIndex().getMap().get("hello"), scoreHolder1), scoreHolder1);
    }
}
