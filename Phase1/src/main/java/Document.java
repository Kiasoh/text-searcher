import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter @AllArgsConstructor
public class Document implements ScoreHandler{

    private String name;
    private double score;
    private int numWords;
    private int numTarget;

    @Override
    public void giveScore() {
        numTarget++;
        this.setScore(Math.log(numTarget*1.0 / numWords));
    }

    public static Set<Document> sumScores(Set<Document> docs){
        ArrayList<Document> docsList = new ArrayList<>(docs);
        Set<Document> result = new HashSet<>();
        for (int i = 0; i < docsList.size(); i++) {
            Document doc = docsList.get(i);
            for (int j = i+1; j < docsList.size() ; j++) {
                Document newDoc = docsList.get(j);
                if(doc.getName().equals(newDoc.getName()))
                    doc.addScore(newDoc.getScore());
            }
            result.add(doc);
        }
        return result;
    }

    public void addScore(double amount){
        this.score += amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(getName(), document.getName());
    }
}
