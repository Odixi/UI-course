package model;

import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

/**
 * The class is designed to handle XML following basic structure demonstrated below.
 * 
 * <rootElement>
 * 		<accountTagName>
 * 			<username>username</username>
 *			<password>password</password>
 *			.
 *			.
 *		</accountTagName>
 * </rootElement>
 *
 * @param filepath Filepath for the XML file storing the user information.
 * @param userTag Tagname that defines the usernames in XML file.
 * @param passwordTag
 */

public class AccountHandler extends XMLHandler {

	private NodeList accountNodeList;
	private Document accountXML;
	private Element rootElement;
	
	private final String filepath;
	private final String userTag;
	private final String userIDTag;
	private final String usernameTag;
	private final String passwordTag;
	
	//>>>> CONSTUCTOR <<<<<
	public AccountHandler(String filepath, String userTag, String userIDTag, String usernameTag, String passwordTag){	
		
		this.filepath = filepath;
		this.userTag = userTag;
		this.userIDTag = userIDTag;
		this.usernameTag = usernameTag;
		this.passwordTag = passwordTag;
		
		accountXML = getDocument(filepath);
		accountXML.getDocumentElement().normalize();
		rootElement = accountXML.getDocumentElement();

	} //constructor
	
	
	// &&&&&&&&&&&&&& GETTERS &&&&&&&&&&&&&&&&&&&
		// Not sure that all these will be needed but I'll write them just in case.
	
	public NodeList getAccountNodeList(){
		return accountNodeList;
	}
	
	public Document getAccountXML(){
		return accountXML;
	}
	
	public Element getRootElement(){
		return rootElement;
	}

