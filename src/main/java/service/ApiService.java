package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Alert;
import model.QueryTerm;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class ApiService {

    /**
     * Fetches query terms from a given URL and parses them into QueryTerm objects.
     */
    public List<QueryTerm> fetchQueryTerms(String url) throws IOException, ParseException {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        try (CloseableHttpResponse response = (CloseableHttpResponse) client.execute(request)) {
            String json = EntityUtils.toString(response.getEntity());
            JSONArray array = new JSONArray(json);
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
    }

    /**
     * Fetches alerts from a given URL and parses them into Alert objects.
     */
    public List<Alert> fetchAlerts(String url) throws IOException, ParseException {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        try (CloseableHttpResponse response = (CloseableHttpResponse) client.execute(request)) {
            String json = EntityUtils.toString(response.getEntity());
            JSONArray array = new JSONArray(json);
            List<Alert> result = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject alertJson = array.getJSONObject(i);
                String alertId = alertJson.getString("id");

                JSONArray contentArray = alertJson.getJSONArray("contents");
                List<Alert.Content> contentList = new ArrayList<>();

                for (int j = 0; j < contentArray.length(); j++) {
                    JSONObject contentObj = contentArray.getJSONObject(j);
                    contentList.add(new Alert.Content(
                            contentObj.getString("text"),
                            contentObj.getString("language"),
                            contentObj.getString("type")
                    ));
                }

                result.add(new Alert(alertId, contentList));
            }

            return result;
        }
    }
}
