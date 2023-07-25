import org.junit.jupiter.api.Test;

import javax.print.Doc;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    @Test
    void testEquals() {
        Document doc2 = Document.createNewDoc("Hello" , 200);
        Document doc1 =Document.createNewDoc("Helllo" , 2002);
        assertEquals(false , doc1.equals(doc2));
    }
}