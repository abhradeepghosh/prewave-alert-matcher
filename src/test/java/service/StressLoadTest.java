
package service;

import java.util.List;
import java.util.Set;
import model.Alert;
import model.QueryTerm;
import org.junit.jupiter.api.Test;
import service.ApiService;
import service.MatcherService;
// import org.junit.jupiter.api.Disabled; // Uncomment to skip if needed

import static org.junit.jupiter.api.Assertions.*;

/**
 * Stress test that repeatedly hits live Prewave endpoints to validate stability and variability handling.
 * Program is tested on multiple API calls (around 200 should suffice)."
 */
public class StressLoadTest {

    private static final String API_KEY = "abhradeep:d90ee0f6f7e33455329d00919c362ecfc5d2e92e0679be4265f9b07029ae3e30";
    private static final String QUERY_TERM_URL = "https://services.prewave.ai/adminInterface/api/testQueryTerm?key=" + API_KEY;
    private static final String ALERTS_URL = "https://services.prewave.ai/adminInterface/api/testAlerts?key=" + API_KEY;
    private static final int ITERATIONS = 200;

    @Test
    //@Disabled("Stress test with live API — enable manually when needed")
    public void testStressRunWithLiveAPI() {
        ApiService apiService = new ApiService();
        MatcherService matcherService = new MatcherService();

        int successfulRuns = 0;
        int totalMatches = 0;

        System.out.println("\n🚀 [Stress Load Test: " + ITERATIONS + " Runs]");

        for (int i = 1; i <= ITERATIONS; i++) {
            try {
                List<QueryTerm> terms = apiService.fetchQueryTerms(QUERY_TERM_URL);
                List<Alert> alerts = apiService.fetchAlerts(ALERTS_URL);
                Set<String> matches = matcherService.findMatches(alerts, terms);

                System.out.printf("✔️ Run #%03d → Matches: %d\n", i, matches.size());

                assertNotNull(matches, "Match set should not be null");
                totalMatches += matches.size();
                successfulRuns++;

            } catch (Exception e) {
                System.out.printf("⚠️ Run #%03d → Failed: %s\n", i, e.getMessage());
            }
        }

        System.out.println("\n📊 Summary:");
        System.out.println("→ Total Successful Runs : " + successfulRuns + "/" + ITERATIONS);
        System.out.println("→ Total Matches Found    : " + totalMatches);
        System.out.println("→ Average Matches/Run    : " + (successfulRuns == 0 ? 0 : totalMatches / successfulRuns));
        System.out.println("✅ Stress test complete.\n");

        assertTrue(successfulRuns >= 180, "At least 90% should succeed to ensure API stability.");
    
    }
}
