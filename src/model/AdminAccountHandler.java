package model;

import java.util.ArrayList;
//import org.jasypt.util.text.BasicTextEncryptor;

public class AdminAccountHandler extends AccountHandler {

	private static final String userFilepath = "src/xmlfiles/admins.xml";
	private static final String userTag = "admin";
	private static final String userIDTag = "adminID";
	private static final String usernameTag = "adminname";
	private static final String passwordTag = "password";

	//>>>> CONSTUCTOR <<<<<
	public AdminAccountHandler(){	
		super(userFilepath, userTag, userIDTag, usernameTag, passwordTag);
	}
	
	/**
	 * Check whether the password given matches the admin's password.
	 * 
	 * @param adminname 
	 * @param password
	 * @return true if the parameter password matches the password saved for the user matching the parameter adminname. Otherwise false.
	 */
	public boolean passwordMatch(String adminname, String password){
		return super.passwordMatch(adminname, password);
	}
	
	/**
	 * Create a new user called 'adminname' who has password 'password'. UserID is generated automatically for the user.
	 * 
	 * @param username The adminname for the new user.
	 * @param password The password for the new user.
	 * 
	 * @return boolean True if the user could be created (adminname not in use). 
	 */
	public boolean createUser(String adminname, String password){
		return super.createUser(adminname, password);
	}
	
	/**
	 * Deletes the admin from the system aka removes information on user in users.xml. 
	 * @param username The adminname of the adminname to be deleted.
	 */
	public void removeUser(String adminname){
		super.removeUser(adminname);
	}
	
	/**
	 * Checks if a user in the system already has the given adminname.
	 * @param adminname
	 * @return True if adminname is already in use. Else false.
	 */
	public boolean usernameInUser(String adminname){
		return super.usernameInUse(adminname);
	}
	
	/**
	 * Change admin's password. 
	 * 
	 * @param adminname The adminname of the admin.
	 * @param newpassword New password for the admin.
	 * 
	 * @return True if the password could be changed. Else false.
	 */
	public boolean changePasswordAdmin(String adminname, String oldpassword){
		return super.changePasswordAdmin(adminname, oldpassword);
	}
	
	/**
	 * Returns true if adminname can be and was changed.
	 * @param oldAdminName Admin's old name
	 * @param newAdminName New name for admin
	 * @return True if u
	 */
	public boolean changeAdminName(String oldAdminName, String newAdminName){
		return super.changeUsername(oldAdminName, newAdminName);
	}
	
	
	public ArrayList<String> getUsernameList(){
		return super.getUsernameList();
	}

}


