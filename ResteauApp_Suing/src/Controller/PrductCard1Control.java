package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAO.DAOPRODUCTCARD1;
import Model.Product;
public class PrductCard1Control {

	    public static boolean updateProduct(Product P) {
			return DAOPRODUCTCARD1.ExecuteupdateProduct( P) ;
	    }
	    public static boolean removeProduct(String id) {
	    	DAOPRODUCTCARD1.ExecuteremoveProduct(id);
			return false;
	    }

}
