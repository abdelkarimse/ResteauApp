package DAO;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import Model.Notification;
import Model.Product;

import java.net.URLEncoder;

public class DAONOTIFICATION {

	    public static List<Notification> fetchNotifications() {
	        List<Notification> notificationList = new ArrayList<>();
	        try {
	            URL url = new URL("https://just-shoptn.000webhostapp.com/fetch_notifications.php");
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");

	            int responseCode = conn.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
	                    StringBuilder response = new StringBuilder();
	                    String line;
	                    while ((line = reader.readLine()) != null) {
	                        response.append(line);
	                    }

	                    JSONArray jsonArray = new JSONArray(response.toString());
	                    for (int i = 0; i < jsonArray.length(); i++) {
	                        JSONObject jsonObject = jsonArray.getJSONObject(i);
	                        int idN = jsonObject.getInt("idN");
	                        int id = jsonObject.getInt("id");
	                        int aff = jsonObject.getInt("aff");
	                        int op = jsonObject.getInt("op");
	                        Notification notification = new Notification(idN, id, aff, op);
	                        notificationList.add(notification);
	                    }
	                }
	            } else {
	                System.out.println("HTTP error: " + responseCode);
	            }
	            conn.disconnect();
	        } catch (Exception e) {
	            // Handle exceptions according to your application's needs
	            e.printStackTrace();
	        }
	        return notificationList;
	    }
	

    public static boolean changeaff(int i) {
        try {
            // Create parameters to send to PHP script
            Map<String, String> parameters = new HashMap<>();
            parameters.put("notificationnum", String.valueOf(i));

            // Send HTTP GET request to PHP script
            String response = sendGetRequest("https://just-shoptn.000webhostapp.com/Updatenoti.php", parameters);

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


	public static List<Product>  getListPRODUITnOTtificaton(int id) {
        List<Product> pList = new ArrayList<>();
        try {
            URL url = new URL("https://just-shoptn.000webhostapp.com/produitList.php?idcommand="+id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idString = jsonObject.getInt("id");
                        String idp = String.valueOf(idString);

                        String np = jsonObject.getString("Name");
                        int price = jsonObject.getInt("Price");
                      
                        Product p = new Product(idp , np,price);
                        pList.add(p);
                    }
                }
            } else {
                System.out.println("HTTP error: " + responseCode);
            }
            conn.disconnect();
        } catch (Exception e) {
            // Handle exceptions according to your application's needs
            e.printStackTrace();
        }
        return pList;}
}
