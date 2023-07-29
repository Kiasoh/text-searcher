import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Chainsaw that can generate n-grams from a given word.
 */
@AllArgsConstructor
public class Chainsaw {

    private int min;
    private int max;

    /**
     * Generates n-grams from the given word.
     * @param word the word to generate n-grams from
     * @return a list of n-grams
     */
    public List<String> EdgeNGram(String word) {
        List<String> candidates = new ArrayList<>();
        for (int i = min; i <= max ; i++) {
            for (int j = 0; j < word.length() - i + 1; j++) {
                candidates.add(word.substring(j , j + i));
            }
        }
        return candidates;
    }
}
