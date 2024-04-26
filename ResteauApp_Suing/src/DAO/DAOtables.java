package DAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class DAOtables {
    public static int DaoNumerotab() {
        try {
            // Create parameters to send to PHP script
            Map<String, String> parameters = new HashMap<>();
            parameters.put("action", "getNumerotab");

            // Send HTTP GET request to PHP script
            String response = sendGetRequest("https://just-shoptn.000webhostapp.com/DAOtables.php", parameters);

            // Parse response
            return Integer.parseInt(response.trim());

        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Return default value on error
        }
    }

    public static boolean DaoVerifTab(int i) {
        try {
            // Create parameters to send to PHP script
            Map<String, String> parameters = new HashMap<>();
            parameters.put("action", "verifyTab");
            parameters.put("tableId", String.valueOf(i));

            // Send HTTP GET request to PHP script
            String response = sendGetRequest("https://just-shoptn.000webhostapp.com/DAOtables.php", parameters);

            // Parse response
            return Boolean.parseBoolean(response.trim());

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return default value on error
        }
    }

    private static String sendGetRequest(String url, Map<String, String> parameters) throws Exception {
        // Construct URL with parameters
        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);

        // Send HTTP GET request
        URL obj = new URL(urlBuilder.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        // Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
