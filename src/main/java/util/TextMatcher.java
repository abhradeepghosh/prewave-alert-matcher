package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for text matching logic.
 */
public class TextMatcher {

    /**
     * Checks whether the alert text matches the query text based on the order requirement.
     *
     * @param alertText The full alert text.
     * @param queryText The query text to match.
     * @param keepOrder True if word order should be preserved; false otherwise.
     * @return true if matched, false otherwise.
     */
    public static boolean isMatch(String alertText, String queryText, boolean keepOrder) {
        if (keepOrder) {
            return alertText.toLowerCase().contains(queryText.toLowerCase());
        }

        Set<String> alertWords = new HashSet<>(Arrays.asList(alertText.toLowerCase().split("\\s+")));
        Set<String> queryWords = new HashSet<>(Arrays.asList(queryText.toLowerCase().split("\\s+")));
        return alertWords.containsAll(queryWords);
    }
}
