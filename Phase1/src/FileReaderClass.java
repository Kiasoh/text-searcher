import java.io.*;

public class FileReaderClass {
    private FileReader fileReader;

    public FileReaderClass() {
    }

    public static String[] getFilesName() {
        File file = new File("./books/");
        return file.list();
    }

    public InvertedIndex createMap(ReadPrinciple readPrinciple){
        InvertedIndex invertedIndex = new InvertedIndex(readPrinciple);
        try {
            for (String fileName : getFilesName()) {
                fileReader = new FileReader("./books/" + fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String curLine;
                while ((curLine = bufferedReader.readLine()) != null) {
                    invertedIndex.addToMapByLine(curLine, fileName);
                }
            }
            fileReader.close();
        }catch (IOException ignored){};
        return invertedIndex;
    }
}
