import javax.management.InvalidAttributeValueException;
import java.io.*;
import java.util.*;

public class FileReaderClass {
    private FileReader fileReader;
    private Navigator navigator;
    public FileReaderClass(Navigator navigator)
    {
        this.navigator = navigator;
    }

    public static String[] GetFilesName()
    {
        File file = new File("./books/");
        return file.list();
    }

    public Navigator createMap() throws IOException {
        for (String fileName : GetFilesName()) {
            fileReader = new FileReader("./books/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) {
                navigator.AddToMapByLine(curLine , fileName);
            }
        }
        fileReader.close();
        return navigator;
    }
}
