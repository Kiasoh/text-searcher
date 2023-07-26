import org.junit.jupiter.api.Test;
import org.testng.annotations.AfterMethod;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DocumentInfoTest {

    @Test
    public void testEquals() {
        DocumentInfo doc2 = DocumentInfo.createNewDoc("doc1" , 200);
        DocumentInfo doc1 = DocumentInfo.createNewDoc("doc2" , 2002);
        assertNotEquals(doc1, doc2);
    }

    @Test
    public void testDeepCopyIsDone() {
        DocumentInfo doc2 = DocumentInfo.createNewDoc("doc1" , 200);
        DocumentInfo doc1 = DocumentInfo.createNewDoc("doc2" , 2002);
        Set<DocumentInfo> set = new HashSet<>();
        set.add(doc1);
        set.add(doc2);
        assertNotEquals(set,DocumentInfo.copyDocuments(set));
    }
}