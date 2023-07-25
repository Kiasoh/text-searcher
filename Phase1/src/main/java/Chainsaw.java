import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Chainsaw {

    private int min;
    private int max;

    public List<String> nGram(String word){
        List<String> candidates = new ArrayList<>();
        for (int i = min; i <=max ; i++) {
            for (int j = 0; j < word.length() - i + 1; j++) {
                candidates.add(word.substring(j , j + i));
            }
        }
        return candidates;
    }
}
