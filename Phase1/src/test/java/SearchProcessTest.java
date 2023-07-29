import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchProcessTest {

    ScoreHolder doc1 = ScoreHolder.createNewDoc(new Document("doc1", 100));
    ScoreHolder doc2 = ScoreHolder.createNewDoc(new Document("doc2", 1000));
    ScoreHolder doc3 = ScoreHolder.createNewDoc(new Document("doc3", 1000));
    ArrayList<String> queryList;
    SearchProcess searchProcess;
    ReadPrinciple readPrinciple;
    QueryLists queryLists;
    InvertedIndex invertedIndex;

    @BeforeEach
    public void setup() {
        Document.documents.add(doc1.getDocument());
        Document.documents.add(doc2.getDocument());
        String[] query = new String[]{"+word1", "word2", "-word3"};
        queryList = new ArrayList<>(Arrays.asList(query));
        queryLists = new QueryLists();
        invertedIndex = new InvertedIndex();
        invertedIndex.enterToMap("word1", doc1.getDocument());
        invertedIndex.enterToMap("word2", doc1.getDocument());
        invertedIndex.enterToMap("word2", doc2.getDocument());
        invertedIndex.enterToMap("word3", doc3.getDocument());
        readPrinciple = mock(ReadPrinciple.class);
        when(readPrinciple.getNormalization()).thenReturn(new NullNormalization());
        queryLists.categorization(query, readPrinciple);
        searchProcess = new SearchProcess(queryLists, invertedIndex);
    }

    private boolean compareResults(Set<ScoreHolder> result1, Set<ScoreHolder> result2) {
        for (ScoreHolder doc : result1) {
            Set<ScoreHolder> ha = new HashSet<>(result2);
            if (ScoreHolder.contains(ha, doc).getClass() == NullScoreHolder.class)
                return false;
        }
        return true;
    }

    @Test
    public void runSearchTest() {
        Set<ScoreHolder> expected = searchProcess.runSearch();
        Set<ScoreHolder> actual = new HashSet<>();
        doc1.setScore(-4.0);
        actual.add(doc1);
        assertTrue(compareResults(actual, expected));
    }

    @Test
    public void baseResultTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method baseResult = SearchProcess.class.getDeclaredMethod("baseResult", ArrayList.class);
        baseResult.setAccessible(true);
        baseResult.invoke(searchProcess, queryList);
        HashSet<ScoreHolder> actual = new HashSet<>();
        actual.add(doc1);
        actual.add(doc2);
        assertTrue(compareResults(searchProcess.getResult(), actual));
    }

    @Test
    public void checkEssentialTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkEssential = SearchProcess.class.getDeclaredMethod("checkEssential", ArrayList.class);
        checkEssential.setAccessible(true);
        checkEssential.invoke(searchProcess, queryLists.getEssential());
        HashSet<ScoreHolder> actual = new HashSet<>();
        actual.add(doc1);
        actual.add(doc2);
        assertTrue(compareResults(searchProcess.getResult(), actual));
    }

    @Test
    public void interSectionTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method intersection = SearchProcess.class.getDeclaredMethod("intersection", Set.class);
        intersection.setAccessible(true);
        HashSet<ScoreHolder> set = new HashSet<>();
        set.add(doc1);
        intersection.invoke(searchProcess, set);
        assertTrue(compareResults(searchProcess.getResult(), set));
    }

    @Test
    public void checkOptionalTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkOptional = SearchProcess.class.getDeclaredMethod("checkOptional", ArrayList.class);
        checkOptional.setAccessible(true);
        checkOptional.invoke(searchProcess, queryLists.getOptional());
        HashSet<ScoreHolder> actual = new HashSet<>();
        actual.add(doc1);
        actual.add(doc2);
        assertTrue(compareResults(searchProcess.getResult(), actual));
    }

    @Test
    public void checkForcedTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkForced = SearchProcess.class.getDeclaredMethod("checkForced", boolean.class, ArrayList.class);
        checkForced.setAccessible(true);
        checkForced.invoke(searchProcess, false, queryLists.getForbidden());
        HashSet<ScoreHolder> actual = new HashSet<>();
        actual.add(doc1);
        actual.add(doc2);
        assertTrue(compareResults(searchProcess.getResult(), actual));
    }
}
