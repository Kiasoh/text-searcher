import java.util.*;

/**
 * do the search with processed inverted index and processed query
 */
public class SearchProcess {

    private final InvertedIndex invertedIndex;
    private final QueryLists queryLists;
    private Set<DocumentInfo> result;

    public SearchProcess(String query, InvertedIndex invertedIndex,
                         ReadPrinciple readPrinciple, FileScanner fileReader) {
        this.invertedIndex = invertedIndex;
        queryLists = new QueryLists();
        queryLists.categorization(query.split("\s+"), readPrinciple);
        result = new HashSet<>(fileReader.getFiles());
    }

    public Set<DocumentInfo> getResult() {
        result = DocumentInfo.sumScores(result);
        search();
        return result;
    }

    private boolean containsOptional(Boolean flag) {
        return (!flag && queryLists.getOptional().size() != 0);
    }

    private boolean containsEssential(String word, DocumentInfo doc) {
        return (invertedIndex.map.get(word) == null || DocumentInfo.contains(invertedIndex.map.get(word), doc).getClass() == NullDocumentInfo.class);
    }

    private void baseResult(ArrayList<String> query) {
        if (query.isEmpty()) {
            return;
        }
        if (invertedIndex.map.containsKey(query.get(0))) {
            result = DocumentInfo.CopyDocuments(invertedIndex.map.get(query.get(0)));
            query.remove(0);
        }
        else {
            result = new HashSet<>();
        }
    }

    private void checkEssential(ArrayList<String> query) {
        if (query.isEmpty())
            return;
        for (String word : query) {
            if (invertedIndex.map.containsKey(word)) {
                intersection(invertedIndex.map.get(word));
            } else {
                result = new HashSet<>();
                return;
            }
        }
    }

    private void intersection(Set<DocumentInfo> documentInfos) {
        Iterator<DocumentInfo> iterator = result.iterator();
        while (iterator.hasNext()) {
            boolean flag = false;
            DocumentInfo doc = iterator.next();
            for (DocumentInfo documentInfo : documentInfos) {
                if (doc.getName().equals(documentInfo.getName())) {
//                    iterator.remove();
                    flag = true;
                    doc.addScore(documentInfo.getScore());
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
        Iterator<DocumentInfo> it = result.iterator();
        DocumentInfo doc1;
        while (it.hasNext()) {
//        it.forEachRemaining(doc -> {
            DocumentInfo doc = it.next();
            boolean flag = false;
            for (String word : query) {
                if (invertedIndex.map.get(word) == null)
                    continue;
                else if ((doc1 = DocumentInfo.contains(invertedIndex.map.get(word), doc)).getClass() != NullDocumentInfo.class) {
                    doc.addScore(doc1.getScore());
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
        baseResult(queryLists.getEssential());
        checkEssential(queryLists.getEssential());
//        checkForced(true,queryLists.getEssential());
        checkOptional(queryLists.getOptional());
        checkForced(false, queryLists.getForbidden());
        if (result.isEmpty())
            result.add(new DocumentInfo("THERE IS NO DOCUMENT", 0, 0, 0));
    }
}