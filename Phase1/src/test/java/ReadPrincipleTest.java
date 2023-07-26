import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReadPrincipleTest {
    @Test
    void testBuilder(){
        ReadPrinciple readPrinciple = ReadPrinciple.builder()
                .useNGram(true).splitMarks("\\r\\n\\s-")
                .normalization(new Stemmer()).chainsaw(null)
                .build();

        assertEquals(readPrinciple.getSplitMarks(), "\\r\\n\\s-");
    }
}
