package model;

import java.util.ArrayList;
//import org.jasypt.util.text.BasicTextEncryptor;


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
	
	public boolean passwordMatch(String username, String password){
		return super.passwordMatch(username, password);
	}
	
	public void createUser(String username, String password){
		super.createUser(username, password);
	}
	
	public void removeUser(String username){
		super.removeUser(username);
	}
	
	public boolean usernameInUser(String username){
		return super.usernameInUse(username);
	}
	
	public boolean changeUsername(String oldUsername, String newUsername){
		return super.changeUsername(oldUsername, newUsername);
	}
	
	public boolean changePassword(String username, String oldPassword, String newPassword){
		return super.changePassword(username, oldPassword, newPassword);
	}
	
	public ArrayList<String> getUsernameList(){
		return super.getUsernameList();
	}

}

