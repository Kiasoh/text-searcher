import java.io.*;

public class FileReaderClass {
    private FileReader fileReader;
    private InvertedIndex invertedIndex;

    public FileReaderClass(InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    public static String[] GetFilesName() {
        File file = new File("./books/");
        return file.list();
    }

    public InvertedIndex createMap() throws IOException {
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
