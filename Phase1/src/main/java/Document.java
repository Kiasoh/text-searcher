import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter @Setter
public class Document {
    public static ArrayList<Document> documents = new ArrayList<>();
    public final String name;
    public int numWords;

    public static Document findDoc(String name) {
        for (Document doc:documents)
            if (doc.getName() == name)
                return doc;
        return new NullDocument();
    }

    public boolean equals(Document obj) {
        return this.getName().equals(obj.getName());
    }
}
class NullDocument extends Document{
    public NullDocument()
    {
        super("null", -1);
    }
}
