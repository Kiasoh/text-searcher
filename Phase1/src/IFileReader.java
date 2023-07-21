import java.util.ArrayList;

public interface IFileReader {
    InvertedIndex createMap(ReadPrinciple readPrinciple);
    ArrayList<String> getFilesName();
}
