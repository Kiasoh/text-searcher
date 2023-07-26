import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * create the hashmap in which each word is mapped to a list of document names that the word is in that
 */
@Getter
public class InvertedIndex {

    private HashMap<String, Set<ScoreHolder>> map;
    public InvertedIndex() {
        map = new HashMap<>();
    }

    public void enterToMap(String word, Document document) {
        if (map.containsKey(word)) {
            boolean flag = false;
            for (ScoreHolder doc : map.get(word)) {
                if (doc.getDocument().equals(document)) {
                    doc.giveScore();
                    flag = true;
                }
            }
            if (!flag)
                map.get(word).add(ScoreHolder.createNewDoc(document));
        }
        else
            map.put(word, new HashSet<>(List.of(ScoreHolder.createNewDoc(document))));
    }
}
