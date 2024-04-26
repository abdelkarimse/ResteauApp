package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DAO.DAOPRODUCTCARD;
import Model.CasierEntry;
import Model.Product;
import View.ProductCard1;

public class ProductController implements ProductManagement{

	@Override
	public JScrollPane displayProd() {
	    JPanel productPanel = new JPanel(new GridLayout(0,3,10,10));
	    JScrollPane scrollPane = new JScrollPane(productPanel);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400)); 

	    List<Product> productList = DAOPRODUCTCARD.ExecutdisplayProd(); 

	    for (Product product : productList) {
	    	
	        ProductCard1 productCard1 = new ProductCard1(product);
	        productPanel.add(productCard1);
	    }

	    return scrollPane;
	}

   
	@Override
    public JPanel affichstock() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] columnNames = {"Produit", "Quantité (pièces)", "Quantité en Kg", "Prix (DT)"};
        Object[][] data = DAOPRODUCTCARD.fetchDataFromDatabase();
        JTable table = new JTable(data, columnNames);
                table.setFillsViewportHeight(true);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                table.setDefaultRenderer(Object.class, centerRenderer);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

	@Override

	  public JPanel getAddProductPanel() {
	        JPanel addProductPanel = new JPanel(new BorderLayout());
	        addProductPanel.setPreferredSize(new Dimension(500, 400));
	        addProductPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 10));
	        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

	        JLabel nameLabel = new JLabel("Name:");
	        JTextField nameField = new JTextField(20);
	        JLabel priceLabel = new JLabel("Price:");
	        JTextField priceField = new JTextField(20);
	        JLabel discountLabel = new JLabel("Discount:");
	        JTextField discountField = new JTextField(20);
	        JLabel imageLabel = new JLabel("Image:");
	        JTextField imageField = new JTextField(20);
	        JLabel descriptionLabel = new JLabel("Description:");
	        JTextField descriptionField = new JTextField(20);
	        JLabel categoryLabel = new JLabel("Category:");
	        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Fast Food", "Cafe", "Plat"});

	        inputPanel.add(createFieldPanel(nameLabel, nameField));
	        inputPanel.add(createFieldPanel(priceLabel, priceField));
	        inputPanel.add(createFieldPanel(discountLabel, discountField));
	        inputPanel.add(createFieldPanel(imageLabel, imageField));
	        inputPanel.add(createFieldPanel(descriptionLabel, descriptionField));
	        inputPanel.add(createFieldPanel(categoryLabel, categoryComboBox));

	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	        JButton uploadButton = new JButton("Upload Image");
	        JButton addButton = new JButton("Add Product");

	        uploadButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
                    String expectedPath = "C:\\Users\\DELL\\eclipse-workspace\\tp\\sester\\ProjectSuing\\projetapiserver3\\src\\assets\\";
	                JFileChooser fileChooser = new JFileChooser(expectedPath);
	                fileChooser.setCurrentDirectory(new File(expectedPath));
	                int returnValue = fileChooser.showOpenDialog(null);
	                if (returnValue == JFileChooser.APPROVE_OPTION) {
	                    File selectedFile = fileChooser.getSelectedFile();
	                    String imagePath = selectedFile.getAbsolutePath();
	                    if (imagePath.toLowerCase().startsWith(expectedPath.toLowerCase()) && imagePath.toLowerCase().endsWith(".jpg")) {
	                        String imageName = new File(imagePath).getName();
	                        String imageNameWithoutExtension = imageName.substring(0, imageName.lastIndexOf('.'));
	                        	                        imageField.setText(imageNameWithoutExtension);
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Please select a JPG file from the correct directory.", "Invalid File", JOptionPane.ERROR_MESSAGE);
	                    }

	                }
	            }
	        });

	        addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String name = nameField.getText();
	            	double price = priceField.getText().isEmpty() ? 0.0 : Double.parseDouble(priceField.getText());
	            	double discount = discountField.getText().isEmpty() ? 0.0 : Double.parseDouble(discountField.getText());
	            	String description = descriptionField.getText();
	            	String category = (String) categoryComboBox.getSelectedItem();
	            	String imagePath = imageField.getText();
	            	name = name.isEmpty() ? "Default Name" : name;
	            	description = description.isEmpty() ? "" : description;
	            	category = category == null ? "Cafe" : category;
	            	imagePath = imagePath.isEmpty() ? "10" : imagePath;
	                Boolean ok=DAOPRODUCTCARD.DaoaddButton(name,price,discount,description,category,imagePath);
	                if(ok) {
	    		        JOptionPane.showMessageDialog(null, "succes to add data" );

	                	
	                }else {
	    		        JOptionPane.showMessageDialog(null, "Failed to add data" );

	                }
	                nameField.setText("");
	                priceField.setText("");
	                discountField.setText("");
	                descriptionField.setText("");
	                imageField.setText("");
	            }
	        });

	        buttonPanel.add(uploadButton);
	        buttonPanel.add(addButton);

	        addProductPanel.add(inputPanel, BorderLayout.NORTH);
	        addProductPanel.add(buttonPanel, BorderLayout.CENTER);
	        return addProductPanel;
	    }

	  private static JPanel createFieldPanel(JLabel label, JComponent field) {
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(Color.WHITE);
	        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 0)); 
	        centerPanel.setBackground(Color.WHITE);
	        centerPanel.add(label);
	        centerPanel.add(field);

	        panel.add(centerPanel, BorderLayout.CENTER);

	        return panel;
	    }
		@Override
	  public JPanel affichiercassier() {
		    JPanel Gstock = new JPanel(new BorderLayout());
		    JPanel tablePanel = new JPanel(new BorderLayout());
		    Gstock.add(tablePanel, BorderLayout.CENTER);
		    DefaultTableModel tableModel = new DefaultTableModel();
		    JTable table = new JTable(tableModel);
		    JScrollPane scrollPane = new JScrollPane(table);
		    tablePanel.add(scrollPane, BorderLayout.CENTER);
		    tableModel.addColumn("Prix");
		    tableModel.addColumn("Date");

		 List<CasierEntry> dataList;
		 dataList = DAOPRODUCTCARD.fetchCasierData();
		 for (CasierEntry entry : dataList) {
		     tableModel.addRow(new Object[]{entry.getPrix(), entry.getDate()});
		 }
		    JPanel totalPrixPanel = new JPanel();
		    totalPrixPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		    totalPrixPanel.setBackground(Color.WHITE);
		    JLabel totalPrixLabel = new JLabel();
		    totalPrixPanel.add(totalPrixLabel);
		    Gstock.add(totalPrixPanel, BorderLayout.NORTH);

		    double totalPrix;
		    try {
		        totalPrix = DAOPRODUCTCARD.fetchTotalPrix();
		        totalPrixLabel.setText("Total Prix: " + totalPrix + " DT");
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Failed to fetch total prix: " + ex.getMessage());
		    } catch (Exception e1) {
		        JOptionPane.showMessageDialog(null, "error: " + e1.getMessage());
				e1.printStackTrace();
			}

		    JButton changeTablesButton = new JButton("Change Number of Tables");
		    changeTablesButton.addActionListener(e -> {
		        String input = JOptionPane.showInputDialog(null, "Enter the new number of tables:");
		        if (input != null && !input.isEmpty()) {
		            try {
		                int newNumberOfTables = Integer.parseInt(input);
		                DAOPRODUCTCARD.updateNumberOfTables(newNumberOfTables);
		            } catch (NumberFormatException ex) {
		                JOptionPane.showMessageDialog(null, "Invalid input: Please enter a valid number.");
		            }
		        }
		    });

		    Gstock.add(changeTablesButton, BorderLayout.SOUTH);


		    return Gstock;
		}

}

	
	


