package Controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DAO.DAOUSE;
import DAO.DAOUser;
import Model.User;
import View.UserAff;

public class UserController implements UserManagement{
	public JPanel  displayUsers() {
        JPanel productPanel = new JPanel(new BorderLayout());
        JPanel userButtonsPanel = new JPanel(new FlowLayout());
        JPanel viewUsersPanel = new JPanel(new BorderLayout());
        viewUsersPanel.setLayout(new BoxLayout(viewUsersPanel, BoxLayout.Y_AXIS));
        viewUsersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        List<User> adminUsers =DAOUSE.getAdminUsers();
		for (User adminUser : adminUsers) {
		    String name = adminUser.getUsername();
		    String password = adminUser.getPassword();
		    String role = adminUser.getRole();
		    int id = adminUser.getId();
		    ImageIcon imageIcon = new ImageIcon("C:/Users/DELL/Downloads/user.png");
		    Image scaledImage = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		    imageIcon = new ImageIcon(scaledImage);
		    UserAff userAff = new UserAff(new User(name, password, role, imageIcon, id));

		    JPanel userPanel = userAff.getPanel();
		    viewUsersPanel.add(userPanel);
		}

      

            JButton newUserButton = new JButton("Add users");
            newUserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("Add User");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel formPanel = new JPanel(new GridLayout(4, 2));
                    JTextField usernameField = new JTextField(20);
                    JPasswordField passwordField = new JPasswordField(20);
                    String[] roles = {"admin", "user"};
                    JComboBox<String> roleComboBox = new JComboBox<>(roles);

                    formPanel.add(new JLabel("Username:"));
                    formPanel.add(usernameField);
                    formPanel.add(new JLabel("Password:"));
                    formPanel.add(passwordField);
                    formPanel.add(new JLabel("Role:"));
                    formPanel.add(roleComboBox);

                    JButton addUserButton = new JButton("Add user");
                    JButton annulerUserButton = new JButton("cancel");
                    annulerUserButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
                            frame.dispose();
							
						}
                    	
                    }
                    );
                    addUserButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String username = usernameField.getText();
                            String password = new String(passwordField.getPassword());
                            String role = (String) roleComboBox.getSelectedItem();

                                    if (DAOUSE.addUser(username,password,role)) {
                                    JOptionPane.showMessageDialog(null, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                   
                                    frame.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                         
                        }
                    });

                    formPanel.add(addUserButton);
                    formPanel.add(annulerUserButton);
                    frame.add(formPanel);
                    ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });

            userButtonsPanel.add(newUserButton);
      
        productPanel.add(viewUsersPanel, BorderLayout.CENTER);
        productPanel.add(userButtonsPanel, BorderLayout.SOUTH);
        productPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        productPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        return productPanel;


}
	public static  String getUserRole(String username, String password) {
		return DAOUser.getUserRolea(username, password);
       }
	
    }