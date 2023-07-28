import lombok.RequiredArgsConstructor;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@RequiredArgsConstructor
public class TxtFileReader implements FileScanner {

    private final String path;
    private final Guard guard;

    public ArrayList<Document> getFiles() {
        File file = new File(path);
        ArrayList<Document> validFiles = new ArrayList<>();
        if(!file.exists()){
            return validFiles;
        }
        for(String fileName : file.list()){
            try {
                if (fileName.substring(fileName.lastIndexOf(".")).equals(".txt"))
                    validFiles.add(new Document(fileName,0));
            } catch (StringIndexOutOfBoundsException ignored) {}
        }
        return validFiles;
    }

    @Override
    public void readFiles(){
        try {
            for (Document doc : getFiles()) {
                String fileName = doc.getName();
                Document.documents.add(doc);
                String text = Files.readString(Paths.get(path + fileName));
                guard.scanWords(text,doc);
            }
        }catch (IOException ignored){}
    }
}
