import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class FileReaderClass {
    private FileReader fileReader;
    public static HashMap<String , ArrayList<String >> map;
    Scanner inputScanner;


    public FileReaderClass() throws IOException {
        map = new HashMap<>();
        File file = new File ("./books/");
        String[] fileNames = file.list();
        for (String fileName : fileNames) {
            fileReader = new FileReader("./books/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String curLine;
            String word = "";
            while ((curLine = bufferedReader.readLine()) != null){
                curLine = curLine.toLowerCase();
                char[] charArray = curLine.toCharArray();
                for (Character c : charArray){
                    if(isAlphabetic(c))
                        word += c.toString();
                    else {
                        if (word.length() <= 2 || word.equals("the") || word.equals("and") || word.equals("into")) {
                            word = "";
                            continue;
                        }
//                        System.out.println(word);
                        ArrayList<String> temp;
                        if (map.containsKey(word)) {
                            temp = map.get(word);
                            if (temp.contains(fileName))
                                continue;
                            else {
                                temp.add(fileName);
                            }
                        }
                        else {
                            temp = new ArrayList<>();
                            temp.add(fileName);
                            map.put(word, temp);
                        }
                        word = "";
                    }
                }
            }
        }
        fileReader.close();
    }

    private boolean isAlphabetic(char c){
        return c >= 'a' && c <= 'z';
    }
}
