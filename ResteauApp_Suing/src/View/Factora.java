package View;

import javax.swing.*;

import Controller.ControlINsertFatorra;
import Controller.FactoraInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Factora  extends JPanel implements FactoraInterface  {

    private static final long serialVersionUID = 1L;
    private JTextArea textfact;
    private JButton printButton;
    private ArrayList<String> products;
    private double subtotal;
    private double tax;
    private String Numbertab;

    private double discount;
    private double total;


	



    private StringBuilder factContent = new StringBuilder(); 

    public Factora() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 700));
        setBackground(Color.WHITE);

        products =new ArrayList<>();
        textfact= new JTextArea();
        textfact.setEditable(false);
        textfact.setFont(new Font("Arial", Font.PLAIN, 14)); 
        JScrollPane scrollPane =new JScrollPane(textfact);
        add(scrollPane, BorderLayout.CENTER);

        printButton= new JButton("Imprimer la Facture");
        printButton.setFont(new Font("Arial", Font.BOLD, 14)); 
        printButton.setBackground(Color.GREEN); 
        printButton.setForeground(Color.WHITE); 
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					printFatora();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        add(printButton, BorderLayout.SOUTH);
    }
    @Override
    public void addToFatora(String productName, double price) {
        boolean productExists = false;
        for (int i = 0; i < products.size(); i++) {
            String product = products.get(i);
            if (product.startsWith(productName)) {
                productExists = true;
                int quantityIndex = product.lastIndexOf("x");
                if (quantityIndex != -1) {
                    String quantityStr = product.substring(quantityIndex + 1).trim();
                    try {
                        int quantity = Integer.parseInt(quantityStr.split("\\s+")[0]); 
                        quantity++;
                        double total = price * quantity;
                        products.set(i, productName + " x " + quantity + "    DT" + total);
                        subtotal += price;
                        updatefact();
                        return; 
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing quantity: " + quantityStr);
                        e.printStackTrace();
                    }
                }
            }
        }

        if (!productExists) {
            products.add(productName + " x 1" + "    " + price+" DT");
            subtotal += price;
            updatefact();
        }
    }

    
    public void setTax(double tax) {
        this.tax = tax;
        updatefact();
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        updatefact();
    }

    public void setNumber(String Numrtab) {
        this.Numbertab = Numrtab;
        updatefact();
    }

    public String getNumber() {
       return this.Numbertab ;
    }

    private void updatefact() {
        factContent.setLength(0);
        factContent.append("Facture:\n\n");
        factContent.append("Number Tabel:     ").append(Numbertab).append("\n");

        for (String product : products) {
            factContent.append(product).append("\n");
        }

        factContent.append("\n");

        DecimalFormat df = new DecimalFormat("#.##");

        factContent.append("Sous-total:        ").append(df.format(subtotal)).append(" DT\n");
        factContent.append("Taxe:                 ").append(df.format(tax)).append(" DT\n");
        factContent.append("Remise:           	").append(df.format(discount)).append(" DT\n");
        total = subtotal + tax - discount;
        factContent.append("Total:                 ").append(df.format(total)).append(" DT\n");

        textfact.setText(factContent.toString());
    }
    private void printFatora() throws SQLException {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        if (printerJob.printDialog()) {
            PageFormat pageFormat = printerJob.defaultPage();
            Paper paper = new Paper();
            paper.setSize(0.1 * 72, pageFormat.getHeight()); 
            pageFormat.setPaper(paper);

            printerJob.setPrintable(new Printable() {
                public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
                	
                		 if (pageIndex > 0) {
                        return NO_SUCH_PAGE;
                    }

                		  Graphics2D g2d = (Graphics2D) g;
                    g2d.translate(pf.getImageableX(), pf.getImageableY());
                    
                    String[] lines = factContent.toString().split("\n");
                    int y = 100; 
                    for (String line : lines) {
                        g.drawString(line, 100, y);
                        y += 20; 
                    }
                    return PAGE_EXISTS;
                }
            }, pageFormat);

           
            try { 
            	printerJob.print();
            	ControlINsertFatorra a=new ControlINsertFatorra(total);
                factContent.setLength(0);

            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'impression de la facture : " + ex.getMessage(), "Erreur d'impression", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    @Override
    public void removeProduct(String productName, double productPrice) {
        for (int i = 0; i < products.size(); i++) {
            String product = products.get(i);
            if (product.startsWith(productName)) {
                int quantityIndex = product.lastIndexOf("x");
                if (quantityIndex != -1) {
                    String quantityStr = product.substring(quantityIndex ).trim();

                    String numericPart = quantityStr.split("\\s+")[1];
                    try {
                        int quantity = Integer.parseInt(numericPart);
                        if (quantity > 1) {
                            quantity--;
                            double total = productPrice * quantity;
                            products.set(i, productName + " x " + quantity + "    DT" + total);
                        } else {
                            products.remove(i);
                        }
                        subtotal -= productPrice; 
                        updatefact();
                        return;
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing quantity: " + quantityStr);
                        e.printStackTrace();
                    }
                }
            }
        }
    }



}
