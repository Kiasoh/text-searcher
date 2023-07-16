import java.util.ArrayList;

public class Inve {
    private String query;
    private ArrayList<String> essentialQuery;
    private ArrayList<String> optionalQuery;
    private ArrayList<String> forbiddenQuery;

    public Search(String query) {
        this.query = query;
        String[] temp;
        temp = query.split(" ");
        for (String word : temp) {
            if (word.charAt(0) == '+')
                optionalQuery.add(word.substring(1));
            else if (word.charAt(0) == '-')
                forbiddenQuery.add(word.substring(1));
            else
                essentialQuery.add(word);
        }
    }

    private void invertedIndex(){

    }
}
