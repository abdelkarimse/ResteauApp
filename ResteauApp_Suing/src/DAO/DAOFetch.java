package DAO;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.Product;

import javax.swing.ImageIcon;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DAOFetch {
    public static List<Product> fetchData() {
        List<Product> dataList = new ArrayList<>();
        try {
            URL url = new URL("https://just-shoptn.000webhostapp.com/Displayprod.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read response from PHP script
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            // Parse JSON array
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int idInt = jsonObject.getInt("id");
                String id = String.valueOf(idInt);
                String name = jsonObject.getString("Name");
                double price = jsonObject.getDouble("Price");
                double discount = jsonObject.getDouble("Remise");
                String description = jsonObject.getString("description");
                String category = jsonObject.getString("category");
                String image = jsonObject.getString("Image");                
                ImageIcon imageIcon = new ImageIcon("C:\\Users\\DELL\\eclipse-workspace\\tp\\sester\\ProjectSuing\\projetapiserver3\\src\\assets\\"+image+".jpg");
                Product product = new Product(id, name, price, discount, description, category, imageIcon);
                dataList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
