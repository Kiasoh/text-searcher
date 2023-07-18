import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * read files line by line and pass them to invertedIndex class
 */
public class FileReaderClass {
    private FileReader fileReader;
    private static String path;
    private static ArrayList<String > validFormats = new ArrayList<>();

    public FileReaderClass() {}

    public static void setPath(String path) {
        FileReaderClass.path = path;
    }

    public static void addValidFormat(String validFormat) {
        FileReaderClass.validFormats.add(validFormat);
    }

    public static ArrayList<String> getFilesName() {
        File file = new File(path);
        ArrayList<String> allFiles = new ArrayList<>(List.of(file.list()));;
        if (validFormats.isEmpty())
            return allFiles;
        ArrayList<String> validFiles = new ArrayList<>();
        for (String fileName : allFiles) {
            if (validFormats.contains(fileName.substring(fileName.lastIndexOf("."))))
                validFiles.add(fileName);
        }
        return validFiles;
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
        }catch (IOException ignored){
            System.out.println("Invalid file.");
        };
        return invertedIndex;
    }
}
