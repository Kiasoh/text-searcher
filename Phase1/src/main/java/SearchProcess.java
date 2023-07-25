import java.util.*;

/**
 * do the search with processed inverted index and processed query
 */
public class SearchProcess {

    private final InvertedIndex invertedIndex;
    private final QueryLists queryLists;
    private Set<Document> result;

    public SearchProcess(String query, InvertedIndex invertedIndex,
                         ReadPrinciple readPrinciple, FileScanner fileReader) {
        this.invertedIndex = invertedIndex;
        queryLists = new QueryLists();
        queryLists.categorization(query.split("\s+"), readPrinciple);
        result = new HashSet<>(fileReader.getFiles());
    }

    public Set<Document> getResult() {
        result = Document.sumScores(result);
        search();
        return result;
    }

    private boolean containsOptional(Boolean flag) {
        return (!flag && queryLists.getOptional().size() != 0);
    }
    private boolean containsEssential(String word, Document doc) {
        return (invertedIndex.map.get(word) == null || !Document.contains(invertedIndex.map.get(word) ,doc ));
    }

    private void checkEssential(ArrayList<String> query) {
        if(query.isEmpty())
            return;

        for (String word : query){
            if (invertedIndex.map.containsKey(word)) {
                intersection(invertedIndex.map.get(word));
//                result.retainAll(invertedIndex.map.get(word));
            }
            else{
                result = new HashSet<>();
                return;
            }
        }
    }

    private void intersection(Set<Document> documents){
        Iterator<Document> iterator = result.iterator();
        while (iterator.hasNext()){
            boolean flag = false;
            Document doc = iterator.next();
            for (Document document : documents) {
                if (doc.getName().equals(document.getName())) {
//                    iterator.remove();
                    flag = true;
                    doc.addScore(document.getScore());
                    break;
                }
            }
            if (!flag)
                iterator.remove();
        }
    }


    private void checkForced(boolean isEssential, ArrayList<String> query) {
        result.removeIf(doc -> query.stream().anyMatch(word -> containsEssential(word, doc) == isEssential));
    }

    private void checkOptional(ArrayList<String> query) {
        Iterator<Document> it = result.iterator();
        while(it.hasNext()) {
//        it.forEachRemaining(doc -> {
            Document doc = it.next();
            boolean flag = false;
            for (String word : query) {
                if (invertedIndex.map.get(word) == null)
                    continue;
                else if (Document.contains(invertedIndex.map.get(word) ,doc )) {
                    flag = true;
                    break;
                }
            }
            if (containsOptional(flag)) {
                it.remove();
            }
//        });
        }
    }

    private void search() {
        checkEssential(queryLists.getEssential());
//        checkForced(true,queryLists.getEssential());
        checkForced(false, queryLists.getForbidden());
        checkOptional(queryLists.getOptional());
        if (result.isEmpty())
            result.add(new Document("THERE IS NO DOCUMENT",0,0,0));
    }
}