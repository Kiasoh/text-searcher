import lombok.Getter;

import javax.print.Doc;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * create the hashmap in which each word is mapped to a list of document names that the word is in that
 */
@Getter
public class InvertedIndex {

    public HashMap<String, Set<DocumentInfo>> map;
    public InvertedIndex() {
        map = new HashMap<>();
    }
    public void enterToMap(String word, Document document) {
        if (map.containsKey(word)) {
            boolean flag = false;
            for (DocumentInfo doc : map.get(word)) {
                if (doc.getDocument().equals(document)) {
                    doc.giveScore();
                    flag = true;
                }
            }
            if (!flag)
                map.get(word).add(DocumentInfo.createNewDoc(document));
        }
        else
            map.put(word, new HashSet<>(List.of(DocumentInfo.createNewDoc(document))));
    }
}
