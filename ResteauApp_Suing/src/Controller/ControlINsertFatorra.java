package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import DAO.DAOINsertFatorra;

public class ControlINsertFatorra {
    public  ControlINsertFatorra(double total) {
    
            Boolean ok;
			try {
				ok = DAOINsertFatorra.executeInsert((float) total);
				 if(ok) {
		                JOptionPane.showMessageDialog(null, "succes to add to casier: " );
		            }
		            else {
		                JOptionPane.showMessageDialog(null, "failed to add to casier: " );

		            }

			} catch (Exception e) {
				e.printStackTrace();
			}
           
            
      
    }

    
}
