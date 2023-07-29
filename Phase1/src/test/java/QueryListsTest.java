import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class QueryListsTest {

    @Mock
    ReadPrinciple readPrinciple;
    QueryLists queryLists = new QueryLists();
    String[] query = new String[]{"word1","+word2","-word3","word4"};

    @BeforeEach
    public void setup(){
        readPrinciple = mock(ReadPrinciple.class);
        when(readPrinciple.getNormalization()).thenReturn(new NullNormalization());
        queryLists.categorization(query,readPrinciple);
    }

    @Test
    public void categorizationEssentialTest(){
        assertEquals(queryLists.getEssential().size(), 2);
        assertTrue(queryLists.getEssential().contains("word1"));
        assertTrue(queryLists.getEssential().contains("word4"));
    }

    @Test
    public void categorizationOptionalTest(){
        assertEquals(queryLists.getOptional().size(), 1);
        assertTrue(queryLists.getOptional().contains("word2"));
    }

    @Test
    public void categorizationForbiddenTest(){
        assertEquals(queryLists.getForbidden().size(), 1);
        assertTrue(queryLists.getForbidden().contains("word3"));
    }
}
