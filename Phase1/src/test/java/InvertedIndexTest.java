import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class InvertedIndexTest {

    @Mock
    ReadPrinciple readPrinciple = mock(ReadPrinciple.class);

    InvertedIndex invertedIndex = new InvertedIndex(null);
    DocumentInfo documentInfo = new DocumentInfo("doc1",0,0,0);

    @BeforeEach
    public void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addToMapWithNGramTest(){
        List<String> words = new ArrayList<>();
        words.add("word1");
        words.add("word2");
        invertedIndex.addToMap(words, documentInfo);
        DocumentInfo checkContainsWord1 = DocumentInfo.contains(invertedIndex.getMap().get("word1"), documentInfo);
        DocumentInfo checkContainsWord2 = DocumentInfo.contains(invertedIndex.getMap().get("word2"), documentInfo);
        assertEquals(checkContainsWord1.getName(),"doc1");
        assertEquals(checkContainsWord2.getName(),"doc1");
    }

    @Test
    public void addToMapTest() {
        String word = "word";
//        when(readPrinciple.).thenReturn(new ReadPrinciple());

        when(readPrinciple.getNormalization()).thenReturn(new NullNormalization());
        invertedIndex.addToMap(word, documentInfo);
        DocumentInfo checkContains = DocumentInfo.contains(invertedIndex.getMap().get("word"), documentInfo);
        assertEquals(checkContains.getName(), "doc1");
    }

    @Test
    public void enterToMapTest() throws Exception {
        String word = "word";
        Method enterToMap = InvertedIndex.class.getDeclaredMethod("enterToMap", String.class, DocumentInfo.class);
        enterToMap.setAccessible(true);
        enterToMap.invoke(invertedIndex, word, documentInfo);

        DocumentInfo checkContains = DocumentInfo.contains(invertedIndex.getMap().get("word"), documentInfo);
        assertEquals(checkContains.getName(),"doc1");
    }
}
