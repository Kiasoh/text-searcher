import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * create the hashmap in which each word is mapped to a list of document names that the word is in that
 */
@Getter
public class InvertedIndex {

    public HashMap<String, HashSet<Document>> map;
    private final ReadPrinciple readPrinciple;

    public InvertedIndex(ReadPrinciple readPrinciple) {
        this.readPrinciple = readPrinciple;
        map = new HashMap<>();
    }

    public void addToMap(String word, Document document){
        word = readPrinciple.getNormalization().normalize(word);
        enterToMap(word, document);
    }

    public void addToMap(List<String> words, Document document){
        words.forEach(word ->enterToMap(word,document));
    }

    private void enterToMap(String word, Document document) {
        if (map.containsKey(word)) {
            boolean flag = false;
            for (Document doc : map.get(word)) {
                if (doc.equals(document)) {
                    doc.giveScore();
                    flag = true;
                }
            }
            if (!flag)
                map.get(word).add(Document.createNewDoc(document.getName() , document.getNumWords()));
//            map.get(word).add(document);
        }
        else
            map.put(word, new HashSet<>(List.of(Document.createNewDoc(document.getName() , document.getNumWords()))));
    }
}
