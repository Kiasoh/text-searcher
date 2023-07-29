/**
 * The Normalization interface defines the contract for classes that perform word normalization.
 */
public interface Normalization {

    /**
     * Normalizes the given word.
     * @param word the word to be normalized
     * @return the normalized word
     */
    String normalize(String word);
}

/**
 * The NullNormalization class implements the Normalization interface and represents a null normalization strategy,
 * where the input word is returned as it is without any modifications.
 */
class NullNormalization implements Normalization{

    /**
     * Normalizes the given word by returning it as it is.
     * @param word the word to be normalized
     * @return the normalized word (same as the input word)
     */
    @Override
    public String normalize(String word) {
        return word;
    }
}