	//------ PASSWORD MATCHING CHECK ----------
	/**
	 * Check whether the password given matches the user's password.
	 * 
	 * @param username 
	 * @param password
	 * @return true if the parameter password matches the password saved for the user matching the parameter username. Otherwise false.
	 */
	public boolean passwordMatch(String username, String password){
		
		boolean passwordMatches = false;
		
		if(getUser(username) == null){
			System.out.println("User not found");
			passwordMatches = false;
		
		} else {
			if( getUser(username).getElementsByTagName(passwordTag) != null ){
				String p = getUser(username).getElementsByTagName(passwordTag).item(0).getTextContent();

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
	
	//--------------- CREATE USER -------------------
	/**
	 * Create a new user called 'username' who has password 'password'. UserID is generated automatically for the user.
	 * 
	 * @param username The username for the new user.
	 * @param password The password for the new user.
	 * 
	 */
	public boolean createUser(String username, String password){
		
		//If the username is already in use
		if( usernameInUse(username) ){
		
			System.out.println("Username " + username + " is already in use! New user can't be created!");
			return false;
			
		} else {
		
			Element user = accountXML.createElement(userTag);
			rootElement.appendChild(user);
			
			//ID attribute
			
			//Generate ID
			//These generated IDs are going to be quite a bit longer than the handwritten ones.
			user.setAttribute(userIDTag, UUID.randomUUID().toString());
	
			//username
			Element uname = accountXML.createElement(usernameTag);
			uname.appendChild(accountXML.createTextNode(username));
			user.appendChild(uname);
			
			//TODO Check possible password restrictions
			
			//password
			Element pword = accountXML.createElement(passwordTag);
			pword.appendChild(accountXML.createTextNode( password ));
			user.appendChild(pword);
					
			//Save changes to the XML file
			writeXML(accountXML, filepath);
			
			System.out.println("new user created " + username);
		
			return true;
		}
	}
	
	//--------------- REMOVE USER -------------------
	
	/**
	 * Deletes the user from the system aka removes information on user in users.xml. 
	 * @param username The username of the username to be deleted.
	 */
	public void removeUser(String username){
		if(getUser(username) != null){
			rootElement.removeChild(getUser(username));
		} else {
			System.out.println("User doesn't exist!");
		}
		
		//Save changes to the XML file
		writeXML(accountXML, filepath);	
	}
	
	
	//------------ Is the username already in use? ------------
	public boolean usernameInUse(String username){

		if(getUser(username) == null){
			return false;
		} else {
			return true;
		}
		
	} //usenameInUse()
	
	
	//--------------- PASSWORD VALID -----------------------------------------
	
	public boolean passwordValid(String password){
		
		if(password.length() >= 8 && password.length() <= 24){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean passwordsMatch(String password, String confirmPassword){
		if(password.equals(confirmPassword)){
			return true;
		} else {
			return false;
		}
	}
	
	//--------------- CHANGE PASSWORD (ADMIN CHANGES) ------------------------
	
	public boolean changePasswordAdmin(String username, String newpassword){

		boolean passwordChanged = false;
		Element user = getUser(username);

		if(user != null && user.getElementsByTagName(passwordTag).item(0) != null){
			user.getElementsByTagName(passwordTag).item(0).setTextContent(newpassword);
			
			System.out.println(username + "'s password changed!");
			passwordChanged = true;
		}
		
		writeXML(accountXML, filepath);
		
		return passwordChanged;
	}
	
	
	//--------------- CHANGE PASSWORD (USER CHANGES) ------------------------
	
	public boolean changePassword(String username, String oldpassword, String newpassword){
		
		//TODO Null check?
		//Return boolean?
		boolean passwordChanged = false;
		
		if( passwordMatch(username, oldpassword) ){
			
			Element user = getUser(username);
			
			if(user != null && user.getElementsByTagName(passwordTag).item(0) != null){
				//TODO user.getElementsByTagName("password").item(0).setTextContent(cryptor.encrypt(newpassword));
				user.getElementsByTagName(passwordTag).item(0).setTextContent(newpassword);
				
				System.out.println(username + "'s password changed!");
				passwordChanged = true;
			}
		}
		
		writeXML(accountXML, filepath);
		
		return passwordChanged;
	}
	
	//----------------- USERNAME VALID ---------------------------
	
	public boolean usernameValid(String username){
		if( username.length() >= 3 && username.length() <= 24){
			return true;
		} else {
			return false;
		}
	}
	
	//----------------- CHANGE USERNAME --------------------------
	
	/**
	 * Returns true if username can be and was changed.
	 * @param oldusername
	 * @param newusername
	 * @return
	 */
	
	public boolean changeUsername(String oldusername, String newusername){		//TODO Should the method take password as parameter?
		
		Boolean usernameWasChanged = false;
		
		//Check that asked username is available
		if( !usernameInUse(newusername) ){
			Element user = getUser(oldusername);
			
			if(user != null && user.getElementsByTagName(usernameTag).item(0) != null){
				user.getElementsByTagName(usernameTag).item(0).setTextContent(newusername);
				
				System.out.println("Username " + oldusername + " was changed to " + newusername);
			}
		} 
		else {
			System.out.println("Username " + newusername + " already in use.");
		}
		
		writeXML(accountXML, filepath);
		
		return usernameWasChanged;	
	}
	
	//--------------- LIST OF USERS ----------------------------
	
	public Hashtable<String, String> getUserList(){
		Hashtable<String, String> userList = new Hashtable<String, String>();
		
		updateaccountNodeList();
		
		for(int i = 0; i < accountNodeList.getLength(); i++){
			
			if(accountNodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element userElement = (Element) accountNodeList.item(i);
				
				if(userElement.getElementsByTagName(usernameTag).item(0) != null && userElement.hasAttribute(userIDTag)){
					//Add username to the list
					String username = userElement.getElementsByTagName(usernameTag).item(0).getTextContent().trim();
					String userID = userElement.getAttribute(userIDTag).trim();
					
					userList.put(userID, username);
					
				}
			}		
		}
		return userList;
	}
	
	//--------------- LIST OF USERNAMES ----------------------------
	
	public ArrayList<String> getUsernameList(){
		
		updateaccountNodeList();
		ArrayList<String> usernameList = new ArrayList<String>();

		for(int i = 0; i < accountNodeList.getLength(); i++){
			
			if(accountNodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element userElement = (Element) accountNodeList.item(i);
				
				if(userElement.getElementsByTagName(usernameTag).item(0) != null){
					//Add username to the list
					usernameList.add( userElement.getElementsByTagName(usernameTag).item(0).getTextContent() );
				}
			}		
		}
		return usernameList;
	}
	
	//------------------ GET USERID MATCHING USERNAME --------------------
	
	public String getUserID(String username){
		String userID = null;
		
		Element userElement = getUser(username);
		
		if(userElement != null){ //If a user matching the username doesn't exist, userElement == null
			if(userElement.hasAttribute(userIDTag)){
				userID = userElement.getAttribute(userIDTag).trim();
			}
		}
		
		return userID;
	}
	
	// o-o-o-o-o-o-o-o-o HELP METHODS o-o-o-o-o-o-o-o-o-o-o-o
	
	private void updateaccountNodeList(){
		accountNodeList = accountXML.getElementsByTagName(userTag);
	}

	//------------------ GET USER (ELEMENT) -------------------------
	
	public Element getUser(String username){
		
		updateaccountNodeList(); //TODO Is it insane to call this again?
		
		Element foundUser = null;
		
		//Cycle through the users
		for(int i = 0; i < accountNodeList.getLength(); i++){
				
			Node userNode = accountNodeList.item(i);
				
			if(userNode.getNodeType() == Node.ELEMENT_NODE){
				Element userElement = (Element) userNode;
				
				if(userElement.getElementsByTagName(usernameTag).item(0) != null
						&& userElement.getElementsByTagName(usernameTag).item(0).getTextContent().equals(username) ){
					foundUser = userElement;
					break;
				}
			}		
		}	
		//Returns an element
		return foundUser;
	}
	
}//class AccountHandler

