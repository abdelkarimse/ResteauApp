package DAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Model.Product;

public class DAOPRODUCTCARD1 {
    static String phpScriptUrl = "https://just-shoptn.000webhostapp.com/ExecuteProduct.php";

    public static Boolean ExecuteupdateProduct(Product P) {
        try {
            URL url = new URL(phpScriptUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            Map<String, String> parameters = new HashMap<>();
            parameters.put("action", "updateProduct");
            parameters.put("id", P.getId());
            parameters.put("Name", P.getName());
            parameters.put("Price", String.valueOf(P.getPrice()));
            parameters.put("Remise", String.valueOf(P.getDiscount()));
            parameters.put("Category", P.getCategory());
            parameters.put("Description", P.getDescription());

            // Send data
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(param.getKey());
                postData.append('=');
                postData.append(param.getValue());
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            conn.getOutputStream().write(postDataBytes);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


            return true; // Assuming success
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean ExecuteremoveProduct(String id) {
        try {
            URL url = new URL(phpScriptUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Construct data to send
            Map<String, String> parameters = new HashMap<>();
            parameters.put("action", "removeProduct");
            parameters.put("id", id);

            // Send data
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(param.getKey());
                postData.append('=');
                postData.append(param.getValue());
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            conn.getOutputStream().write(postDataBytes);

            // Get response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


            return true; // Assuming success
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
