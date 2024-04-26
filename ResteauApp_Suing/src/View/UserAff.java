package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controller.ControlUserAff;
import Model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAff extends JPanel {

	private JPanel panel;
	private final JButton modifyButton;


	public UserAff(User U) {
		panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(100, 100, 100));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		add(panel, BorderLayout.CENTER);

		JPanel detailsPanel = new JPanel(new BorderLayout());
		detailsPanel.setBackground(Color.WHITE);
		detailsPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

		JLabel nameLabel = new JLabel("Name: " +U.getUsername());
		nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
		JLabel passwordLabel = new JLabel("Password: " + U.getPassword());
		JLabel roleLabel = new JLabel("Role: " + U.getRole());
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		textPanel.setBackground(Color.WHITE);
		textPanel.add(nameLabel);
		textPanel.add(passwordLabel);
		textPanel.add(roleLabel);
		detailsPanel.add(textPanel, BorderLayout.CENTER);

		JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		imagePanel.setBackground(Color.WHITE);
		JLabel imageLabel = new JLabel(U.getImageIcon());
		imageLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
		imagePanel.add(imageLabel);
		detailsPanel.add(imagePanel, BorderLayout.WEST);

		modifyButton = new JButton("Modify");
		modifyButton.setBackground(Color.GREEN);
		modifyButton.setForeground(Color.WHITE);
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?",
						"Warning", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
						
						Boolean rowsDeleted = ControlUserAff.deleteUser(U.getId());
						if (rowsDeleted ) {
							JOptionPane.showMessageDialog(null, "User deleted successfully.", "Success",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Failed to delete user.", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					
				}
			}
		});
		modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame modifyFrame = new JFrame("Modify User");
				modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				modifyFrame.setLayout(new BorderLayout());
				modifyFrame.setPreferredSize(new Dimension(400, 200));
				modifyFrame.setResizable(false);

				JPanel modifyPanel = new JPanel();
				modifyPanel.setLayout(new BoxLayout(modifyPanel, BoxLayout.Y_AXIS));
				modifyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

				JLabel nameLabel = new JLabel("Name:");
				JTextField nameField = new JTextField(U.getUsername());
				JLabel passwordLabel = new JLabel("Password:");
				JTextField passwordField = new JTextField(U.getPassword());

				modifyPanel.add(createFieldPanel(nameLabel, nameField));
				modifyPanel.add(createFieldPanel(passwordLabel, passwordField));

				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String newName = nameField.getText();
						String newPassword = passwordField.getText();
						Boolean rowsAffected = ControlUserAff.updateUser(U.getId(), newName, newPassword);
						if (rowsAffected) {
							JOptionPane.showMessageDialog(null, "Number Tabel  updated successfully!");
						} else {
							JOptionPane.showMessageDialog(null, "Failed to Number Tabel.");
						}

						modifyFrame.dispose();
					}
				});

				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						modifyFrame.dispose();
					}
				});

				JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				buttonPanel.setBackground(Color.WHITE);
				buttonPanel.add(saveButton);
				buttonPanel.add(cancelButton);
				modifyPanel.add(buttonPanel);

				modifyFrame.add(modifyPanel, BorderLayout.CENTER);
				modifyFrame.pack();
				modifyFrame.setLocationRelativeTo(null);
				modifyFrame.setVisible(true);
			}
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.add(modifyButton);
		buttonPanel.add(deleteButton);
		detailsPanel.add(buttonPanel, BorderLayout.EAST);
		panel.add(detailsPanel);
	}

	private JPanel createFieldPanel(JLabel label, JComponent field) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(Color.WHITE);
		panel.add(label);
		panel.add(field);
		return panel;
	}

	public JPanel getPanel() {
		return panel;
	}
}
