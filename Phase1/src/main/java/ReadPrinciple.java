import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * split and normalization
 */
@Getter @AllArgsConstructor
@Builder @Setter
public class ReadPrinciple {

    private String splitMarks;
    public Normalizable normalization;
    private boolean useNGram;
    private Chainsaw chainsaw;

}
