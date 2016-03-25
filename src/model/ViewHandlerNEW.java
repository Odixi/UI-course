package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;

import exceptions.DocumentNullException;
import exceptions.ElementNullException;
import exceptions.XMLBrokenException;

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
		this.filelist = new Hashtable<String, String>();
		
	}//constructor

	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>> SET USERVIEW  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	/** Set which houses/rooms/items are included in the userview.
	 * If user doesn't yet have a saved view, the view is created and then updated to match the
	 * values given in the hashtable.
	 * 
	 * @param userID The ID of the user that the view is set for.
	 * @param userview Hashtable includes IDs of all houses/rooms/items and whether they are included in the view.
	 */
	public void setUserView(String userID, Hashtable<String, Boolean> userview){
		
		if( !userHasView(userID) ){
			createDefaultView(userID);
		}
		
		updateUserView(userID, userview);
		
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>> GET USERVIEW  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	/**
	 * 
	 * @param userID
	 * @return
	 */
	public Hashtable<String, Boolean> getUserView(String userID){
		
		//If the user doesn't have a view, create one
		if( !userHasView(userID) ){
			createDefaultView(userID);
		}
		return null;
	}
	
	/** Creates a view where no houses/rooms/items are included.
	 * @param userID The ID of the user that the view is created for.
	 */
	//TODO Make private (?)
	public void createDefaultView(String userID){
		
		if( userHasView(userID) ){
			
			//TODO What if the method is called on user that has a view?
			
		} else { //If user doesn't yet have a view, default view is created.

			Document userViewDocument = createDocument();
			Element rootElement = userViewDocument.createElement("view");
		
			if(rootElement == null){
				System.out.println("rootElement null");
			}
			
			
			Element view = userViewDocument.createElementNS(viewNS, nsPrefix + ":" + viewPrefix);
			view.setAttribute(viewIDTag, UUID.randomUUID().toString());
			rootElement.appendChild(view);
			
			//User
			Element user = userViewDocument.createElementNS(viewNS, nsPrefix + ":" + userPrefix);
			view.appendChild(user);
			user.setAttribute(userIDTag, userID);
			
			Element housesRoot = houseHandler.getRootElement();
			
			//Copy houses structure to the view
			Element newhouses = (Element) housesRoot.cloneNode(true);
			userViewDocument.adoptNode(newhouses);
			view.appendChild(newhouses);
			userViewDocument.renameNode(newhouses, viewNS, nsPrefix + ":" + housesPrefix);
		
			//Go through all elements and set them not included (inView = false) in the view.
		
			//TODO WORK ON THIS PIECE OF S...
			ArrayList<Element> houseElements;
			try {
				houseElements = getHouseElements(view);
			} catch (ElementNullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Number of house elements: " + houseElements.size());
			
			housesNotIncluded(houseElements);
		
			for(Element house : houseElements){
				ArrayList<Element> roomElements = getRoomElements(house);
				roomsNotIncluded(roomElements);
				
				for(Element room : roomElements){
					itemsNotIncluded( getItemElements(room) );
				}
			}
			
			String filepath = createFilepath(userID);
			filelist.put(userID, filepath);
			
			//Save the information to the XML file (self created method in XMLHandler class)
			writeXML(userViewDocument, filepath);
			
			//FOR TESTING ETC:
			System.out.println("Userview for user " + userID + " created!");
		}
			
	} //createDefaultView
	
		
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
			
			if(file.exists() && file.isFile()){
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
	
	private Element getRootElement(Document doc){ //Pointless...
		return doc.getDocumentElement();
	}
	
	/**
	 * 
	 * @param userID
	 * @return
	 */
	private Document getUserviewDocument(String userID){
		String filepath = filelist.get(userID);
		
		if(filepath != null){
			return getDocument(filepath);
		} else {
			return null;
		}
	}
	
	
	private String parseBoolean(String booleanString){
		//TODO COPY;
		return null;
	}
	
//	updateViewNodeList()
	
	
	/**
	 * Get the view
	 * @param doc
	 * @return
	 * @throws DocumentNullException
	 * @throws ElementNullException
	 * @throws XMLBrokenException 
	 */
	
	//TODO Is this method actually even needed?
	private Element getViewElement(Document doc) throws DocumentNullException, ElementNullException, XMLBrokenException{
		
		Element viewElement = null;
		
		if(doc == null){
			throw new DocumentNullException("Parameter doc is null");
		}
		
		NodeList viewNodes = doc.getElementsByTagName(viewTag);
		
		if(viewNodes.getLength() != 1){
			/* View element should be the root of the file.
			 * If there are multiple view elements, something is terribly wrong.
			 * Therefore exception is thrown when such situation is encountered.
			 */
			throw new XMLBrokenException("There are multiple roots or 'view' is something other than the root element in the xml file representing the user's view.");
		}
		
		if(viewNodes.item(0).getNodeType() == Node.ELEMENT_NODE ){
			viewElement = (Element) viewNodes.item(0);
		}
		
		if(viewElement == null){
			//If view element isn't found, throw exception
			throw new ElementNullException("View element not found.");
		}
		
		return viewElement;
	}
	
	private ArrayList<Element> getHouseElements(Element viewElement) throws ElementNullException{
		
		ArrayList<Element> houseElements = new ArrayList<Element>();
		
		if(viewElement == null){
			throw new ElementNullException("ViewElement null.");
		}
		
		NodeList housesRoot = viewElement.getElementsByTagName(housesTag);

		//There's actually just one 'houses' element but I'm going for the more general solution just in case.
		for(int i = 0; i < housesRoot.getLength(); i++){
			if(housesRoot.item(i).getNodeType() == Node.ELEMENT_NODE){
				NodeList houseNodes = ((Element)housesRoot.item(i)).getElementsByTagName(houseTag);
				
				for(int j = 0; j < houseNodes.getLength(); j++){
					if(houseNodes.item(j).getNodeType() == Node.ELEMENT_NODE){
						houseElements.add( (Element)houseNodes.item(j) );
					}
				}
			}
		}

		return houseElements;

	}
	
	private ArrayList<Element> getRoomElements(Element houseElement){
		
		ArrayList<Element> roomElements = new ArrayList<Element>();
		
		NodeList rooms = houseElement.getElementsByTagName(roomTag);
		
		//There's actually just one 'houses' element but I'm going for the more general solution just in case.
		for(int i = 0; i < rooms.getLength(); i++){
			if(rooms.item(i).getNodeType() == Node.ELEMENT_NODE && rooms.item(i) != null){
				roomElements.add( (Element) rooms.item(i));
			}
		}
		return roomElements;
	}
	
	private ArrayList<Element> getItemElements(Element roomElement){
		
		ArrayList<Element> itemElements = new ArrayList<Element>();
		
		//Lights
		NodeList lightNodes = roomElement.getElementsByTagName(lightTag);
		
		for(int i = 0; i < lightNodes.getLength(); i++){
			if(lightNodes.item(i).getNodeType() == Node.ELEMENT_NODE && lightNodes.item(i) != null){
				itemElements.add( (Element) lightNodes.item(i) );
			}
		}
		
		//Sensors
		NodeList sensorNodes = roomElement.getElementsByTagName(sensorTag);
		
		for(int i = 0; i < sensorNodes.getLength(); i++){
			if(sensorNodes.item(i).getNodeType() == Node.ELEMENT_NODE && sensorNodes.item(i) != null){
				itemElements.add( (Element) sensorNodes.item(i) );
			}
		}
		
		//Appliances
		NodeList applianceNodes = roomElement.getElementsByTagName(applianceTag);
		
		for(int i = 0; i < applianceNodes.getLength(); i++){
			if(applianceNodes.item(i).getNodeType() == Node.ELEMENT_NODE && applianceNodes.item(i) != null){
				itemElements.add( (Element) applianceNodes.item(i) );
			}
		}
		
		//Controllers
		NodeList controllerNodes = roomElement.getElementsByTagName(controllerTag);
		
		for(int i = 0; i < controllerNodes.getLength(); i++ ){
			if(controllerNodes.item(i).getNodeType() == Node.ELEMENT_NODE && controllerNodes.item(i) != null ){
				itemElements.add( (Element) controllerNodes.item(i) );
			}
		}
		
		return itemElements;
	}
}
