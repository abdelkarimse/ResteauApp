package View;

import javax.swing.*;
import Controller.Controltable;
import DAO.DAOFetch;
import DAO.DAONOTIFICATION;
import Model.Notification;
import Model.Product;

import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Dashborad extends JFrame {
 
    protected static Factora fatora;

    public Dashborad() {
        super("Cashier");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int widthOffset = 100;
        int heightOffset = 100;
        setSize(screenWidth - widthOffset, screenHeight - heightOffset);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel productPanel = new JPanel(new GridLayout(0,3,10,10));
      
        productPanel.setBorder(BorderFactory.createTitledBorder("Fatora"));
        JPanel fatoraContainer = new JPanel(new BorderLayout());
        fatoraContainer.setBorder(BorderFactory.createTitledBorder("Fatora"));
        List<Product> productList;
		try {
			productList = DAOFetch.fetchData();
			  for (Product product : productList) {
		            String name = product.getName();
		            double price = product.getPrice();
		            double remise = product.getDiscount(); 
		            ImageIcon imageIcon = product.getImageIcon(); 
		            Image scaledImage = imageIcon.getImage().getScaledInstance(220, 200, Image.SCALE_SMOOTH);
		            imageIcon = new ImageIcon(scaledImage);
		            ProductCard productCard = new ProductCard(name, price, remise, imageIcon);
		            productCard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		            productPanel.add(productCard);
		        }
		} catch (Exception e) {
		}
	
        fatora = new Factora();
        fatora.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        fatora.addToFatora("Sample Product", 0);
        JPanel Footer = new JPanel(new FlowLayout());
        JLabel tableLabel = new JLabel("Number Table");
        JComboBox<String> tableComboBox = new JComboBox<>();

        Vector<String> tableItems = new Vector<>();
        tableItems.add("Selected Number");
        int num=Controltable.numerotab();
            for (int i = 1; i <= num; i++) {
            	Boolean ok=Controltable.veriftab(i);
            	if(!ok) {
                tableItems.add(String.valueOf(i));}
            }

        tableComboBox.setModel(new DefaultComboBoxModel<>(tableItems));
        fatora.setNumber("0");
        tableComboBox.addActionListener(v -> {
            String selectedValue = (String) tableComboBox.getSelectedItem();
            if (selectedValue != null && !selectedValue.equals("Selected Number") && selectedValue.length() > 0) {
                fatora.setNumber(selectedValue);
            } else {
                fatora.setNumber("0");
            }
        });
        Footer.add(tableLabel);
        Footer.add(tableComboBox);
        JScrollPane scrollPane = new JScrollPane(productPanel);

        mainPanel.add(Footer, BorderLayout.SOUTH);
        fatoraContainer.add(fatora, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(fatoraContainer, BorderLayout.EAST);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                List<Notification> notifications = DAONOTIFICATION.fetchNotifications();
                if (notifications != null && !notifications.isEmpty()) {
                    for (Notification notification : notifications) {
                    	String a=notification.toString();
                    	System.out.println(a);
                        int choice = JOptionPane.showConfirmDialog(null, a, "Notifications", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        if (choice == JOptionPane.OK_OPTION) { 
                            DAONOTIFICATION.changeaff(notification.getIdN());
                            List<Product> pList =DAONOTIFICATION.getListPRODUITnOTtificaton(notification.getId());
                            Factora f=new Factora();
                            for(Product p:pList) {
                            	f.addToFatora(p.getName(), p.getPrice());
                            	
                            }

                            
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No notifications found.", "Notifications", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 3, TimeUnit.MINUTES);
        
        add(mainPanel);
        setVisible(true);
    }

  

    public static Factora getFatora() {
        return fatora;
    }

    
}
