package model;

import org.w3c.dom.*;
import java.util.ArrayList;

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
	
	private String filepath;
	private String userTag;
	private String usernameTag;
	private String passwordTag;
	
	//>>>> CONSTUCTOR <<<<<
	public AccountHandler(String filepath, String userTag, String usernameTag, String passwordTag){	
		
		this.filepath = filepath;
		this.userTag = userTag;
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

	public String getFilepath(){
		return filepath;
	}
	
	public String getuserTag(){
		return userTag;
	}
	
	public String getusernameTag(){
		return usernameTag;
	}
	
	public String getpasswordTag(){
		return passwordTag;
	}
	
	
	//------ PASSWORD MATCHING CHECK ----------
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
	
	//--------------- CREATE & REMOVE USER -------------------
	
	public void createUser(String username, String password){
		
		//TODO usernameInUse(username);
		
		Element user = accountXML.createElement(userTag);
		rootElement.appendChild(user);
		
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
		writeXML(accountXML, filepath);	
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
		
		//TODO Null check? Return boolean?
		
		if( passwordMatch(username, oldpassword) ){
			Element user = getUser(username);
			//TODO user.getElementsByTagName("password").item(0).setTextContent(cryptor.encrypt(newpassword));
			user.getElementsByTagName(passwordTag).item(0).setTextContent(newpassword);
			
			System.out.println(username + "'s password changed!");
		}
		
		writeXML(accountXML, filepath);
		
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
			}
		} 
		else {
			System.out.println("Username already in use.");
		}
		
		return usernameWasChanged;	
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

