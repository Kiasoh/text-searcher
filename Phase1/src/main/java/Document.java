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


    public static Document createNewDoc(String name , int numWords)
    {
        Document doc = new Document(name , 0 , numWords , 0);
        doc.giveScore();
        return doc;
    }
    public static Set<Document> CopyDocuments (Set<Document> docs)
    {
        Set<Document> newDocs = new HashSet<>();
        docs.stream().forEach(doc -> newDocs.add(new Document(doc.getName(), doc.getScore(), doc.getNumWords(), doc.getNumTarget())));
        return newDocs;
    }
    public static Set<Document> sumScores(Set<Document> docs){
        ArrayList<Document> docsList = new ArrayList<>(docs);
        Set<Document> result = new HashSet<>();
        for (int i = 0; i < docsList.size(); i++) {
            Document doc = docsList.get(i);
            for (int j = i+1; j < docsList.size() ; j++) {
                Document newDoc = docsList.get(j);
                if(doc.equals(newDoc)) {
                    doc.addScore(newDoc.getScore());
                    docs.remove(newDoc);
                    j--;
                }
            }
            result.add(doc);
        }
        return result;
    }
    public static boolean contains (Set<Document> docs , Document target)
    {
        for (Document doc:docs) {
            if (doc.equals(target))
                return true;
        }
        return false;
    }

    @Override
    public void giveScore() {
        numTarget++;
        this.setScore((Math.log(numTarget*1.0 / numWords))*-1.0);
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
