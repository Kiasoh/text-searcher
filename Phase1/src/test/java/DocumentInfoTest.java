import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentInfoTest {

    @Test
    void testEquals() {
        DocumentInfo doc2 = DocumentInfo.createNewDoc("Hello" , 200);
        DocumentInfo doc1 = DocumentInfo.createNewDoc("Helllo" , 2002);
        assertEquals(false , doc1.equals(doc2));
    }
}