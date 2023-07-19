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
            try {
                if (validFormats.contains(fileName.substring(fileName.lastIndexOf("."))))
                    validFiles.add(fileName);
            }catch (StringIndexOutOfBoundsException ignored){}
        }
        return validFiles;
    }
    public void addToMapByLine(String line , String fileName , InvertedIndex in)
    {
        String word = "";
        int count = 0;
        for (Character c : in.getReadPrinciple().prepareForScan(line)) {
            if (in.getReadPrinciple().splitBy(c)) {
                word += c.toString();
                count++;
                if(count == line.length())
                    in.addToMap(word,fileName);
                continue;
            }

            in.addToMap(word, fileName);
            word = "";
        }
    }
    public InvertedIndex createMap(ReadPrinciple readPrinciple){
        InvertedIndex invertedIndex = new InvertedIndex(readPrinciple);
        try {
            for (String fileName : getFilesName()) {
                fileReader = new FileReader(path + fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String curLine;
                while ((curLine = bufferedReader.readLine()) != null) {
                    addToMapByLine(curLine, fileName , invertedIndex);
                }
            }
            fileReader.close();
        }catch (IOException ignored){
            System.out.println("Invalid file.");
        };
        return invertedIndex;
    }
}
