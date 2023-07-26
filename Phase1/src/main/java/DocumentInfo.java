import lombok.*;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
public class DocumentInfo implements ScoreHandler {
    private final Document document;
    private double score;
    private int numTarget = 0;


    public static DocumentInfo createNewDoc(Document document) {
        DocumentInfo doc = new DocumentInfo(document);
        doc.giveScore();
        return doc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentInfo documentInfo = (DocumentInfo) o;
        return Objects.equals(document.getName(), documentInfo.document.getName());
    }

    public static Set<DocumentInfo> copyDocuments(Set<DocumentInfo> docs) {
        Set<DocumentInfo> newDocs = new HashSet<>();
        docs.forEach(doc -> newDocs.add(new DocumentInfo(doc.getDocument(), doc.getScore(),
                doc.getNumTarget())));
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
        this.setScore((Math.log(numTarget * 1.0 / document.getNumWords())) * -1.0);
    }

    public void addScore(double amount) {
        this.score += amount;
    }
}

class NullDocumentInfo extends DocumentInfo {

    public NullDocumentInfo() {
        super(Document.findDoc("asd:%^&#@$^%*(^.234"));
    }
}
