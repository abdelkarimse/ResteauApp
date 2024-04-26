package DAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUser {

    public static boolean executeUpdateUser(int id, String newName, String newPassword) {
        try {
            // Construct POST data
            String postData = "id=" + id + "&newName=" + URLEncoder.encode(newName, "UTF-8") + "&newPassword=" + URLEncoder.encode(newPassword, "UTF-8");

            // Send POST request to PHP script
            URL url = new URL("https://just-shoptn.000webhostapp.com/userU.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.getOutputStream().write(postData.getBytes());

            // Get response from PHP script
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = in.readLine();
            in.close();

            // Check if update was successful
            return response.equals("success");

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean executeDelete(int id) {
        try {

            String postData = "id=" + id;
            URL url = new URL("https://just-shoptn.000webhostapp.com/deleteU.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.getOutputStream().write(postData.getBytes());

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = in.readLine();
            in.close();
            return response.equals("success");

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public static String getUserRolea(String username, String password) {
        StringBuilder response = new StringBuilder();
        try {
            String query = "https://just-shoptn.000webhostapp.com/Login.php?username=" + username + "&password=" + password;

            URL url = new URL(query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
