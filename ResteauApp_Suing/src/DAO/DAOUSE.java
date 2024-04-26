package DAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import Model.User;

public class DAOUSE {

    public static List<User> getAdminUsers() {
        List<User> adminUsers = new ArrayList<>();
        try {
            // Send HTTP POST request to PHP script
            URL url = new URL("https://just-shoptn.000webhostapp.com/getAdminUsers.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Get response from PHP script
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response using org.json
            JSONArray jsonArray = new JSONArray(response.toString());

            // Populate adminUsers list with User objects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                String role = jsonObject.getString("role");
                int id = jsonObject.getInt("id");
                User user = new User(username, password, role, null, id);
                adminUsers.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminUsers;
    }
   


        public static boolean addUser(String username, String password, String role) {
            try {
                // Construct JSON object with user data
                JSONObject userData = new JSONObject();
                userData.put("action", "addUser");
                userData.put("username", username);
                userData.put("password", password);
                userData.put("role", role);

                // Send HTTP POST request to PHP script
                URL url = new URL("https://just-shoptn.000webhostapp.com/addUser.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Send JSON data
                connection.getOutputStream().write(userData.toString().getBytes());

                // Get response from PHP script
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

           

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        
    }

}



