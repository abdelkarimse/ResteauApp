	package View;
	
	import javax.swing.*;

import Controller.PrductCard1Control;
import Model.Product;
	
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.SQLException;
	
	public class ProductCard1 extends JPanel {
	    private static final long serialVersionUID = 1L;
	
	    private JLabel nameLabel;
	    private JLabel priceLabel;
	    private JLabel descriptionLabel;
	    private JLabel categoryLabel;
	    private JButton ModButton;
	    private JButton removeButton;
	
		
	
	
	
	    public ProductCard1(Product P1) {
	
	        setLayout(new BorderLayout());
	        setSize(150, 300);
	        setBackground(Color.WHITE);
	        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
	
	        JPanel detailsPanel = new JPanel();
	        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
	        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        detailsPanel.setBackground(Color.WHITE);
	
	        nameLabel = new JLabel(P1.getName());
	        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
	        priceLabel = new JLabel(P1.getPrice() + " DT");
	        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
	        priceLabel.setForeground(Color.BLUE);
	        descriptionLabel = new JLabel("Description: " + P1.getDescription());
	        categoryLabel = new JLabel("Category: " + P1.getDescription());
	
	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));       
	        buttonPanel.setBackground(Color.WHITE);
	
	        ModButton = new JButton("Modifier");
	        ModButton.setBackground(Color.GREEN);
	        ModButton.setForeground(Color.WHITE);
	        removeButton = new JButton("Remove from Database");
	        removeButton.setBackground(Color.RED);
	        removeButton.setForeground(Color.WHITE);
	
	        buttonPanel.add(ModButton);
	        buttonPanel.add(removeButton);
	
	        ImageIcon originalIcon = P1.getImageIcon();
	        Image originalImage = originalIcon.getImage();
	        Image scaledImage = originalImage.getScaledInstance(200, 220, Image.SCALE_SMOOTH); 
	        ImageIcon scaledIcon = new ImageIcon(scaledImage);
	        JLabel imageLabel = new JLabel(scaledIcon);
	        imageLabel.setHorizontalAlignment(JLabel.CENTER);
	        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	        detailsPanel.add(nameLabel);
	        detailsPanel.add(priceLabel);
	        detailsPanel.add(descriptionLabel);
	        detailsPanel.add(categoryLabel);
	        detailsPanel.add(buttonPanel);
	
	        add(imageLabel, BorderLayout.CENTER);
	        add(detailsPanel, BorderLayout.SOUTH);
	        ModButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                JFrame modifyFrame = new JFrame("Modify Product");
	                modifyFrame.setLayout(new BorderLayout());
	
	                JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
	                formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	
	                JLabel nameLabel = new JLabel("Name:");
	                JTextField nameField = new JTextField(P1.getName());
	                JLabel priceLabel = new JLabel("Price:");
	                JTextField priceField = new JTextField(String.valueOf(P1.getPrice()));
	                JLabel discountLabel = new JLabel("Discount:");
	                JTextField discountField = new JTextField(String.valueOf(P1.getDiscount()));
	                JLabel categoryLabel = new JLabel("Category:");
	                JTextField categoryField = new JTextField(String.valueOf(P1.getCategory())); 
	                JLabel descriptionLabel = new JLabel("Description:");
	                JTextField descriptionField = new JTextField(String.valueOf(P1.getDescription())); 
	
	                formPanel.add(createFieldPanel(nameLabel, nameField));
	                formPanel.add(createFieldPanel(priceLabel, priceField));
	                formPanel.add(createFieldPanel(discountLabel, discountField));
	                formPanel.add(createFieldPanel(categoryLabel, categoryField));
	                formPanel.add(createFieldPanel(descriptionLabel, descriptionField));
	
	                JButton saveButton = new JButton("Save");
	                saveButton.addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        String newName = nameField.getText();
	                        double newPrice = Double.parseDouble(priceField.getText());
	                        double newDiscount = Double.parseDouble(discountField.getText());
	                        String newCategory = categoryField.getText();
	                        String newDescription = descriptionField.getText();
	                        boolean produitup= PrductCard1Control.updateProduct(new Product( P1.getId(), newName,  newPrice,  newDiscount, newCategory, newDescription, P1.getImageIcon()));
	
	                            if (produitup) {
	                                JOptionPane.showMessageDialog(null, "Product updated successfully!");
	                                modifyFrame.dispose();
	
	                            } else {
	                                JOptionPane.showMessageDialog(null, "Failed to update product.");
	                                modifyFrame.dispose();
	
	                    }
	                    }} );
	                
	                JButton cancelButton = new JButton("Cancel");
	                cancelButton.addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        modifyFrame.dispose();
	                    }
	                });
	
	                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	                buttonPanel.add(saveButton);
	                buttonPanel.add(cancelButton);
	
	                modifyFrame.add(formPanel, BorderLayout.CENTER);
	                modifyFrame.add(buttonPanel, BorderLayout.SOUTH);
	
	                modifyFrame.setSize(400, 300);
	                modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                modifyFrame.setLocationRelativeTo(null);
	                modifyFrame.setVisible(true);
	            }
	        });
	        removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this product from the database?", "Confirmation", JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        boolean removed = PrductCard1Control.removeProduct(P1.getId());
                        if (removed) {
                            JOptionPane.showMessageDialog(null, "Product removed from the database successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to remove product from the database.");
                        }
                    }
                }
            });
	    }
	    
	
	   


		private JPanel createFieldPanel(JLabel label, JComponent field) {
	        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
	        panel.add(label);
	        panel.add(field);
	        return panel;
	    }
	}
