import java.util.List;

public interface Guard {

    void scanWords(String text, Document document);

    ReadPrinciple getReadPrinciple();

    void setInvertedIndex(InvertedIndex invertedIndex);

    void addToMap(List<String> words, Document documentInfo);

    void addToMap(String word, Document documentInfo);
}
