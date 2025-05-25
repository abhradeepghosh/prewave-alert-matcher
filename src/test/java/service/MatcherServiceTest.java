package service;

import java.util.List;
import java.util.Set;
import model.Alert;
import model.QueryTerm;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MatcherService's text-matching logic.
 * Covers ordered, unordered, edge, and language-sensitive scenarios.
 */
public class MatcherServiceTest {

    // 🔧 Helper: Create a single-content alert
    private Alert alert(String id, String text, String lang) {
        return new Alert(id, List.of(new Alert.Content(text, lang, "incident")));
    }

    // 🔧 Helper: Create a query term
    private QueryTerm term(int id, String text, String lang, boolean keepOrder) {
        return new QueryTerm(id, text, lang, keepOrder);
    }

    // 🔧 Helper: Log output and assert match count
    private void assertAndLog(Set<String> matches, int expected, String title, String alertText, String termText) {
        System.out.println("\n🧪 [" + title + "]");
        System.out.println("→ Alert Text     : " + alertText);
        System.out.println("→ Query Term     : " + termText);
        System.out.println("→ Expected Match : " + expected);
        System.out.println("→ Actual Match   : " + matches.size());
        System.out.println("→ Matches        : " + matches + "\n");

        assertEquals(expected, matches.size());
    }

    @Test
    public void testFindMatchesWithOrderedMatch() {
        MatcherService matcher = new MatcherService();
        Alert alert = alert("alert1", "factory explosion in Berlin", "en");
        QueryTerm term = term(101, "factory explosion", "en", true);

        Set<String> matches = matcher.findMatches(List.of(alert), List.of(term));
        assertAndLog(matches, 1, "Ordered Match Test", alert.getContents().get(0).getText(), term.getText());
    }

    @Test
    public void testFindMatchesWithUnorderedMatch() {
        MatcherService matcher = new MatcherService();
        Alert alert = alert("alert2", "explosion at a local factory", "en");
        QueryTerm term = term(102, "factory explosion", "en", false);

        Set<String> matches = matcher.findMatches(List.of(alert), List.of(term));
        assertAndLog(matches, 1, "Unordered Match Test", alert.getContents().get(0).getText(), term.getText());
    }

    @Test
    public void testNoMatchDueToLanguageMismatch() {
        MatcherService matcher = new MatcherService();
        Alert alert = alert("alert3", "strike at the plant", "de");
        QueryTerm term = term(103, "strike", "en", true);

        Set<String> matches = matcher.findMatches(List.of(alert), List.of(term));
        assertAndLog(matches, 0, "Language Mismatch Test", alert.getContents().get(0).getText(), term.getText());
    }

    @Test
    public void testUnorderedPartialOverlapShouldNotMatch() {
        MatcherService matcher = new MatcherService();
        Alert alert = alert("alert4", "explosion near the site", "en");
        QueryTerm term = term(104, "factory explosion", "en", false);

        Set<String> matches = matcher.findMatches(List.of(alert), List.of(term));
        assertAndLog(matches, 0, "Unordered Partial Overlap Test", alert.getContents().get(0).getText(), term.getText());
    }

    @Test
    public void testEmptyInputShouldReturnNoMatch() {
        MatcherService matcher = new MatcherService();

        QueryTerm term = term(105, "explosion", "en", true);
        Alert alert = alert("alert5", "factory explosion in city", "en");

        Set<String> result1 = matcher.findMatches(List.of(), List.of(term));
        Set<String> result2 = matcher.findMatches(List.of(alert), List.of());

        assertAndLog(result1, 0, "Empty Alerts List Test", "[EMPTY]", term.getText());
        assertAndLog(result2, 0, "Empty Terms List Test", alert.getContents().get(0).getText(), "[EMPTY]");
    }

    @Test
    public void testMultipleOccurrencesShouldStillMatchOnce() {
        MatcherService matcher = new MatcherService();
        Alert alert = alert("alert6", "explosion explosion explosion at the factory", "en");
        QueryTerm term = term(106, "factory explosion", "en", false);

        Set<String> matches = matcher.findMatches(List.of(alert), List.of(term));
        assertAndLog(matches, 1, "Multiple Occurrences Match Test", alert.getContents().get(0).getText(), term.getText());
    }

    @Test
    public void testCaseInsensitiveMatching() {
        MatcherService matcher = new MatcherService();
        Alert alert = alert("alert7", "FACTORY EXPLOSION reported downtown", "en");
        QueryTerm term = term(107, "factory explosion", "en", true);

        Set<String> matches = matcher.findMatches(List.of(alert), List.of(term));
        assertAndLog(matches, 1, "Case Insensitivity Test", alert.getContents().get(0).getText(), term.getText());
    }
}
