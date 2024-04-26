package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.DAOtables;

public class Controltable {

    public static int numerotab() {
        int num = 0;
        num = DAOtables.DaoNumerotab();
        return num;
    }

   

    public static Boolean veriftab(int i) {
        return DAOtables.DaoVerifTab(i);
    }

 
}
