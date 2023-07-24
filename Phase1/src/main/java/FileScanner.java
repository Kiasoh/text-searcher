import java.util.ArrayList;

public interface FileScanner {

    InvertedIndex readFiles(ReadPrinciple readPrinciple);

    ArrayList<Document> getFiles();

}
