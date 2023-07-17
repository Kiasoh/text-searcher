import java.io.*;
import java.util.*;

public class FileReaderClass {
    private FileReader fileReader;
    public static HashMap<String, ArrayList<String>> map;
    private static final ArrayList<String> prepositions = new ArrayList<>(List.of("of","with","at","from","into",
            "during","including","until","against","among","through","despite","towards","upon",
            "concerning","to","in","for","on","by","about","like","through","over","before",
            "between","after","since","without","under","within","along","following","across",
            "behind","beyond","plus","except","but","up","out","around","down","off","above","near","the"));

    public FileReaderClass() throws IOException {
        map = new HashMap<>();
    }

    private boolean isAlphabetic(char c) {
        return c >= 'a' && c <= 'z';
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
                curLine = curLine.toLowerCase();
                curLine += '/';
                char[] charArray = curLine.toCharArray();
                for (Character c : charArray) {
                    if (isAlphabetic(c)) {
                        word += c.toString();
                        continue;
                    }
                    else
                    word = normalize(word);
                    ArrayList<String> temp;
                    if (map.containsKey(word)) {
                        temp = map.get(word);
                        if (temp.contains(fileName)) {
                            word = "";
                            continue;
                        }
                        else {
                            temp.add(fileName);
                            map.put(word, temp);
                        }
                    } else {
                        temp = new ArrayList<>();
                        temp.add(fileName);
                        map.put(word, temp);
                    }
                    word = "";
                }
            }
        }
        fileReader.close();
    }

    public static String normalize(String word){
        if (word.length() <= 2 || prepositions.contains(word)) {
            word = "";
            return word;
        }
        char[] wordCharArray = word.toCharArray();
        Stemmer stemmer = new Stemmer();
        stemmer.add(wordCharArray, wordCharArray.length);
        stemmer.stem();
        word = stemmer.toString();
        return word;
    }
}
