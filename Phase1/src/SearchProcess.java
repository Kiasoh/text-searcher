import java.util.*;

public class SearchProcess {
    private InvertedIndex invertedIndex;
    private QueryLists queryLists = new QueryLists();
    private Set<String> ans;

    public SearchProcess(String query, InvertedIndex invertedIndex, ReadPrinciple readPrinciple) {
        this.invertedIndex = invertedIndex;
        queryLists.categorization(query.split(" "), readPrinciple);
    }

    public Set<String> getAns() {
        search();
        return ans;
    }

    public boolean isThereFiles(String[] fileNames) {
        return (fileNames == null || fileNames.length == 0);
    }

    private Iterator<String> setIterator() {
        return ans.iterator();
    }

    private boolean isEssentialWaste(String word, String doc) {
        return (invertedIndex.map.get(word) == null || !invertedIndex.map.get(word).contains(doc));
    }

    private boolean isOptionalWaste(Boolean flag) {
        return (!flag && queryLists.getOptional().size() != 0);
    }

    public void checkForced(boolean isEssential, ArrayList<String> query) {
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

    public void checkOptional() {
        Iterator<String> it = setIterator();
        boolean flag;
        while (it.hasNext()) {
            flag = false;
            String doc = it.next();
            for (String word : queryLists.getOptional()) {
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
        String[] fileNames = FileReaderClass.getFilesName();
        if (isThereFiles(fileNames))
            ans.add("THERE IS NO FILE");
        ans = new HashSet<>(Arrays.asList(fileNames));
        checkForced(true, queryLists.getEssential());
        checkOptional();
        checkForced(false, queryLists.getForbidden());
    }
}