import java.util.*;

/**
 * do the search with processed inverted index and processed query
 */
public class SearchProcess {

    private final InvertedIndex invertedIndex;
    private final QueryLists queryLists;
    private Set<String> result;

    public SearchProcess(String query, InvertedIndex invertedIndex,
                         ReadPrinciple readPrinciple, FileScanner fileReader) {
        this.invertedIndex = invertedIndex;
        queryLists = new QueryLists();
        queryLists.categorization(query.split("\s+"), readPrinciple);
        result = new HashSet<>(fileReader.getFilesName());
    }

    public Set<String> getResult() {
        search();
        return result;
    }

    private Iterator<String> setIterator() {
        return result.iterator();
    }

    private boolean containsOptional(Boolean flag) {
        return (!flag && queryLists.getOptional().size() != 0);
    }

    private void checkEssential(ArrayList<String> query) {
        if(query.isEmpty())
            return;

        for (String word : query){
            if (invertedIndex.map.containsKey(word)) {
                result.retainAll(invertedIndex.map.get(word));
            }
            else{
                result = new HashSet<>();
                return;
            }
        }
    }

    private boolean containsEssential(String word, String doc) {
        return (invertedIndex.map.get(word) == null || !invertedIndex.map.get(word).contains(doc));
    }

    private void checkForced(boolean isEssential, ArrayList<String> query) {
        Iterator<String> it = setIterator();

        while (it.hasNext()) {
            String doc = it.next();

            for (String word : query) {
                if (containsEssential(word, doc) == isEssential) {
                    it.remove();
                    break;
                }
            }
        }
    }

    private void checkOptional(ArrayList<String> query) {
        Iterator<String> it = setIterator();
        boolean flag;
        while (it.hasNext()) {
            flag = false;
            String doc = it.next();
            for (String word : query) {
                if (invertedIndex.map.get(word) == null)
                    continue;
                else if (invertedIndex.map.get(word).contains(doc)) {
                    flag = true;
                    break;
                }
            }
            if (containsOptional(flag)) {
                it.remove();
            }
        }
    }

    private void search() {
        checkEssential(queryLists.getEssential());
//        checkForced(true,queryLists.getEssential());
        checkForced(false, queryLists.getForbidden());
        checkOptional(queryLists.getOptional());
        if (result.isEmpty())
            result.add("THERE IS NO FILE");
    }
}