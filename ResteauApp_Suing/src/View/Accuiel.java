package View;

import javax.swing.*;

import Controller.ProductManagement;
import Controller.UserController;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Accuiel extends JFrame implements ActionListener {
  
	
	private JTextField usernameField;
    private JPasswordField passwordField;
	




    public Accuiel() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(600, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color2 = new Color(25, 25, 25);
                Color color1 = new Color(0xFF5E00); 
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        setResizable(false);
        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBounds(270, 30, 100, 40);
        panel.add(title);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(140, 300, 80, 25);
        panel.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(140, 360, 80, 25);
        panel.add(passwordLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(230, 300, 200, 25);
        setPlaceholder(usernameField, "Enter username");
        panel.add(usernameField);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(230, 360, 200, 25);
        setPlaceholder(passwordField, "Enter password");
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(220, 420, 100, 30);
        loginButton.addActionListener(this);
        loginButton.setBackground(new Color(46, 204, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(39, 174, 96));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(46, 204, 113));
            }
        });
        panel.add(loginButton);
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(340, 420, 100, 30);
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setBackground(new Color(231, 76, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(192, 57, 43));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(231, 76, 60));
            }
        });
        panel.add(exitButton);

        ImageIcon profileImage = new ImageIcon("C:/Users/DELL/Downloads/chef.png");
        int originalWidth = profileImage.getIconWidth();
        int originalHeight = profileImage.getIconHeight();
        
        int newWidth =300; 
        int newHeight = 400;
                Image scaledImage = profileImage.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(260, 100, 400, 400);
        panel.add(imageLabel);

        add(panel);
        setVisible(true);
    }

    private void setPlaceholder(JTextField field, String text) {
        field.setForeground(Color.GRAY);
        field.setText(text);
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(text)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(text);
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String role;
		try {
			role = UserController.getUserRole(username, password);
			 if (role.equals("admin")) {
                 Dashborad dashboard1 = new Dashborad();
                 JOptionPane.showMessageDialog(dashboard1, "Login Successful!");
                 dashboard1.setVisible(true);
                 dispose();
             } else if (role.equals("superadmin")) {
             	
             	 Dashboard2 dashboard2= new Dashboard2();
                 JOptionPane.showMessageDialog(dashboard2, "Login Successful!");
                 dashboard2.setVisible(true);
                 dispose();
             } else {
                 JOptionPane.showMessageDialog(this, "Invalid role. Please contact administrator.");
             }

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
                       
    }

}
