package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
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
	private final String rootfilepath = "src/viewfiles/";
	
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
		
		filelistUpToDate();
		
		//TODO REMOVE For testing
		System.out.println("Userview files in the system: ");
		
		for(String userID : filelist.keySet() ){
			System.out.println("User " + userID + " has userview " + filelist.get(userID) );
		}
		
	}//constructor

	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>> SET USERVIEW  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	/** Set which houses/rooms/items are included in the userview.
	 * If user doesn't yet have a saved view, the view is created and then updated to match the
	 * values given in the hashtable.
	 * 
	 * @param userID The ID of the user that the view is set for.
	 * @param userview Hashtable includes IDs of all houses/rooms/items and whether they are included in the view.
	 * @throws ElementNullException 
	 */
	public boolean setUserView(String userID, Hashtable<String, Boolean> userview) throws ElementNullException{

		if( !userHasView(userID) ){
			createDefaultView(userID);
		}
		
		//Returns boolean value indicating whether the update was successful.
		return updateUserView(userID, userview);

	}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>> GET USERVIEW  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	/**
	 * 
	 * @param userID
	 * @return
	 * @throws ElementNullException 
	 */
	public Hashtable<String, Boolean> getUserView(String userID) throws ElementNullException {
		
		Hashtable<String, Boolean> userview = new Hashtable<String, Boolean>();
		
		if(userID == null){
			
			//TODO Right now, the method is called with null value in the beginning of the program, when no user is selected.
			return getNothingIncludedList();
			
		}
		
		//If the user doesn't have a view, create one
		else if( !userHasView(userID) ){
			System.out.println("getUserView: User " + userID + " doesn't have a view, so default is being created.");
			
			createDefaultView(userID);
		}

		Document userviewDocument = getUserviewDocument(userID);
		Element viewElement;
			
		try {	
			viewElement = getViewElement(userviewDocument);
			
		} catch (DocumentNullException | ElementNullException | XMLBrokenException e) {
			System.out.println("Problem with user's (" + userID + ") view file. Recovering from problem by creating default view.");
			
			//Return the default view
			return recoverWithDefaultView(userID);
		}
		
		ArrayList<Element> houseElements = getHouseElements(viewElement);
		
		//Iterate through houses
		for(int houseIndex = 0; houseIndex < houseElements.size(); houseIndex++){
			
			//inView = true/false?
			if( houseElements.get(houseIndex).getAttribute(houseIDTag) != null ){
				if( houseElements.get(houseIndex).getAttribute(inView) == null){
					houseElements.get(houseIndex).setAttribute(inView, "false");
				}
				
				//Add house info (ID, inView) to hashtable 
				userview.put(houseElements.get(houseIndex).getAttribute(houseIDTag), parseBoolean( houseElements.get(houseIndex).getAttribute(inView).trim() ) );
			}
			//Rooms
			ArrayList<Element> roomElements = getRoomElements(houseElements.get(houseIndex));

			for(int roomIndex = 0; roomIndex < roomElements.size(); roomIndex++){
				
				//inView = true/false?
				if(roomElements.get(roomIndex).getAttribute(roomIDTag) != null ){
					if( roomElements.get(roomIndex).getAttribute(inView) == null){
						roomElements.get(roomIndex).setAttribute(inView, "false");
					}
					//Add room info to hashtable
					userview.put(roomElements.get(roomIndex).getAttribute(roomIDTag), parseBoolean(roomElements.get(roomIndex).getAttribute(inView)) );
				}
				
				//Lights, sensors and appliance
				ArrayList<Element> itemElements = getItemElements( roomElements.get(roomIndex) );
					
				for(int itemIndex = 0; itemIndex < itemElements.size(); itemIndex++){
					//inView = true/false?
					if(itemElements.get(itemIndex).hasAttribute(lightIDTag)){
						if(itemElements.get(itemIndex).getAttribute(inView) == null){
							itemElements.get(itemIndex).setAttribute(inView, "false");
						}	
						userview.put(itemElements.get(itemIndex).getAttribute(lightIDTag), parseBoolean(itemElements.get(itemIndex).getAttribute(inView)) );
						
					} else if(itemElements.get(itemIndex).hasAttribute(sensorIDTag)){
						if(itemElements.get(itemIndex).getAttribute(inView) == null){
							itemElements.get(itemIndex).setAttribute(inView, "false");
						}
						userview.put(itemElements.get(itemIndex).getAttribute(sensorIDTag), parseBoolean(itemElements.get(itemIndex).getAttribute(inView)) );
						
					} else if(itemElements.get(itemIndex).hasAttribute(applianceIDTag)){
						if(itemElements.get(itemIndex).getAttribute(inView) == null){
							 itemElements.get(itemIndex).setAttribute(inView, "false");
						}
						userview.put(itemElements.get(itemIndex).getAttribute(applianceIDTag), parseBoolean(itemElements.get(itemIndex).getAttribute(inView)));
						
					} else if(itemElements.get(itemIndex).hasAttribute(controllerIDTag)){
						if(itemElements.get(itemIndex).getAttribute(inView) == null ){
							itemElements.get(itemIndex).setAttribute(inView, "false");
						}
						userview.put(itemElements.get(itemIndex).getAttribute(controllerIDTag), parseBoolean(itemElements.get(itemIndex).getAttribute(inView)) );
					}
				}
			}
		}
		
		//Return the hashtable with houseIDs, roomIDs & itemIDs and corresponding boolean values telling whether object is included in the view or not
		return userview;
	}
	
	
	/**
	 * 
	 * @param userID
	 * @return
	 * @throws ElementNullException 
	 */
	private Hashtable<String, Boolean> recoverWithDefaultView(String userID) throws ElementNullException {
		
		//Delete the corrupted file
		deleteUserview(userID);
		
		//Create the default view for the user
		createDefaultView(userID);
	
		return getUserView(userID);
	}
	

	//--------------------------- RETURN 'NOTHING INCLUDED' LIST --------------------------------
	
	/**
	 * 
	 * @return
	 * @throws ElementNullException 
	 */
	public Hashtable<String, Boolean> getNothingIncludedList() throws ElementNullException{
		Hashtable<String, Boolean> nothingIncludedList = new Hashtable<String, Boolean>();
		
		Hashtable<String, String> houseNames = houseHandler.getHouseNameList();
		
		for(String houseID : houseNames.keySet()){
			Hashtable<String, String> roomNames = houseHandler.getRoomNames(houseID);
			nothingIncludedList.put(houseID, false);
			
			for(String roomID : roomNames.keySet()){
				Hashtable<String, String> itemNames = houseHandler.getItemNames(houseID, roomID);
				nothingIncludedList.put(roomID, false);
				
				for(String itemID : itemNames.keySet()){
					nothingIncludedList.put(itemID, false);
				}
			}
		}
		
		return nothingIncludedList;
	}
	
	//--------------------------- CREATE DEFAULT USERVIEW ----------------------------------------
	/** Creates a view where no houses/rooms/items are included.
	 * @param userID The ID of the user that the view is created for.
	 * @throws ElementNullException 
	 */

	//TODO Make private, needs to be public for testing though.
	public void createDefaultView(String userID) {
			
		//Method is called on user that has a view. No need to do anything.
		if( userHasView(userID) ){
			
			System.out.println("User already has a view.");
			return; //TODO Unnecessary
			
		}
		
		//If user doesn't yet have a view, default view is created.
		else if( !userHasView(userID) ){ 

			Document userViewDocument = createDocument();

			Element viewRoot = userViewDocument.createElementNS(viewNS, nsPrefix + ":" + viewPrefix);
			viewRoot.setAttribute(viewIDTag, UUID.randomUUID().toString());
			userViewDocument.appendChild(viewRoot);
			
			//User
			Element user = userViewDocument.createElementNS(viewNS, nsPrefix + ":" + userPrefix);
			viewRoot.appendChild(user);
			user.setAttribute(userIDTag, userID);
			
			Element housesRoot = houseHandler.getRootElement();
			
			//Copy houses structure to the view
			Element newhouses = (Element) housesRoot.cloneNode(true);
			userViewDocument.adoptNode(newhouses);
			viewRoot.appendChild(newhouses);
			userViewDocument.renameNode(newhouses, viewNS, nsPrefix + ":" + housesPrefix);
		
			ArrayList<Element> houseElements = getHouseElements(viewRoot);
			
			//Set the houses not included in the view
			housesNotIncluded(houseElements);
		
			//Followed by setting all rooms in the houses and items in the rooms to not included in the view.
			for(Element house : houseElements){
				ArrayList<Element> roomElements = getRoomElements(house);
				roomsNotIncluded(roomElements);
				
				for(Element room : roomElements){
					itemsNotIncluded( getItemElements(room) );
				}
			}
			
			//All view files are saved to the same folder and each is named after the user's ID.
			String filepath = createFilepath(userID);
			
			//Save the user ID and the view files path to filelist
			//TODO REMOVE
			System.out.println("CreateDefaultView: userID " + userID + ", filepath: " + filepath);
			
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
		
		if( !filelist.containsKey(userID)){
			return false;
			
		} else {
			File file = new File( filelist.get(userID) );
			
			if(file.exists() && file.isFile()){
				return true;
				
			} else {
				//If user doesn't have a file, but there's still path in filelist, it gets confusing
				//Therefore delete the path since there isn't a file mathing it.
				System.out.println("User " + userID + " had reference to file but file didn't exist. Reference was deleted."); //REMOVE For testing 
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
	 * @throws ElementNullException 
	 */
	public boolean updateUserView(String userID, Hashtable<String, Boolean> userview) throws ElementNullException{

		Document viewDocument = getUserviewDocument(userID);
		Element viewElement;
		
		try {
			viewElement  = getViewElement(viewDocument);
			
		} catch (DocumentNullException | ElementNullException | XMLBrokenException e) {
			//TODO Document is corrupted. What should be done?
			System.out.println("UpdateUserView: The user's " + userID + " viewfile is corrupted. The file can't be updated.");
			
			//Recover from the error by recreating the view
			recoverWithDefaultView(userID);
			
			//TODO Maybe the recovery method should be called?
			
			return false;
		}
		
		ArrayList<Element> houseElements = getHouseElements(viewElement);
		
		System.out.println("UpdateUserView: houseElements.size: "+ houseElements.size());
		
		//TODO REMOVE For testing
		for(int i = 0; i < houseElements.size(); i++){
			System.out.println("HouseElement's inView attribute: " + houseElements.get(i).getAttribute(inView).toString());
		}
		
		updateHousesIncluded(houseElements, userview);
		
		//Update the houses (set the value of inView attribute for each house)
		for(Element house : houseElements){
			
			ArrayList<Element> roomElements = getRoomElements(house);
			updateRoomsIncluded(roomElements, userview);
			
			for(Element room : roomElements){
			
				ArrayList<Element> itemElements = getItemElements(room);
				updateItemsIncluded(itemElements, userview);
			}
		}
		
		String filepath = filelist.get(userID);
		System.out.println("UpdateUserView: " + filepath);
			
		System.out.println("UpdateUserView: userID-parameter: " + userID);
		
		writeXML(viewDocument, filepath);
		
		//FOR TESTING ETC:
		System.out.println("Userview for user " + userID + " was updated!");
		
		return true;
	}
	
	/**
	 * 
	 * @param houseElements
	 * @param userview
	 */
	public void updateHousesIncluded(ArrayList<Element> houseElements, Hashtable<String, Boolean> userview){
		
		//Iterate through houses
		if( !houseElements.isEmpty() ){
			for(int i = 0; i < houseElements.size(); i++){
				//inView = true/false?
				if(houseElements.get(i).hasAttribute(houseIDTag) && houseElements.get(i).getAttribute(houseIDTag) != null){
						
					Boolean included = userview.get( houseElements.get(i).getAttribute(houseIDTag) );
					
					if( houseElements.get(i).hasAttribute(inView) ){
						//TODO REMOVE For testing
						System.out.println("Get house's (" + houseElements.get(i).getAttribute(houseIDTag).toString()  + "), inView attribute is: " + houseElements.get(i).getAttribute(inView).toString() );
						System.out.println("Get house's (" + houseElements.get(i).getAttribute(houseIDTag).toString()  + "), inView attribute will be: " + included.toString() );
						houseElements.get(i).setAttribute(inView, included.toString());
					
					} else {
						//TODO REMOVE: for testing
						System.out.println("House doesn't have inView attribute!");
					}
				}
			}
		}
	} //updateHousesIncluded
	
	
	/**
	 * 
	 * @param roomElements
	 * @param userview
	 */
	public void updateRoomsIncluded(ArrayList<Element> roomElements, Hashtable<String, Boolean> userview){
		
		if( !roomElements.isEmpty() ){
			for(int j = 0; j < roomElements.size(); j++){
				
				//inView = true/false?
				if(roomElements.get(j).hasAttribute(roomIDTag) && roomElements.get(j).getAttribute(roomIDTag) != null){

					Boolean included = userview.get( roomElements.get(j).getAttribute(roomIDTag));
					
					//TODO REMOVE For testing
					System.out.println("Get house's (" + roomElements.get(j).getAttribute(roomIDTag).toString()  + "), inView attribute is: " + roomElements.get(j).getAttribute(inView).toString() );
					System.out.println("Get house's (" + roomElements.get(j).getAttribute(roomIDTag).toString()  + "), inView attribute will be: " + included.toString() );
					roomElements.get(j).setAttribute(inView, included.toString());
				}				
			}
		}		
	}
	
	/**
	 * 
	 * @param itemElements
	 * @param userview
	 */
	public void updateItemsIncluded(ArrayList<Element> itemElements, Hashtable<String, Boolean> userview){
		
		if( !itemElements.isEmpty() ){
			
			for(int k = 0; k < itemElements.size(); k++){

				if(itemElements.get(k).hasAttribute(lightIDTag)){
					Boolean included = userview.get( itemElements.get(k).getAttribute(lightIDTag));
					itemElements.get(k).setAttribute(inView, included.toString());
						
				} else if(itemElements.get(k).hasAttribute(sensorIDTag)){
					Boolean included = userview.get( itemElements.get(k).getAttribute(sensorIDTag));
					itemElements.get(k).setAttribute(inView, included.toString());
						
				} else if(itemElements.get(k).hasAttribute(applianceIDTag)){
					Boolean included = userview.get( itemElements.get(k).getAttribute(applianceIDTag));
					itemElements.get(k).setAttribute(inView, included.toString());
						
				} else if(itemElements.get(k).hasAttribute(controllerIDTag)){
					Boolean included = userview.get( itemElements.get(k).getAttribute(controllerIDTag));
					itemElements.get(k).setAttribute(inView, included.toString());
				}
			}
		}
	}

	
	//-------------------------- UPDATE THE HOUSE STRUCTURE IN VIEW -----------------------------------

		public void updateTheHouseStructureInView(String userID){
			
			//TODO DO THIS IF YOU HAVE TIME
		
		}
	
	//------------------------- DELETE THE USERVIEW --------------------------------------
	
	/**
	 * 
	 * @param userID
	 * @return
	 */
	public boolean deleteUserview(String userID){
		
		if(filelist.get(userID) != null){
			//Get the filepath and delete the file
			deleteFile( filelist.get(userID) );
			filelist.remove(userID);
			
			return true;
		}
		return false;
	}
	
	
	/** Method deletes the file in the specified filepath. Prints whether the deletion was successful or not in the console.
	 * @param filepath Filepath of the file to be deleted.
	 */
	private void deleteFile(String filepath){
		File file = new File(filepath);
		
		if(file.delete()){
			System.out.println("SmartModel: A file in filepath " + filepath + " deleted successfully.");
		} else {
			System.out.println("SmartModel: Deleting a file in filepath " + filepath + " failed.");
		}
	}
	
// <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
	
	//--------------- SET HOUSES NOT INCLUDE IN VIEW -------------------------------
	/** Sets the attribute (inView) for each house in the list to false therefore stating that none of the houses are included in the view.
	 * @param houseElements List of houseElements that need to be set not included.
	 */
	private void housesNotIncluded(ArrayList<Element> houseElements){
		
		//Iterate through houses and for each house set inView (attribute stating whether the element is included in the view ) false.
		if( !houseElements.isEmpty() ){
			for(int i = 0; i < houseElements.size(); i++){
				houseElements.get(i).setAttribute(inView, "false");
			}
		} else {
			//TODO What do I do if there are no house elements?
			//Do I have to do something?
		}
	}
	
	//-------------------- SET ROOMS NOT INCLUDED IN VIEW ---------------------------------
	/** Sets the attribute (inView) for each room in the list to false therefore stating that none of the rooms are included in the view.
	 * @param houseElements List of houseElements that need to be set not included.
	 */
	private void roomsNotIncluded(ArrayList<Element> roomElements){
		
		if( !roomElements.isEmpty() ){
			for(int j = 0; j < roomElements.size(); j++){
				roomElements.get(j).setAttribute(inView, "false");				
			}
		} else {
			//TODO ?
		}
	}
	
	//-------------------- SET ITEMS NOT INCLUDED IN VIEW --------------------------------
	/** Sets the attribute (inView) for each item in the list to false therefore stating that none of the items are included in the view.
	 * @param houseElements List of houseElements that need to be set not included.
	 */
	private void itemsNotIncluded(ArrayList<Element> itemElements){
		
		if( !itemElements.isEmpty() ){
			
			for(int itemIndex = 0; itemIndex < itemElements.size(); itemIndex++){
				itemElements.get(itemIndex).setAttribute(inView, "false");
			}
		} else {
			//TODO ?
		}
	}
	
// <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

	
// o.o.o.o.o.o.o.o.o.o.o.o.o.o.o HELP METHODS o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o
		
	/** Creates the filepath for user's view file
	 * @param userID
	 * @return 
	 */
	private String createFilepath(String userID){
		System.out.println("Filepath generated: " + rootfilepath + userID + ".xml"); //REMOVE For testing
		return rootfilepath + userID + ".xml";
	}
		
	/**
	 * 
	 */
	private void filelistUpToDate(){
		File viewfolder = new File(rootfilepath);
		File[] listOfFiles = viewfolder.listFiles();
		
		for(int fileIndex = 0; fileIndex < listOfFiles.length; fileIndex++ ){
			//Check that the file is a XML file
			if( listOfFiles[fileIndex].isFile() && listOfFiles[fileIndex].getName().contains(".xml") ){
				
				String userID = FilenameUtils.removeExtension(listOfFiles[fileIndex].getName().trim() );
				String filepath = rootfilepath + userID + ".xml";
				filelist.put(userID, filepath);
				
			}
		}
		
	}
	
	/**
	 * 
	 * @param userID
	 * @return
	 */
	private Document getUserviewDocument(String userID){
		
		System.out.println("getUserviewDocument: userID " + userID);
		
		String filepath = filelist.get(userID);
		//TODO REMOVE For testing
		System.out.println("getUserviewDocument: filepath " + filepath);
		
		if(filepath != null){
			//TODO REMOVE
			System.out.println("Is retrieved document null? " + getDocument(filepath) == null);
			
			return getDocument(filepath);
		} else {
			//TODO REMOVE
			System.out.println("NOOOOOOOOOOOO! Return null");
			return null;	//TODO Or throw exception? 
		}
	}
	
	/**
	 * Parse boolean value out of the string acquired from a XML file. 
	 * @param booleanString
	 * @return
	 */
	private boolean parseBoolean(String booleanString){
		
		if(booleanString.equals("0") || booleanString.equalsIgnoreCase("true")){
			return true;
		} else {
			return false;
		}
	}

	
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
			// View element should be the root of the file.
			 // If there are multiple view elements, something is terribly wrong.
			 // Therefore exception is thrown when such situation is encountered.
			 
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
		//return doc.getDocumentElement();
	}
	
	/**
	 * 
	 * @param viewElement
	 * @return
	 */
	private ArrayList<Element> getHouseElements(Element viewElement) {
		
		//TODO Throw exceptions?
		
		ArrayList<Element> houseElements = new ArrayList<Element>();

		NodeList housesRoot = viewElement.getElementsByTagName(housesTag);
		
		//There's actually just one 'houses' element but I'm going for the more general solution just in case.
		for(int i = 0; i < housesRoot.getLength(); i++){
			if(housesRoot.item(i).getNodeType() == Node.ELEMENT_NODE){
				NodeList houseNodes = ((Element)housesRoot.item(i)).getElementsByTagName(houseTag);
				
				//Go through and add to the ArrayList all the 'house' elements.
				for(int j = 0; j < houseNodes.getLength(); j++){
					if(houseNodes.item(j).getNodeType() == Node.ELEMENT_NODE){
						houseElements.add( (Element)houseNodes.item(j) );
					}
				}
			}
		}

		return houseElements;

	}
	
	/**
	 * 
	 * @param houseElement
	 * @return
	 */
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
	
	/**
	 * 
	 * @param roomElement
	 * @return
	 */
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