import java.io.*;

public class FileReaderClass {
    private FileReader fileReader;
    private InvertedIndex invertedIndex;

    public FileReaderClass() {
    }

    public static String[] GetFilesName() {
        File file = new File("./books/");
        return file.list();
    }

    public InvertedIndex createMap(ReadPrinciple readPrinciple) throws IOException {
        invertedIndex = new InvertedIndex(readPrinciple);
        for (String fileName : GetFilesName()) {
            fileReader = new FileReader("./books/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) {
                invertedIndex.AddToMapByLine(curLine, fileName);
            }
        }
        fileReader.close();
        return invertedIndex;
    }
}
