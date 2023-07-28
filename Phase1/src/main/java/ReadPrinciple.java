import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReadPrinciple {

    private String splitMarks;
    private Normalizable normalization;
    private boolean useNGram;
    private Chainsaw chainsaw;

    public String[] splitText(String text){
        return text.split("[" + splitMarks + "]+");
    }
}
