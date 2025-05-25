package service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Alert;
import model.QueryTerm;


public class MatcherService {

    /**
     * Matches alerts to query terms based on language, text, and keepOrder flags.
     *
     * @param alerts List of alerts fetched from the API
     * @param terms List of query terms fetched from the API
     * @return A set of stringified match results in the format { "alert_id": "...", "term_id": ... }
     */
    public Set<String> findMatches(List<Alert> alerts, List<QueryTerm> terms) {
        Set<String> matches = new HashSet<>();

        for (Alert alert : alerts) {
            for (Alert.Content content : alert.getContents()) {
                matches.addAll(findMatchesForContent(alert.getId(), content, terms));
            }
        }

        return matches;
    }

    /**
     * Finds matching query terms for a single alert content block.
     */
    private Set<String> findMatchesForContent(String alertId, Alert.Content content, List<QueryTerm> terms) {
        Set<String> matches = new HashSet<>();
        String contentText = content.getText().toLowerCase();
        String contentLang = content.getLanguage();

        for (QueryTerm term : terms) {
            if (!term.getLanguage().equalsIgnoreCase(contentLang)) continue;

            String termText = term.getText().toLowerCase();

            if (term.isKeepOrder()) {
                // Order-sensitive match
                if (contentText.contains(termText)) {
                    matches.add(formatMatch(alertId, term.getId()));
                }
            } else {
                // Order-agnostic match
                Set<String> termWords = new HashSet<>(List.of(termText.split("\\s+")));
                Set<String> contentWords = new HashSet<>(List.of(contentText.split("\\s+")));

                if (contentWords.containsAll(termWords)) {
                    matches.add(formatMatch(alertId, term.getId()));
                }
            }
        }

        return matches;
    }

    /**
     * Formats the match result as a JSON-style string.
     */
    private String formatMatch(String alertId, int termId) {
        return String.format("{\"alert_id\":\"%s\", \"term_id\":%d}", alertId, termId);
    }
}
