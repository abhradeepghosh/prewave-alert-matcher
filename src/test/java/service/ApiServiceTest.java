package service;

import java.io.IOException;
import java.util.List;
import model.Alert;
import model.QueryTerm;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;


// import org.junit.jupiter.api.Disabled; // Uncomment to skip if needed

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ApiService using live Prewave endpoints.
 *
 * ⚠️ NOTE: These tests hit live API endpoints.
 * They require internet access and may fail if the API is rate-limited or offline.
 * You can uncomment @Disabled annotations to skip them if needed.
 */
public class ApiServiceTest {

    private static final String API_KEY = "abhradeep:d90ee0f6f7e33455329d00919c362ecfc5d2e92e0679be4265f9b07029ae3e30";
    private static final String QUERY_TERM_URL =
            "https://services.prewave.ai/adminInterface/api/testQueryTerm?key=" + API_KEY;
    private static final String ALERTS_URL =
            "https://services.prewave.ai/adminInterface/api/testAlerts?key=" + API_KEY;

    // @Disabled("Live API call – enable manually for integration testing.")
    @Test
    public void testFetchQueryTermsFromAPI() throws IOException , ParseException {
        ApiService apiService = new ApiService();
        List<QueryTerm> terms = apiService.fetchQueryTerms(QUERY_TERM_URL);

        assertNotNull(terms, "Query terms should not be null.");
        assertFalse(terms.isEmpty(), "Expected non-empty query term list.");

        System.out.println("\n🧪 [TEST] Fetch Query Terms from API");
        System.out.println("→ Endpoint Used : " + QUERY_TERM_URL);
        System.out.println("→ Terms Fetched : " + terms.size());
        System.out.println("→ Sample Output :");
        terms.stream().limit(3).forEach(term ->
            System.out.println("   - " + term.getText() + " (Lang: " + term.getLanguage() + ", keepOrder: " + term.isKeepOrder() + ")")
        );
    }

    // @Disabled("Live API call – enable manually for integration testing.")
    @Test
    public void testFetchAlertsFromAPI() throws IOException , ParseException {
        ApiService apiService = new ApiService();
        List<Alert> alerts = apiService.fetchAlerts(ALERTS_URL);

        assertNotNull(alerts, "Alerts should not be null.");
        assertFalse(alerts.isEmpty(), "Expected non-empty alerts list.");

        System.out.println("\n🧪 [TEST] Fetch Alerts from API");
        System.out.println("→ Endpoint Used : " + ALERTS_URL);
        System.out.println("→ Alerts Fetched: " + alerts.size());
        System.out.println("→ Sample Output :");
        alerts.stream().limit(2).forEach(alert -> {
            System.out.println("   - Alert ID: " + alert.getId());
            alert.getContents().stream().limit(1).forEach(content ->
                System.out.println("     • Text: " + content.getText() + " (Lang: " + content.getLanguage() + ", Type: " + content.getType() + ")")
            );
        });
    }
}
