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
	public boolean passwordMatch(String username, String password){
		
		boolean passwordMatches = false;
		
		if(getUser(username) == null){
			System.out.println("User not found");
			passwordMatches = false;
		
		} else {
			if( getUser(username).getElementsByTagName("password") != null ){
				String p = getUser(username).getElementsByTagName("password").item(0).getTextContent();
				
				//TODO Add encryption
				
				if(p.equals(password)){
					passwordMatches = true;
				} else {
					passwordMatches = false;
				}
			}
		}
		
		return passwordMatches;
		
	}
	
	//--------------- CREATE & REMOVE USER -------------------
	public void createUser(){
		
		
		
	}
	
	public void removeUser(){
		
		
		
	}
	
	
	//------------ Is the username already in use? ------------
	public boolean usernameInUse(String username){

		if(getUser(username) != null){
			return false;
		} else {
			return true;
		}
		
	} //usenameInUse()
	
	//--------------- CHANGE PASSWORD ------------------------
	
	public void changePassword(){
		
		
		
	}
	
	
	//--------------- HELP METHODS ----------------------------------
	
	/**
	 * 
	 * @param username
	 * @return Element matching the username. If no match is found, returns null.
	 */
	public Element getUser(String username){
		
		Element foundUser = null;
		
		//Cycle through the users
		for(int i = 0; i < userList.getLength(); i++){
				
			Node userNode = userList.item(i);
				
			if(userNode.getNodeType() == Node.ELEMENT_NODE){
				Element userElement = (Element) userNode;
				
				if(userElement.getElementsByTagName("username").item(0) != null
						&& userElement.getElementsByTagName("username").item(0).getTextContent().equals(username) ){
					foundUser = userElement;
					break;
				}
			}		
		}	
		
		return foundUser;
		
	}
	
	
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
		
	} //getDocument()
	
}
