import java.util.ArrayList;
import java.util.List;

/**
 * The WordManipulation class implements the Normalization interface
 * and provides methods for manipulating words.
 */
public class WordManipulation implements Normalization {

    private static final ArrayList<String> prepositions = new ArrayList<>(List.of("of","with","at",
            "from","into", "during","including","until","against","among","through","despite","towards",
            "upon","that", "concerning","to","in","for","on","by","about","like","through","over","before",
            "these", "between","after","since","without","under","within","along","following","across", "those",
            "behind","beyond","plus","except","but","up","out","around","down","off","above","near","the"
            ,"this","us"));

    /**
     * Constructs a WordManipulation object.
     */
    public WordManipulation() {
        super();
    }

    /**
     * Normalizes a word by removing it if it is less than or equal to 2 characters long,
     * or if it is found in the list of prepositions.
     * @param word the word to normalize
     * @return the normalized word
     */
    @Override
    public String normalize(String word) {
        if (word.length() <= 2 || prepositions.contains(word))
            word = "";
        return word;
    }
}
