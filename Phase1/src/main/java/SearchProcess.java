import lombok.Getter;

import java.util.*;

/**
 * The SearchProcess class is responsible for performing a search process based on
 * given query lists and an inverted index.
 */
@Getter
public class SearchProcess {

    private final InvertedIndex invertedIndex;
    private final QueryLists queryLists;
    private Set<ScoreHolder> result;

    /**
     * Constructs a new SearchProcess object with the specified query lists and inverted index.
     * @param queryLists the query lists containing essential, optional, and forbidden words
     * @param invertedIndex the inverted index used for searching
     */
    public SearchProcess(QueryLists queryLists, InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
        this.queryLists = queryLists;
        ArrayList<ScoreHolder> scoreHolders = new ArrayList<>();
        Document.documents.forEach(doc -> scoreHolders.add(new ScoreHolder(doc)));
        result = new HashSet<>(scoreHolders);
    }

    /**
     * Returns the search result after performing the search process.
     * @return the set of ScoreHolder objects representing the search result
     */
    public Set<ScoreHolder> runSearch() {
        result = ScoreHolder.sumScores(result);
        search();
        return result;
    }

    /**
     * Checks if the query lists contain optional words and the flag is false.
     * @param flag the flag indicating if the search should consider optional words
     * @return true if the query lists contain optional words and the flag is false, false otherwise
     */
    private boolean containsOptional(Boolean flag) {
        return (!flag && queryLists.getOptional().size() != 0);
    }

    /**
     * Checks if the specified word is essential for the given ScoreHolder.
     * @param word the word to check
     * @param doc the ScoreHolder object to check against
     * @return true if the word is essential for the ScoreHolder, false otherwise
     */
    private boolean containsEssential(String word, ScoreHolder doc) {
        return (invertedIndex.getMap().get(word) == null ||
                ScoreHolder.contains(invertedIndex.getMap().get(word), doc).getClass() == NullScoreHolder.class);
    }

    /**
     * Sets the base result by copying the ScoreHolders associated with the first query word from the inverted index.
     * @param query the list of query words
     */
    private void baseResult(ArrayList<String> query) {
        if (query.isEmpty())
            return;
        if (invertedIndex.getMap().containsKey(query.get(0))) {
            result = ScoreHolder.copyScoreHolders(invertedIndex.getMap().get(query.get(0)));
            query.remove(0);
        }
        else
            result = new HashSet<>();
    }

    /**
     * Checks the essential words from the query lists and updates the search result accordingly.
     * @param query the list of query words
     */
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

    /**
     * Intersects the given scoreHolders set with the current search result to update the scores.
     * @param scoreHolders the set of ScoreHolder objects to intersect with the search result
     */
    private void intersection(Set<ScoreHolder> scoreHolders) {
        Iterator<ScoreHolder> iterator = result.iterator();
        while (iterator.hasNext()) {
            boolean flag = false;
            ScoreHolder doc = iterator.next();
            for (ScoreHolder scoreHolder : scoreHolders) {
                if (doc.equals(scoreHolder)) {
                    flag = true;
                    doc.addScore(scoreHolder.getScore());
                    break;
                }
            }
            if (!flag)
                iterator.remove();
        }
    }

    /**
     * Checks for forced words in the query lists and removes ScoreHolders from the search result accordingly.
     * @param isEssential flag indicating if the forced words are essential or forbidden
     * @param query the list of query words
     */
    private void checkForced(boolean isEssential, ArrayList<String> query) {
        result.removeIf(doc -> query.stream().anyMatch(word -> containsEssential(word, doc) == isEssential));
    }

    /**
     * Checks the optional words from the query lists and updates the search result accordingly.
     * @param query the list of query words
     */
    private void checkOptional(ArrayList<String> query) {
        Iterator<ScoreHolder> it = result.iterator();
        ScoreHolder doc1;
        while (it.hasNext()) {
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
        }
    }

    /**
     * Performs the search process by calling different methods to update the search result based on the query lists. 
     */
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