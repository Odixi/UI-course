package model;

import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

/**
 * 
 * @author 
 *
 */

public class UserAccountHandler {

	private NodeList userList;
	private Document usersXML;
	
	public UserAccountHandler(){	
		usersXML = getDocument(".src/xmlfiles/users.xml");;
	}

	//------ PASSWORD MATCHING CHECK ----------
	public boolean passwordMatch(String password){
		
		try {
			//Cycle through the users
			//for(){}
		} catch (){
			
		}
		
	}
	
	//
	public void removeUser(){
		
		
		
	}
	
	//
	public void createUser(){
		
		
		
	}
	
	//--------------- HELP METHODS ----------------------------------
	
	private void updateUserList(){
		userList = usersXML.getElementsByTagName("user");
	}
	
	
	private Document getDocument(String filepath) {		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(true);
			
			DocumentBuilder builder = factory.newDocumentBuilder();

			return builder.parse(new InputSource(filepath));
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
	
}
