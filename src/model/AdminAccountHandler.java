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
	 * @param username 
	 * @param password
	 * @return true if the parameter password matches the password saved for the user matching the parameter username. Otherwise false.
	 */
	public boolean passwordMatch(String adminname, String password){
		return super.passwordMatch(adminname, password);
	}
	
	/**
	 * Create a new user called 'username' who has password 'password'. UserID is generated automatically for the user.
	 * 
	 * @param username The username for the new user.
	 * @param password The password for the new user.
	 * 
	 * @return boolean True if the user could be created (username not in use). 
	 */
	public boolean createUser(String adminname, String password){
		return super.createUser(adminname, password);
	}
	
	public void removeUser(String adminname){
		super.removeUser(adminname);
	}
	
	public boolean usernameInUser(String adminname){
		return super.usernameInUse(adminname);
	}
	
	public boolean changePassword(String adminname, String oldpassword, String newpassword){
		return super.changePassword(adminname, oldpassword, newpassword);
	}
	
	public boolean changeAdminName(String oldAdminName, String newAdminName){
		return super.changeUsername(oldAdminName, newAdminName);
	}
	
	public ArrayList<String> getUsernameList(){
		return super.getUsernameList();
	}

}


