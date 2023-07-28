import lombok.*;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class GateKeeper implements Guard {

    private InvertedIndex invertedIndex;
    private final ReadPrinciple readPrinciple;

    public void setInvertedIndex(InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    public void scanWords(String text, Document document){
        String[] words = readPrinciple.splitText(text);
        ScoreHolder scoreHolder = new ScoreHolder(document);
        document.setNumWords(words.length);
        for (String word : words) {
            scoreHolder.giveScore();
            if(readPrinciple.isUseNGram())
                addToMap(readPrinciple.getChainsaw().nGram(word) , document);
            addToMap(word, document);
        }
    }

    public void addToMap(String word, Document document){
        word = readPrinciple.getNormalization().normalize(word);
        invertedIndex.enterToMap(word, document);
    }

    public void addToMap(List<String> words, Document document){
        words.forEach(word ->invertedIndex.enterToMap(word.toLowerCase(), document));
    }
}
