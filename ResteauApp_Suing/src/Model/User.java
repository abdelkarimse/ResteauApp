package Model;

import javax.swing.ImageIcon;

public class User {
	private String username;
    private String password;
    private String role;
    private ImageIcon imageIcon;
    private int id;
	public User(String username, String password, String role, ImageIcon imageIcon, int id) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.imageIcon = imageIcon;
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public ImageIcon getImageIcon() {
		return imageIcon;
	}
	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}