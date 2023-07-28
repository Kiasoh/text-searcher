import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ReadPrincipleTest {

    ReadPrinciple readPrinciple = ReadPrinciple.builder()
            .useNGram(true).splitMarks("\\r\\n\\s-")
            .normalization(new Stemmer()).chainsaw(null)
            .build();

    @Test
    void testBuilder(){
        assertEquals(readPrinciple.getSplitMarks(), "\\r\\n\\s-");
        assertEquals(readPrinciple.getNormalization().getClass(), Stemmer.class);
        assertTrue(readPrinciple.isUseNGram());
    }

    @Test
    void splitTextTester(){
        String text1 = "code,star -bootcamp -\nhello";
        String text2 = "word1   word2.word3 ";

        String[] text1Expected = new String[]{"code,star", "bootcamp", "hello"};
        String[] text2Expected = new String[]{"word1", "word2.word3"};
        assertArrayEquals(readPrinciple.splitText(text1), text1Expected);
        assertArrayEquals(readPrinciple.splitText(text2), text2Expected);
    }
}
