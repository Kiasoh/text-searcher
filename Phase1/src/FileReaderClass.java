import java.io.*;

public class FileReaderClass {
    private FileReader fileReader;
    private static String path = "./books/";

    public FileReaderClass() {}

    public static void setPath(String path) {
        FileReaderClass.path = path;
    }

    public static String[] getFilesName() {
        File file = new File(path);
        return file.list();
    }

    public InvertedIndex createMap(ReadPrinciple readPrinciple){
        InvertedIndex invertedIndex = new InvertedIndex(readPrinciple);
        try {
            for (String fileName : getFilesName()) {
                fileReader = new FileReader(path + fileName);
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
