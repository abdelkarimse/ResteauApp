package DAO;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.json.JSONObject;
import Model.CasierEntry;
import Model.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DAOPRODUCTCARD {
	private static final int PAGE_SIZE = 20;




	    public static List<Product> ExecutdisplayProd() {
	        List<Product> productList = new ArrayList<>();

	        try {
	            URL url = new URL("https://just-shoptn.000webhostapp.com/DAOPRODUCTCARD.php");

	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");
	            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            StringBuilder response = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                response.append(line);
	            }
	            reader.close();

	            JSONArray jsonArray = new JSONArray(response.toString());
	            for (int i = 0; i < jsonArray.length(); i++) {
	                JSONObject jsonObject = jsonArray.getJSONObject(i);
	                int idInt = jsonObject.getInt("id");
	                String id = String.valueOf(idInt);
	                String name = jsonObject.getString("name");
	                double price = jsonObject.getDouble("price");
	                double remise = jsonObject.getDouble("remise");
	                String description = jsonObject.getString("description");
	                String category = jsonObject.getString("category");
	                String imageUrlString = jsonObject.getString("image");
	                ImageIcon imageIcon = new ImageIcon("C:\\Users\\DELL\\eclipse-workspace\\tp\\sester\\ProjectSuing\\projetapiserver3\\src\\assets\\"+imageUrlString+".jpg");
	                Product product = new Product(id, name, price, remise, description, category, imageIcon);
	                productList.add(product);
	            }
	            connection.disconnect();
	        } catch (IOException | JSONException e) {
	            e.printStackTrace();
	        }

	        return productList;
	    }

	    public static Object[][] fetchDataFromDatabase() {
	        Object[][] data = null;
	        try {
	            // Establish connection
	            URL url = new URL("https://just-shoptn.000webhostapp.com/fetchDataFromDatabase.php");
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("POST");
	            connection.setDoOutput(true);
	            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            StringBuilder response = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                response.append(line);
	            }
	            reader.close();
	            String[] rows = response.toString().split("\\n");
	            data = new Object[rows.length][4];
	            for (int i = 0; i < rows.length; i++) {
	                String[] columns = rows[i].split(",");
	                for (int j = 0; j < columns.length; j++) {
	                    data[i][j] = columns[j];
	                }
	            }

	            connection.disconnect();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            // Handle exceptions
	        }
	        return data;
	    }
	    public static boolean DaoaddButton(String name, double price, double discount, String description, String category, String imagePath) {
	        try {
	            // Encode parameters
	            String urlParameters  = "name=" + URLEncoder.encode(name, "UTF-8") +
	                                    "&price=" + price +
	                                    "&discount=" + discount +
	                                    "&description=" + URLEncoder.encode(description, "UTF-8") +
	                                    "&category=" + URLEncoder.encode(category, "UTF-8") +
	                                    "&imagePath=" + URLEncoder.encode(imagePath, "UTF-8");

	            // Send POST request to PHP script
	            URL url = new URL("https://just-shoptn.000webhostapp.com/DaoaddButton.php");
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("POST");
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            
	            OutputStream outputStream = connection.getOutputStream();
	            outputStream.write(urlParameters.getBytes());
	            outputStream.flush();
	            outputStream.close();

	            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String response = reader.readLine();
	            reader.close();
	            boolean success = Boolean.parseBoolean(response);
	            connection.disconnect();
	            return success;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return false;
	        }}
	    public static List<CasierEntry> fetchCasierData() {
	        List<CasierEntry> dataList = new ArrayList<>();
	        try {
	            URL url = new URL("https://just-shoptn.000webhostapp.com/fetchCasierData.php");
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("POST");
	            connection.setDoOutput(true);

	            // Read response from PHP script
	            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String line;
	            StringBuilder response = new StringBuilder();
	            while ((line = reader.readLine()) != null) {
	                response.append(line);
	            }
	            reader.close();
	            connection.disconnect();

	            String[] entries = response.toString().split(";");
	            for (String entry : entries) {
	                String[] parts = entry.split(",");
	                if (parts.length == 2) {
	                    String prix = parts[0];
	                    String date = parts[1];
	                    dataList.add(new CasierEntry(prix, date));
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dataList;
	    }
	    public static double fetchTotalPrix() throws Exception {
	        URL url = new URL("https://just-shoptn.000webhostapp.com/fetchTotalPrix.php");
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setDoOutput(true);

	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String inputLine;
	        StringBuilder response = new StringBuilder();
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();

	        return Double.parseDouble(response.toString());
	    }

	    public static void updateNumberOfTables(int newNumberOfTables) {
	        try {
	            URL url = new URL("https://just-shoptn.000webhostapp.com/updateNumberOfTables.php?newNumberOfTables=" + newNumberOfTables);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");
	            connection.setDoOutput(true);
	            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String inputLine;
	            StringBuilder response = new StringBuilder();
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();

	            System.out.println(response.toString());

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
