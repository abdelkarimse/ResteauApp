package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 400;

    private JLabel nameLabel;
    private JLabel priceLabel;
    private JButton addButton;
    private JButton removeButton;

    public ProductCard(String productName, double price, double discount, ImageIcon imageIcon) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); 
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        detailsPanel.setBackground(Color.WHITE);

        nameLabel = new JLabel(productName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20)); 
        priceLabel = new JLabel(price + " DT");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18)); 
        priceLabel.setForeground(Color.BLUE);


        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setBackground(Color.WHITE);

        addButton = new JButton("Add to Cart");
        addButton.setBackground(Color.GREEN);
        addButton.setForeground(Color.WHITE);
        removeButton = new JButton("Remove from Cart");
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        detailsPanel.add(nameLabel, BorderLayout.NORTH);
        detailsPanel.add(priceLabel, BorderLayout.CENTER);
        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(imageLabel, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToFatora();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFromFatora();
            }
        });
    }

    private void addToFatora() {
        Dashborad.getFatora().addToFatora(nameLabel.getText(), Double.parseDouble(priceLabel.getText().substring(0, priceLabel.getText().indexOf(" "))));
    }

    private void removeFromFatora() {
        String priceLabelText = priceLabel.getText();
        try {
            double productPrice = Double.parseDouble(priceLabelText.substring(0, priceLabelText.indexOf(" ")));
            String productName = nameLabel.getText();
            Dashborad.getFatora().removeProduct(productName, productPrice);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            System.err.println("Error removing product from Fatora: " + e.getMessage());
        }
    }
}
