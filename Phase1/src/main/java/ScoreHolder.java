import lombok.*;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
public class ScoreHolder implements ScoreHandler {
    private final Document document;
    private double score;
    private int numTarget = 0;


    public static ScoreHolder createNewDoc(Document document) {
        ScoreHolder doc = new ScoreHolder(document);
        doc.giveScore();
        return doc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreHolder scoreHolder = (ScoreHolder) o;
        return Objects.equals(document.getName(), scoreHolder.document.getName());
    }

    public static Set<ScoreHolder> copyDocuments(Set<ScoreHolder> docs) {
        Set<ScoreHolder> newDocs = new HashSet<>();
        docs.forEach(doc -> newDocs.add(new ScoreHolder(doc.getDocument(), doc.getScore(),
                doc.getNumTarget())));
        return newDocs;
    }

    public static ScoreHolder contains(Set<ScoreHolder> docs, ScoreHolder target) {
        ArrayList<ScoreHolder> orderedDocs = new ArrayList<>(docs);
        for (ScoreHolder doc : orderedDocs) {
            if (doc.equals(target))
                return doc;
        }
        return new NullScoreHolder();
    }

    public static Set<ScoreHolder> sumScores(Set<ScoreHolder> docs) {
        ArrayList<ScoreHolder> docsList = new ArrayList<>(docs);
        Set<ScoreHolder> result = new HashSet<>();
        for (int i = 0; i < docsList.size(); i++) {
            ScoreHolder doc = docsList.get(i);
            for (int j = i + 1; j < docsList.size(); j++) {
                ScoreHolder newDoc = docsList.get(j);
                if (doc.equals(newDoc)) {
                    doc.addScore(newDoc.getScore());
                    docsList.remove(j);
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
        this.setScore((Math.log10(numTarget * 1.0 / document.getNumWords())) * -1.0);
    }

    public void addScore(double amount) {
        this.score += amount;
    }
}

class NullScoreHolder extends ScoreHolder {

    public NullScoreHolder() {
        super(new NullDocument());
    }
}

