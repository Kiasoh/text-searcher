import java.util.*;

/**
 * do the search with processed inverted index and processed query
 */
public class SearchProcess {
    private InvertedIndex invertedIndex;
    private QueryLists queryLists = new QueryLists();
    private Set<String> ans;

    public SearchProcess(String query, InvertedIndex invertedIndex, ReadPrinciple readPrinciple) {
        this.invertedIndex = invertedIndex;
        queryLists.categorization(query.split(" "), readPrinciple);
        ans = new HashSet<>(FileReaderClass.getFilesName());
    }

    public Set<String> getAns() {
        search();
        return ans;
    }

    public boolean isThereFiles(ArrayList<String> fileNames) {
        return (fileNames == null || fileNames.isEmpty());
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

//    private void checkForbidden(ArrayList<String> query) {
//        Iterator<String> it = setIterator();
//        while (it.hasNext()) {
//            String doc = it.next();
//            for (String word : query) {
//                if (invertedIndex.map.get(word) == null)
//                    continue;
//                if (invertedIndex.map.get(word).contains(doc)) {
//                    it.remove();
//                    break;
//                }
//            }
//        }
//    }

    private void search() {
        ArrayList<String> fileNames = FileReaderClass.getFilesName();
        if (isThereFiles(fileNames))
            ans.add("THERE IS NO FILE");
        ans = new HashSet<>(fileNames);
        checkEssential(queryLists.getEssential());
//        checkForced(true,queryLists.getEssential());
        checkForced(false, queryLists.getForbidden());
        checkOptional(queryLists.getOptional());
//        checkForbidden(queryLists.getForbidden());

    }
}