package model;

import java.util.ArrayList;
//import org.jasypt.util.text.BasicTextEncryptor;
import java.util.Hashtable;


public class UserAccountHandler extends AccountHandler {

	private static final String userFilepath = "src/xmlfiles/users.xml";
	private static final String userTag = "user";
	private static final String userIDTag = "userID";
	private static final String usernameTag = "username";
	private static final String passwordTag = "password";

	//>>>> CONSTUCTOR <<<<<
	public UserAccountHandler(){	
		super(userFilepath, userTag, userIDTag, usernameTag, passwordTag);
	}
	
	/**
	 * Check whether the password given matches the admin's password.
	 * 
	 * @param adminname 
	 * @param password
	 * @return true if the parameter password matches the password saved for the user matching the parameter adminname. Otherwise false.
	 */
	public boolean passwordMatch(String username, String password){
		return super.passwordMatch(username, password);
	}
	
	/**
	 * Create a new user called 'adminname' who has password 'password'. UserID is generated automatically for the user.
	 * 
	 * @param username The adminname for the new user.
	 * @param password The password for the new user.
	 * 
	 * @return boolean True if the user could be created (adminname not in use). 
	 */
	public boolean createUser(String username, String password){
		return super.createUser(username, password);
	}
	
	/**
	 * Deletes the admin from the system aka removes information on user in users.xml. 
	 * @param username The adminname of the adminname to be deleted.
	 */
	public void removeUser(String username){
		super.removeUser(username);
	}
	
	/**
	 * Checks if a user in the system already has the given adminname.
	 * @param adminname
	 * @return True if adminname is already in use. Else false.
	 */
	public boolean usernameInUser(String username){
		return super.usernameInUse(username);
	}

	/**
	 * Returns true if adminname can be and was changed.
	 * @param oldAdminName Admin's old name
	 * @param newAdminName New name for admin
	 * @return True if u
	 */
	public boolean changeUsername(String oldUsername, String newUsername){
		return super.changeUsername(oldUsername, newUsername);
	}

	/**
	 * Change admin's password. 
	 * 
	 * @param adminname The adminname of the admin.
	 * @param newpassword New password for the admin.
	 * 
	 * @return True if the password could be changed. Else false.
	 */
	public boolean changePassword(String username, String oldPassword, String newPassword){
		return super.changePassword(username, oldPassword, newPassword);
	}
	
	/**
	 * Get list of the admin names
	 * @return ArrayList of the adminnames
	 */
	public ArrayList<String> getUsernameList(){
		return super.getUsernameList();
	}

	/**
	 * Get list of the userIDs and usernames
	 * @return Hashtable where userIDs are the keys and usernames the values.
	 */
	public Hashtable<String, String> getUsers(){
		return super.getUserList();
	}
	
}

