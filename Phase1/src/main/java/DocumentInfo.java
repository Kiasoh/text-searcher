import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInfo implements ScoreHandler {

    private String name;
    private double score;
    private int numWords;
    private int numTarget;


    public static DocumentInfo createNewDoc(String name, int numWords) {
        DocumentInfo doc = new DocumentInfo(name, 0, numWords, 0);
        doc.giveScore();
        return doc;
    }

    public static Set<DocumentInfo> CopyDocuments(Set<DocumentInfo> docs) {
        Set<DocumentInfo> newDocs = new HashSet<>();
        docs.forEach(doc -> newDocs.add(new DocumentInfo(doc.getName(), doc.getScore(),
                doc.getNumWords(), doc.getNumTarget())));
        return newDocs;
    }

    public static DocumentInfo contains(Set<DocumentInfo> docs, DocumentInfo target) {
        ArrayList<DocumentInfo> orderedDocs = new ArrayList<>(docs);
        for (DocumentInfo doc : orderedDocs) {
            if (doc.equals(target))
                return doc;
        }
        return new NullDocumentInfo();
    }

    public static Set<DocumentInfo> sumScores(Set<DocumentInfo> docs) {
        ArrayList<DocumentInfo> docsList = new ArrayList<>(docs);
        Set<DocumentInfo> result = new HashSet<>();
        for (int i = 0; i < docsList.size(); i++) {
            DocumentInfo doc = docsList.get(i);
            for (int j = i + 1; j < docsList.size(); j++) {
                DocumentInfo newDoc = docsList.get(j);
                if (doc.equals(newDoc)) {
                    doc.addScore(newDoc.getScore());
                    docs.remove(newDoc);
                    j--;
                }
            }
            result.add(doc);
        }
        return result;
    }

    @Override
    public void giveScore() {
        numTarget++;
        this.setScore((Math.log(numTarget * 1.0 / numWords)) * -1.0);
    }

    public void addScore(double amount) {
        this.score += amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentInfo documentInfo = (DocumentInfo) o;
        return Objects.equals(getName(), documentInfo.getName());
    }
}

class NullDocumentInfo extends DocumentInfo {

    public NullDocumentInfo() {
        super();
    }
}
