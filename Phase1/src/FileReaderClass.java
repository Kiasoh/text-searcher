import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class FileReaderClass {
    private FileReader fileReader;
    public static HashMap<String, ArrayList<String>> map;


    public FileReaderClass() throws IOException {
        map = new HashMap<>();
        File file = new File("./books/");
        String[] fileNames = file.list();
        for (String fileName : fileNames) {
            fileReader = new FileReader("./books/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String curLine;
            String word = "";
            Stemmer s;
            while ((curLine = bufferedReader.readLine()) != null) {
                curLine = curLine.toLowerCase();
                curLine += '/';
                char[] charArray = curLine.toCharArray();
                for (Character c : charArray) {
                    if (isAlphabetic(c)) {
                        word += c.toString();
                        continue;
                    }
                    if (word.length() <= 2 || word.equals("the") || word.equals("and") || word.equals("into")) {
                        word = "";
                        continue;
                    }
//                        System.out.println(word);
                    char[] wordCharArray = word.toCharArray();
                    s = new Stemmer();
                    s.add(wordCharArray, wordCharArray.length);
                    s.stem();
                    word = s.toString();
                    ArrayList<String> temp;
                    if (map.containsKey(word)) {
                        temp = map.get(word);
                        if (temp.contains(fileName))
                            continue;
                        else {
                            temp.add(fileName);
                            map.put(word, temp);
                        }
                    } else {
                        temp = new ArrayList<>();
                        temp.add(fileName);
                        char[] boz = word.toCharArray();

//                        System.out.println(s);
                        map.put(s.toString(), temp);
                    }
                    word = "";
                }
            }
        }
        fileReader.close();
    }

    private boolean isAlphabetic(char c) {
        return c >= 'a' && c <= 'z';
    }
}
