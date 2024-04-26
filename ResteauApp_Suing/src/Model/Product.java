package Model;

import javax.swing.ImageIcon;

public class Product {
	private String id;
    private String name;
    private double price;
    private double discount;
    private String description;
    private String Category;
    private  ImageIcon imageIcon;
	public Product(String id, String name, double price, double discount, String description, String category,
			ImageIcon imageIcon) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.description = description;
		Category = category;
		this.imageIcon = imageIcon;
	}
	public Product(String id2, String np, int price2) {
		super();
		this.id = id2;
		this.name = np;
		this.price = price2;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public ImageIcon getImageIcon() {
		return imageIcon;
	}
	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

}