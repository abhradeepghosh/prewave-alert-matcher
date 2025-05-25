// src/test/java/util/TestDataLoader.java

package util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Alert;
import model.QueryTerm;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utility class to load mock alerts and query terms from JSON files for testing.
 */
public class TestDataLoader {

    /**
     * Loads mock query terms from /resources/mock-query-terms.json
     *
     * @return List of QueryTerm objects. Returns empty list on failure.
     */
    public static List<QueryTerm> loadMockQueryTerms(String resourceName) {
        JSONArray array = readJsonArrayFromResource(resourceName);
        List<QueryTerm> result = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            result.add(new QueryTerm(
                    obj.getInt("id"),
                    obj.getString("text"),
                    obj.getString("language"),
                    obj.getBoolean("keepOrder")
            ));
        }
        return result;
    }

    /**
     * Loads mock alerts from /resources/mock-alerts.json
     *
     * @return List of Alert objects. Returns empty list on failure.
     */
    public static List<Alert> loadMockAlerts(String resourceName) {
        JSONArray array = readJsonArrayFromResource(resourceName);
        List<Alert> result = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String id = obj.getString("id");
            JSONArray contents = obj.getJSONArray("contents");
            List<Alert.Content> contentList = new ArrayList<>();

            for (int j = 0; j < contents.length(); j++) {
                JSONObject content = contents.getJSONObject(j);
                contentList.add(new Alert.Content(
                        content.getString("text"),
                        content.getString("language"),
                        content.getString("type")
                ));
            }

            result.add(new Alert(id, contentList));
        }
        return result;
    }

    /**
    * Reads a JSON array from a resource file in the classpath.
    *
    * @return Parsed JSONArray object
    * @throws RuntimeException if the resource is not found or cannot be read
    */
    private static JSONArray readJsonArrayFromResource(String resourceName) {
        InputStream is = TestDataLoader.class.getClassLoader().getResourceAsStream(resourceName);
        if (is == null) throw new RuntimeException("Resource not found: " + resourceName);

        try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
            String json = scanner.useDelimiter("\\A").next();
            return new JSONArray(json);
        }
    }
}
