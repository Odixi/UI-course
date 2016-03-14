package model;

import org.xml.sax.*;
import org.w3c.dom.*;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 
 * @author 
 *
 */

/* TODO
 * - Method: get list of usernames DONE
 * - 
 */

public class UserAccountHandlerCOPY extends XMLHandler {

	private String filepath = "src/xmlfiles/users.xml";
	private NodeList userList;
	private Document usersXML;
	private Element rootElement;
	private ArrayList<String> usernameList;
	
	//private BasicTextEncryptor cryptor;

	//>>>> CONSTUCTOR <<<<<
	public UserAccountHandlerCOPY(){	
		
		usersXML = getDocument(filepath);
		usersXML.getDocumentElement().normalize();
		rootElement = usersXML.getDocumentElement();

		usernameList = new ArrayList<String>();
		
		//TODO For encrypting and decrypting passwords
		//cryptor = new BasicTextEncryptor();

	} //constructor
	
	
	//------ PASSWORD MATCHING CHECK ----------
	public boolean passwordMatch(String username, String password){
		
		boolean passwordMatches = false;
		
		if(getUser(username) == null){
			System.out.println("User not found");
			passwordMatches = false;
		
		} else {
			if( getUser(username).getElementsByTagName("password") != null ){
				String p = getUser(username).getElementsByTagName("password").item(0).getTextContent();

				//TODO Jasypt has it's own method for checking passwords. Take a look.
				
				//TODO if( cryptor.decrypt(p).equals( cryptor.decrypt(password)) ){
				//if( cryptor.decrypt(p).equals( password) ){
				if( p.equals( password) ){
					passwordMatches = true;
				} else {
					passwordMatches = false;
				}
			}
		}
		return passwordMatches;
	}
	
	//--------------- CREATE & REMOVE USER -------------------
	
	public void createUser(String username, String password){
		
		//TODO usernameInUse(username);
		
		Element user = usersXML.createElement("user");
		rootElement.appendChild(user);
		
		//username
		Element uname = usersXML.createElement("username");
		uname.appendChild(usersXML.createTextNode(username));
		user.appendChild(uname);
		
		//TODO Check possible password restrictions
		
		//password
		Element pword = usersXML.createElement("password");
		//pword.appendChild(usersXML.createTextNode( cryptor.encrypt(password) ));
		pword.appendChild(usersXML.createTextNode( password ));
		user.appendChild(pword);
		
		//Save changes to the XML file
		writeXML(usersXML, filepath);
		
	}
	
	/**
	 * Removes information on user in 
	 * @param username
	 */
	public void removeUser(String username){
		if(getUser(username) != null){
			rootElement.removeChild(getUser(username));
		} else {
			System.out.println("User doesn't exist!");
		}
		
		//Save changes to the XML file
		writeXML(usersXML, filepath);
		
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
	
	public void changePassword(String username, String oldpassword, String newpassword){
		
		if( passwordMatch(username, oldpassword) ){
			Element user = getUser(username);
			//TODO user.getElementsByTagName("password").item(0).setTextContent(cryptor.encrypt(newpassword));
			user.getElementsByTagName("password").item(0).setTextContent(newpassword);
			
			System.out.println(username + "'s password changed!");
		}
		
		writeXML(usersXML, filepath);
		
	}
	
	//--------------- LIST OF USERNAMES ----------------------------
	
	public ArrayList<String> getUsernameList(){
		
		updateUserList();
		usernameList.clear();

		for(int i = 0; i < userList.getLength(); i++){
			
			if(userList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element userElement = (Element) userList.item(i);
				
				if(userElement.getElementsByTagName("username").item(0) != null){
					//Add username to the list
					usernameList.add( userElement.getElementsByTagName("username").item(0).getTextContent() );
				}
			}		
		}

		/* UNSAFE, more compact method of getting the usernames
		for(int i = 0; i < userList.getLength(); i++){
			usernameList.add( ((Element) userList.item(i)).getElementsByTagName("username").item(0).getTextContent());	
		}*/
		
		return usernameList;
	
	}
	
	// o-o-o-o-o-o-o-o-o HELP METHODS o-o-o-o-o-o-o-o-o-o-o-o
	
	private void updateUserList(){
		userList = usersXML.getElementsByTagName("user");
	}
	
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
		
		//Returns an element
		return foundUser;
	}
	
}
