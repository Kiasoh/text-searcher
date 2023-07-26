import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InvertedIndexTest {

    ReadPrinciple readPrinciple = new ReadPrinciple("\\r\\n\\s-",new Stemmer(), true, new Chainsaw(2,5));
    InvertedIndex invertedIndex = new InvertedIndex(readPrinciple);
    @Test
    public void addToMapWithNGramTest(){
        List<String> words = new ArrayList<>();
        words.add("ab");
        words.add("cd");
        DocumentInfo documentInfo = DocumentInfo.createNewDoc("doc1",5);
        invertedIndex.addToMap(words,documentInfo);

//        List<DocumentInfo> list = List.of(invertedIndex.getMap().get("ab"));
//        String expected = documentInfo1.getName();
//        String actual = "doc1";
//        assertEquals(expected,actual);
    }

}
