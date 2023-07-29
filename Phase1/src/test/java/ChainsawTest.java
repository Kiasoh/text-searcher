import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChainsawTest {

    Chainsaw chainsaw;

    @Test
    public void ngramNormalWordLength(){
        chainsaw = new Chainsaw(3,4);
        String s = "collaborate";
        List<String> expected = chainsaw.EdgeNGram(s);
        List<String> actual = new ArrayList<>(Arrays.asList("col", "oll", "lla",
                "lab", "abo", "bor", "ora", "rat", "ate", "coll", "olla",
                "llab", "labo", "abor", "bora", "orat", "rate"));
        assertEquals(actual, expected);
    }

    @Test
    public void ngramMinLargerThanWordLength(){
        chainsaw = new Chainsaw(6,8);
        String s = "hello";
        List<String> expected = chainsaw.EdgeNGram(s);
        List<String> actual = new ArrayList<>();
        assertEquals(actual, expected);
    }
}
