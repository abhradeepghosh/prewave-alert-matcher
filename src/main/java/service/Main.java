package service;

import java.util.List;
import java.util.Set;
import model.Alert;
import model.QueryTerm;


public class Main {

    public static void main(String[] args) throws Exception {

        // ⚠️ NOTE: For simplicity in this assignment, the API key is hardcoded.
        // In real-world production systems, API keys should be securely stored
        // using environment variables or secret managers.
        String API_KEY = "abhradeep:d90ee0f6f7e33455329d00919c362ecfc5d2e92e0679be4265f9b07029ae3e30";

        // Construct API URLs
        String QUERY_TERM_URL = "https://services.prewave.ai/adminInterface/api/testQueryTerm?key=" + API_KEY;
        String ALERTS_URL = "https://services.prewave.ai/adminInterface/api/testAlerts?key=" + API_KEY;

        // Initialize services
        ApiService apiService = new ApiService();
        MatcherService matcher = new MatcherService();

        // Fetch live data
        List<QueryTerm> terms = apiService.fetchQueryTerms(QUERY_TERM_URL);
        List<Alert> alerts = apiService.fetchAlerts(ALERTS_URL);

        // Match alerts with query terms
        Set<String> matches = matcher.findMatches(alerts, terms);

        // Output results
        System.out.println("\n🌐 [Live Run Output]");
        System.out.println("→ Query Terms Fetched: " + terms.size());
        System.out.println("→ Alerts Fetched     : " + alerts.size());
        System.out.println("→ Matches Found      : " + matches.size());
        System.out.println("\n Matched Alert-Term Pairs:");
        System.out.println(String.join("\n", matches));
        System.out.println("\n✅ Done.\n");
    }
}
