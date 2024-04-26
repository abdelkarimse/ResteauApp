package View;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

import Controller.ProductController;
import Controller.ProductManagement;
import Controller.UserController;
import Controller.UserManagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;


    

class Dashboard2 extends JFrame implements ActionListener {


    private Timer timer;
    
    UserManagement GestionUser = new UserController();
    ProductManagement GestionProduct = new ProductController();

    Dashboard2() {
        super("Dashboard");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int widthOffset = 100;
        int heightOffset = 100;
        setSize(screenWidth - widthOffset, screenHeight - heightOffset);
        setLocationRelativeTo(null);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem viewProd = new JMenuItem("View Products");
        JMenuItem addProd = new JMenuItem("Add Product");
        JMenuItem viewUsers = new JMenuItem("View Users");
        JMenuItem stock = new JMenuItem("stock");
        JMenuItem casier = new JMenuItem("casier");
        viewProd.setActionCommand("View Products");
        addProd.setActionCommand("Add Product");
        viewUsers.setActionCommand("View Users");
        stock.setActionCommand("stock");
        casier.setActionCommand("casier");
        viewProd.addActionListener(this);
        addProd.addActionListener(this);
        viewUsers.addActionListener(this);
        stock.addActionListener(this);
        casier.addActionListener(this);
        menuBar.add(viewProd);
        menuBar.add(addProd);
        menuBar.add(viewUsers);
        menuBar.add(stock);
        menuBar.add(casier);
        setJMenuBar(menuBar);
        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
        JScrollPane refreshedProductPanel = GestionProduct.displayProd();
        setContentPane(new JScrollPane(refreshedProductPanel));
        timer = new Timer(5000, this);
        timer.setActionCommand("RefreshData");
        timer.start();
    }

    String prevCommand = "View Products";

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("RefreshData".equals(command)) {
            switch (prevCommand) {
                case "View Users":
                    JPanel refreshedUsersPanel = GestionUser.displayUsers();
                    setContentPane(new JScrollPane(refreshedUsersPanel));
                    break;
                case "View Products":
                    JScrollPane refreshedProductPanel = GestionProduct.displayProd();
                    setContentPane(new JScrollPane(refreshedProductPanel));
                    break;
                default:
            }
            revalidate();
            repaint();
        } else if (command != null) {
            switch (command) {
                case "View Products":
                    JScrollPane productPanelProducts = GestionProduct.displayProd();
                    setContentPane(new JScrollPane(productPanelProducts));
                    revalidate();
                    repaint();
                    prevCommand = command;
                    break;
                case "View Users":
                    JPanel productPanelUsers = GestionUser.displayUsers();
                    setContentPane(new JScrollPane(productPanelUsers));
                    revalidate();
                    repaint();
                    prevCommand = command;
                    break;
                case "Add Product":
                    JPanel productAddPanel = GestionProduct.getAddProductPanel();
                    setContentPane(new JScrollPane(productAddPanel));
                    revalidate();
                    repaint();
                    prevCommand = command;
                    break;
                case "stock":
                    JPanel stockPanel = GestionProduct.affichstock();
                    setContentPane(new JScrollPane(stockPanel));
                    revalidate();
                    repaint();
                    prevCommand = command;
                    break;
                case "casier":
                    JPanel casier = GestionProduct.affichiercassier();
                    setContentPane(new JScrollPane(casier));
                    revalidate();
                    repaint();
                    prevCommand = command;
                    break;
                default:
                    System.err.println("Unknown command: " + command);
            }
        } else {
            System.err.println("Action command is null");
        }
    }

}
