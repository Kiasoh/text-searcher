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

    public HashMap<String, Set<DocumentInfo>> map;
    private final ReadPrinciple readPrinciple;

    public InvertedIndex(ReadPrinciple readPrinciple) {
        this.readPrinciple = readPrinciple;
        map = new HashMap<>();
    }

    public void addToMap(String word, DocumentInfo documentInfo){
        word = readPrinciple.getNormalization().normalize(word);
        enterToMap(word, documentInfo);
    }

    public void addToMap(List<String> words, DocumentInfo documentInfo){
        words.forEach(word ->enterToMap(word.toLowerCase(), documentInfo));
    }

    private void enterToMap(String word, DocumentInfo documentInfo) {
        if (map.containsKey(word)) {
            boolean flag = false;
            for (DocumentInfo doc : map.get(word)) {
                if (doc.equals(documentInfo)) {
                    doc.giveScore();
                    flag = true;
                }
            }
            if (!flag)
                map.get(word).add(DocumentInfo.createNewDoc(documentInfo.getName(),
                        documentInfo.getNumWords()));
        }
        else
            map.put(word, new HashSet<>(List.of(DocumentInfo.createNewDoc(documentInfo.getName()
                    , documentInfo.getNumWords()))));
    }
}
