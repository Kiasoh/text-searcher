import java.util.ArrayList;
import java.util.List;

public class WordManipulation implements Normalizable {

    private static final ArrayList<String> prepositions = new ArrayList<>(List.of("of","with","at",
            "from","into", "during","including","until","against","among","through","despite","towards",
            "upon","that", "concerning","to","in","for","on","by","about","like","through","over","before",
            "these", "between","after","since","without","under","within","along","following","across", "those",
            "behind","beyond","plus","except","but","up","out","around","down","off","above","near","the"
            ,"this","us"));

    public WordManipulation(){
        super();
    }


    @Override
    public String normalize(String word){

        if (word.length() <= 2 || prepositions.contains(word)) {
            word = "";
        }
        return word;
    }
}
