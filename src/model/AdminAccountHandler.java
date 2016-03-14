package model;

import java.util.ArrayList;
//import org.jasypt.util.text.BasicTextEncryptor;

public class AdminAccountHandler extends AccountHandler {

	private static final String userFilepath = "src/xmlfiles/admins.xml";
	private static final String userTag = "admin";
	private static final String usernameTag = "adminname";
	private static final String passwordTag = "password";

	//>>>> CONSTUCTOR <<<<<
	public AdminAccountHandler(){	
		super(userFilepath, userTag, usernameTag, passwordTag);
	}
	
	public boolean passwordMatch(String adminname, String password){
		return super.passwordMatch(adminname, password);
	}
	
	public void createUser(String adminname, String password){
		super.createUser(adminname, password);
	}
	
	public void removeUser(String adminname){
		super.removeUser(adminname);
	}
	
	public boolean usernameInUser(String adminname){
		return super.usernameInUse(adminname);
	}
	
	public void changePassword(String adminname, String oldpassword, String newpassword){
		super.changePassword(adminname, oldpassword, newpassword);
	}
	
	public ArrayList<String> getUsernameList(){
		return super.getUsernameList();
	}

}


