import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

/**
 * The Document class represents a document with a name and number of words.
 * It also provides a method for checking equality with another Document object.
 */
@AllArgsConstructor
@Getter
@Setter
public class Document {

    public static ArrayList<Document> documents = new ArrayList<>();
    private final String name;
    private int numWords;

    /**
     * Checks if this Document is equal to another Document based on their names.
     *
     * @param obj The other Document object to compare.
     * @return {@code true} if the Documents have the same name, {@code false} otherwise.
     */
    public boolean equals(Document obj) {
        return this.getName().equals(obj.getName());
    }
}
/**
 * The NullDocument class represents a special type of Document that is used to represent a null value.
 * It inherits from the Document class and sets its name as "null" and number of words as -1.
 */
class NullDocument extends Document{

    /**
     * constructor
     */
    public NullDocument() {
        super("null", -1);
    }
}
