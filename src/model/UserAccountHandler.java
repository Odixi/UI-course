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

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 
 * @author 
 *
 */

/* TODO
 * - Method: get list of usernames
 * - 
 */

public class UserAccountHandler {

	private String filepath = ".src/xmlfiles/users.xml";
	private NodeList userList;
	private Document usersXML;
	private Element rootElement;
	private ArrayList<String> usernameList;
	
	private BasicTextEncryptor cryptor;
	
	//TODO Move DocumentBuilder etc. to attributes
	//Maybe...?
	
	
	public UserAccountHandler(){	
		
		usersXML = getDocument(filepath);
		usersXML.getDocumentElement().normalize();
	
		//For encrypting and decrypting passwords
		cryptor = new BasicTextEncryptor();
		
		rootElement = usersXML.getDocumentElement();
				
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

				if( cryptor.decrypt(p).equals(password) ){
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
		pword.appendChild(usersXML.createTextNode( cryptor.encrypt(password) ));
		user.appendChild(pword);
		
		//Save changes to the XML file
		writeXML();
		
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
		writeXML();
		
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
			user.getElementsByTagName("password").item(0).setTextContent(cryptor.encrypt(newpassword));
			
			System.out.println(username + "'s password changed!");
		}
		
		writeXML();
		
	}
	
	//--------------- LIST OF USERNAMES ----------------------------
	
	public ArrayList<String> getUsernameList(){
		
		
		
		return usernameList;
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
	
	
	//--------------- WRITE INTO THE XML FILE -------------
	
	private void writeXML(){
		try {
		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(usersXML);
			StreamResult result = new StreamResult(new File(filepath) );
			transformer.transform(source, result);
			
		} catch (TransformerConfigurationException e) {	
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
	} //writeXML
	
}
