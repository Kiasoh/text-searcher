import java.util.*;

/**
 * do the search with processed inverted index and processed query
 */
public class SearchProcess {
    private final InvertedIndex invertedIndex;
    private final QueryLists queryLists;
    private Set<String> ans;

    public SearchProcess(String query, InvertedIndex invertedIndex,
                         ReadPrinciple readPrinciple, IFileReader fileReader) {
        this.invertedIndex = invertedIndex;
        queryLists = new QueryLists();
        queryLists.categorization(query.split("\s+"), readPrinciple);
        ans = new HashSet<>(fileReader.getFilesName());
    }

    public Set<String> getAns() {
        search();
        return ans;
    }

    private Iterator<String> setIterator() {
        return ans.iterator();
    }

    private boolean isOptionalWaste(Boolean flag) {
        return (!flag && queryLists.getOptional().size() != 0);
    }

    private void checkEssential(ArrayList<String> query) {
        if(query.isEmpty())
            return;
        ans = invertedIndex.map.get(query.get(0));
        if(ans == null)
            ans = new HashSet<>();
        for (String word : query){
            HashSet<String> docs = invertedIndex.map.get(word);
            if (docs == null)
                continue;
            ans.retainAll(docs);
        }
    }

    private boolean isEssentialWaste(String word, String doc) {
        return (invertedIndex.map.get(word) == null || !invertedIndex.map.get(word).contains(doc));
    }

    private void checkForced(boolean isEssential, ArrayList<String> query) {
        Iterator<String> it = setIterator();

        while (it.hasNext()) {
            String doc = it.next();

            for (String word : query) {
                if (isEssentialWaste(word, doc) == isEssential) {
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
            if (isOptionalWaste(flag)) {
                it.remove();
            }
        }
    }

    private void search() {
        checkEssential(queryLists.getEssential());
//        checkForced(true,queryLists.getEssential());
        checkForced(false, queryLists.getForbidden());
        checkOptional(queryLists.getOptional());
        if (ans.isEmpty())
            ans.add("THERE IS NO FILE");
    }
}