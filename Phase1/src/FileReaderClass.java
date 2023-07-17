import java.io.*;
import java.util.*;

public class FileReaderClass {
    private FileReader fileReader;
    private Map map;
    public FileReaderClass(Map map)
    {
        this.map = map;
    }


    public void createMap() throws IOException {
        File file = new File("./books/");
        String[] fileNames = file.list();
        for (String fileName : fileNames) {
            fileReader = new FileReader("./books/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String curLine;
            String word = "";
            Stemmer stemmer;
            while ((curLine = bufferedReader.readLine()) != null) {
                map.AddToMapByLine(curLine , fileName);
            }
        }
        fileReader.close();
    }


}
