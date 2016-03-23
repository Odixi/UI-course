package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ViewHandlerNEW extends XMLHandler {
	
	//HouseHandler gives the access to XML file describing house structures
	private HouseHandler houseHandler;

	//FileList
	private Hashtable<String, String> filelist;
	private final String rootfilepath = "src/xmlfiles.viewsfiles/";
	
	// >>> XML tags
	private static final String inView = "inView";
	
	private static final String viewTag = "tns:view";	
	private static final String viewIDTag = "viewID";

	private static final String userIDTag = "userID";
	private static final String userTag = "tns:user";
	
	private static final String housesTag = "tns:houses";
		//House
	private static final String houseTag = "house";
	private static final String housenameTag = "houseName";
	private static final String houseIDTag = "houseID";
		//Room
	private static final String roomnameTag = "roomName";
	private static final String roomTag = "room";
	private static final String roomIDTag = "roomID";
		//Lights
	private static final String lightTag = "light";
	private static final String lightnameTag = "lightName";
	private static final String lightIDTag = "lightID";
		//Sensors
	private static final String sensorTag = "sensor";
	private static final String sensornameTag = "sensorName";
	private static final String sensorIDTag = "sensorID";
		//Appliances
	private static final String applianceTag = "appliance";
	private static final String appliancenameTag = "applianceName";
	private static final String applianceIDTag = "applianceID";
	
	private static final String controllerTag = "controller";
	private static final String controllernameTag = "controllerName";
	private static final String controllerIDTag = "controllerID";
	private static final String controllerTypeTag = "controllerType";
	
	//PREFIXES ETC.
	private static final String viewNS = "http://www.example.org/views";
	private static final String nsPrefix = "tns";
	
	private static final String viewPrefix = "view";
	private static final String userPrefix = "user";
	private static final String housesPrefix = "houses";
	
	
	//CONSTRUCTOR
	
	public ViewHandlerNEW(HouseHandler houseHandler){
		
		this.houseHandler = houseHandler;		
	
	}//constructor

	
	
	public void createDefaultView(String userID){
		//TODO Copy
		
		
	}
	
	public void setUserView(String userID, Hashtable<String, Boolean> userview){
		//TODO Copy
	}
	
	
		
	public Hashtable<String, Boolean> getUserView(String userID){
		
		//Check that the user has a view
		if( userHasView(userID) ){
			
			
			
		} else {
			//If the user doesn't have a view, create one

			//TODO Create view
			
		}

		return null;
	}
	
	//---------------------- USER HAS A VIEW ? ------------------------
	/** Check if a view (file) has been created for the user.
	 * @param userID
	 * @return
	 */
	public boolean userHasView(String userID){
		
		if(filelist.get(userID) == null){
			return false;
			
		} else {
			File file = new File( filelist.get(userID) );
			
			if(file.exists()){
				return true;
				
			} else {
				//If user doesn't have a file, but there's still path in filelist, it gets confusing
				//Therefore delete the path since there isn't a file mathing it.
				filelist.remove(userID);
				return false;
			}
		}
	}
	
	//-------------------- UPDATE THE USERVIEW --------------------------
	
	/** Update which items are included in the user's view.
	 * @param userID
	 * @param userview 
	 * @return
	 */
	public boolean updateUserView(String userID, Hashtable<String, Boolean> userview){
		//TODO COPY (partially);
		return false; //TODO?
	}
	
	public void updateTheHouseStructureInView(String userID){
		//TODO DO THIS IF YOU HAVE TIME
	}
	
	public void updateHousesIncluded(ArrayList<Element> houseElements, Hashtable<String, Boolean> userview){
		//TODO Copy
	}
	
	public void updateRoomsIncluded(ArrayList<Element> roomElements, Hashtable<String, Boolean> userview){
		//TODO Copy
	}
	
	public void updateItemsIncluded(ArrayList<Element> itemElements, Hashtable<String, Boolean> userview){
		//TODO Copy
	}
	
	public void housesNotIncluded(ArrayList<Element> houseElements){
		//TODO Copy
	}
	
	public void roomsNotIncluded(ArrayList<Element> roomElements){
		//TODO Copy
	}
	
	public void itemsNotIncluded(ArrayList<Element> itemElements){
		//TODO Copy
	}
	

	
// o.o.o.o.o.o.o.o.o.o.o.o.o.o.o HELP METHODS o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o
		
	/** Creates the filepath for user's view file
	 * @param userID
	 * @return 
	 */
	private String createFilepath(String userID){
		return rootfilepath + userID + ".xml";
	}
	
	/** Return the root element of the given document
	 * @param filepath
	 * @return
	 */
	private Element getRootElement(String filepath){
		
		Document doc = getDocument(filepath);
		return doc.getDocumentElement();
		
	}
	
	private String parseBoolean(String booleanString){
		//TODO COPY;
		return null;
	}
	
//	updateViewNodeList()
	

	private Element getViewElement(String userID){
		//TODO ?
		return null;
	}
	
	private ArrayList<Element> getHouseElements(Element viewElement){
		//TODO
		return null;
	}
	
	private ArrayList<Element> getRoomElements(Element houseElement){
		//TODO
		return null;
	}
	
	private ArrayList<Element> getItemElements(Element roomElement){
		//TODO
		return null;
	}
}
