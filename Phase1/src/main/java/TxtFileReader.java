import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * read files line by line and pass them to invertedIndex class
 */
public class TxtFileReader implements FileScanner {

    private final String path;

    public TxtFileReader(String path){
        this.path = path;
    }

    public ArrayList<Document> getFiles() {
        File file = new File(path);
        ArrayList<Document> validFiles = new ArrayList<>();
        if(!file.exists()){
            return validFiles;
        }
        for(String fileName : file.list()){
            try {
                if (fileName.substring(fileName.lastIndexOf(".")).equals(".txt"))
                    validFiles.add(new Document(fileName,0,0 , 0));
            } catch (StringIndexOutOfBoundsException ignored) {}
        }
        return validFiles;
    }

//    private void addToMapByLine(String line , String fileName , InvertedIndex in) {
//        String[] words = line.split("[" + in.getReadPrinciple().getSplitMarks() + "]+");
//        for (String word : words) {
//            if(in.getReadPrinciple().isUseNGram())
//                in.addToMap(in.getReadPrinciple().getChainsaw().nGram(word) , fileName);
//            in.addToMap(word, fileName);
//        }
//    }

    private void addToMap(String text, InvertedIndex in, String documentName){
        String[] words = text.split("[" + in.getReadPrinciple().getSplitMarks() + "]+");
        Document document = new Document(documentName,0 , words.length,0);
        for (String word : words) {
            document.giveScore();
            if(in.getReadPrinciple().isUseNGram())
                in.addToMap(in.getReadPrinciple().getChainsaw().nGram(word) , document);
            in.addToMap(word, document);
        }
    }
    @Override
    public InvertedIndex readFiles(ReadPrinciple readPrinciple){
        InvertedIndex invertedIndex = new InvertedIndex(readPrinciple);
        try {
            for (Document document : getFiles()) {
                String fileName = document.getName();
                String text = Files.readString(Paths.get(path + fileName));
                addToMap(text,invertedIndex,fileName);
            }
        }catch (IOException ignored){}
        return invertedIndex;
    }
}
