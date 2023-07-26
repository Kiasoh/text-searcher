import java.util.*;

/**
 * do the search with processed inverted index and processed query
 */
public class SearchProcess {
    private final InvertedIndex invertedIndex;
    private final QueryLists queryLists;
    private Set<ScoreHolder> result;

    public SearchProcess(QueryLists queryLists, InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
        this.queryLists = queryLists;
        ArrayList<ScoreHolder> scoreHolders = new ArrayList<>();
        Document.documents.stream().forEach(doc -> scoreHolders.add(new ScoreHolder(doc)));
        result = new HashSet<>(scoreHolders);
    }

    public Set<ScoreHolder> getResult() {
        result = ScoreHolder.sumScores(result);
        search();
        return result;
    }

    private boolean containsOptional(Boolean flag) {
        return (!flag && queryLists.getOptional().size() != 0);
    }

    private boolean containsEssential(String word, ScoreHolder doc) {
        return (invertedIndex.getMap().get(word) == null ||
                ScoreHolder.contains(invertedIndex.getMap().get(word), doc).getClass() == NullScoreHolder.class);
    }

    private void baseResult(ArrayList<String> query) {
        if (query.isEmpty()) {
            return;
        }
        if (invertedIndex.getMap().containsKey(query.get(0))) {
            result = ScoreHolder.copyDocuments(invertedIndex.getMap().get(query.get(0)));
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
            if (invertedIndex.getMap().containsKey(word)) {
                intersection(invertedIndex.getMap().get(word));
            } else {
                result = new HashSet<>();
                return;
            }
        }
    }

    private void intersection(Set<ScoreHolder> scoreHolders) {
        Iterator<ScoreHolder> iterator = result.iterator();
        while (iterator.hasNext()) {
            boolean flag = false;
            ScoreHolder doc = iterator.next();
            for (ScoreHolder scoreHolder : scoreHolders) {
                if (doc.equals(scoreHolder)) {
//                    iterator.remove();
                    flag = true;
                    doc.addScore(scoreHolder.getScore());
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
        Iterator<ScoreHolder> it = result.iterator();
        ScoreHolder doc1;
        while (it.hasNext()) {
//        it.forEachRemaining(doc -> {
            ScoreHolder doc = it.next();
            boolean flag = false;
            for (String word : query) {
                if (invertedIndex.getMap().get(word) == null)
                    continue;
                else if ((doc1 = ScoreHolder.contains(invertedIndex.getMap().get(word), doc)).getClass()
                        != NullScoreHolder.class) {
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
            result.add(new ScoreHolder(new Document("NO DOCUMENT FOUND" , 0)));
    }
}