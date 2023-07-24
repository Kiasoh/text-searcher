import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @AllArgsConstructor
public class Document implements ScoreHandler{

    private String name;
    private double score;
    private

    @Override
    public void giveScore() {

    }
}
