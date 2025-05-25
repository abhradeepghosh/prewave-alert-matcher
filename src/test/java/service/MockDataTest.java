package service;

import java.util.List;
import java.util.Set;
import model.Alert;
import model.QueryTerm;
import org.junit.jupiter.api.Test;
import util.TestDataLoader;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test using mock data loaded from JSON files.
 */
public class MockDataTest {

    @Test
    public void testMockMatchIntegration() {
        List<QueryTerm> terms = TestDataLoader.loadMockQueryTerms("mock-query-terms.json");
        List<Alert> alerts = TestDataLoader.loadMockAlerts("mock-alerts.json");

        MatcherService matcher = new MatcherService();
        Set<String> matches = matcher.findMatches(alerts, terms);

        System.out.println("\n🧪 [Integration Test with Mock Data]");
        System.out.println("→ Query Terms Loaded: " + terms.size());
        System.out.println("→ Alerts Loaded     : " + alerts.size());
        System.out.println("→ Matches Found     : " + matches.size());
        System.out.println("→ Match Results     : ");
        matches.forEach(System.out::println);
        System.out.println("✅ Integration with mock data completed.\n");

        assertNotNull(matches, "Matches result should not be null.");
        assertFalse(matches.isEmpty(), "Expected at least one match from mock data.");
    }
}