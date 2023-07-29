import lombok.*;

/**
 * Represents a ReadPrinciple object.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReadPrinciple {

    private String splitMarks;
    private Normalization normalization;
    private boolean useNGram;
    private Chainsaw chainsaw;

    /**
     * Splits the given text based on the specified split marks.
     *
     * @param text the text to be split
     * @return an array of strings after splitting the text
     */
    public String[] splitText(String text){
        return text.split("[" + splitMarks + "]+");
    }
}
