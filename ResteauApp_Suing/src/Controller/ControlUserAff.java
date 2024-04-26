package Controller;



import DAO.DAOUser;

public class ControlUserAff {

    public static Boolean updateUser(int id, String newName, String newPassword) {
    	return DAOUser.executeUpdateUser(id,newName,newPassword);
    }

    public static Boolean deleteUser(int id) {
        return DAOUser.executeDelete(id);
    }

   
}
