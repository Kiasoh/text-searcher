import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TxtFileReaderTest {

    @Mock
    InvertedIndex invertedIndex;

    @Test
    void addToMapWithNGramTest(){
        List<String> words = new ArrayList<>();
        words.add("ab");
        words.add("cd");
        DocumentInfo documentInfo = new DocumentInfo("doc1",0,0,0);

        invertedIndex.addToMap(words, documentInfo);

        HashMap<String, HashSet<DocumentInfo>> res = new HashMap<>();
        HashSet<DocumentInfo> set = new HashSet<>();
        set.add(documentInfo);
        res.put("ab",set);
        res.put("cd",set);
        assertEquals(invertedIndex.getMap(),res);

    }
}
