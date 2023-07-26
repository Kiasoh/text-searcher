import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ScoreHolderTest {

    ScoreHolder doc2 = ScoreHolder.createNewDoc(new Document("doc1" , 200));
    ScoreHolder doc1 = ScoreHolder.createNewDoc(new Document("doc2" , 2002));

    @Test
    public void createNewDocTest(){
        ScoreHolder scoreHolder = ScoreHolder.createNewDoc(new Document("doc1" , 200));
        assertEquals(



                
        )
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
        assertNotEquals(set,ScoreHolder.copyDocuments(set));
    }
}