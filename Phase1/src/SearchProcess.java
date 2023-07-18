import java.util.*;

public class SearchProcess {
    InvertedIndex invertedIndex;
    private QueryLists queryLists = new QueryLists();
    private Set<String> ans;

    public SearchProcess(String query, InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
        queryLists.Categorization(query.split(" "));
    }

    public Set<String> getAns() {
        Search();
        return ans;
    }

    public boolean IsThereFiles(String[] fileNames) {
        return (fileNames == null || fileNames.length == 0);
    }

    private Iterator<String> SetIterator() {
        return ans.iterator();
    }

    private boolean IsEssentialWaste(String word, String doc) {
        return (invertedIndex.map.get(word) == null || !invertedIndex.map.get(word).contains(doc));
    }

    private boolean IsOptionalWaste(Boolean flag) {
        return (!flag && queryLists.getOptional().size() != 0);
    }

    private void CheckForced(boolean isEssential, ArrayList<String> query) {
        Iterator<String> it = SetIterator();
        while (it.hasNext()) {
            String doc = it.next();
            for (String word : query) {
                if (IsEssentialWaste(word, doc) == isEssential) {
                    it.remove();
                    break;
                }
            }
        }
    }

    private void CheckOptional() {
        Iterator<String> it = SetIterator();
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
            if (IsOptionalWaste(flag)) {
                it.remove();
            }
        }
    }

    private void Search() {
        String[] fileNames = FileReaderClass.GetFilesName();
        if (IsThereFiles(fileNames))
            ans.add("THERE IS NO FILE");
        ans = new HashSet<>(Arrays.asList(fileNames));
        CheckForced(true, queryLists.getEssential());
        CheckOptional();
        CheckForced(false, queryLists.getForbidden());
    }
}