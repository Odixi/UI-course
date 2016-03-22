package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;

import model.house.Room;

public class ViewHandler extends XMLHandler {

	private String filepath = "src/xmlfiles/views.xml";
	private Document viewsXML;
	private Element rootElement;
	private NodeList viewNodeList;
	
	private HouseHandler houseHandler;
	
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
	
	//PREFIXES ETC.
	private static final String viewNS = "http://www.example.org/views";
	private static final String nsPrefix = "tns";
	
	private static final String viewPrefix = "view";
	private static final String userPrefix = "user";
	private static final String housesPrefix = "houses";
	
	//CONSTRUCTOR
	
	public ViewHandler(HouseHandler houseHandler){

		//Isn't needed too much.... But you know.
		this.houseHandler = houseHandler;
		
		viewsXML = getDocument(filepath);
		viewsXML.getDocumentElement().normalize();
		
		rootElement = viewsXML.getDocumentElement();
		
	} //constructor
	
	//TODO userHasView is checked kind of unnecessarily in createDefaultView & updateUserView
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>> SET USERVIEW  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public void setUserView(String userID, Hashtable<String, Boolean> userview){
		
		if( !userHasView(userID) ){
			createDefaultView(userID);
		}
		
		updateUserView(userID, userview);
	
	}

	
	//--------------- SAVE THE USERVIEW TO THE XML FOR THE FIRST TIME --------------------
	/**
	 * Creates a view where all houses, rooms & items are set not included. (Basically an empty view.)
	 * @param userID
	 */
	public void createDefaultView(String userID){	//Could also be called setUserView()

		//Check - just in case - if the user already has a view.
		if( userHasView(userID) ){
			
			//TODO Check if this actually works
			
			System.out.println("User " + userID + " already has a view.");
			
		} else {
			
			Element view = viewsXML.createElementNS(viewNS, nsPrefix + ":" + viewPrefix);
			view.setAttribute(viewIDTag, UUID.randomUUID().toString());
			rootElement.appendChild(view);
			
			//User
			Element user = viewsXML.createElementNS(viewNS, nsPrefix + ":" + userPrefix);
			user.setAttribute(userIDTag, userID);
			view.appendChild(user);
			
			Element housesRoot = houseHandler.getRootElement();
			
			//Copy houses structure to the view
			Element newhouses = (Element) housesRoot.cloneNode(true);
			viewsXML.adoptNode(newhouses);
			view.appendChild(newhouses);
			viewsXML.renameNode(newhouses, viewNS, nsPrefix + ":" + housesPrefix);

			//Go through all elements and set them not included (inView = false) in the view.
		
			ArrayList<Element> houseElements = getHouseElements(view);
			
			System.out.println("Number of house elements: " + houseElements.size());
			
			housesNotIncluded(houseElements);

			for(Element house : houseElements){
				ArrayList<Element> roomElements = getRoomElements(house);
				roomsNotIncluded(roomElements);
				
				for(Element room : roomElements){
					itemsNotIncluded( getItemElements(room) );
				}
			}
			
			//Save the information to the XML file (self created method in XMLHandler class)
			writeXML(viewsXML, filepath);
			
			//FOR TESTING ETC:
			System.out.println("Userview for user " + userID + " created!");
		}
	}
	
	
	//--------------- UPDATE THE USERVIEW --------------------
	
	public void updateUserView(String userID, Hashtable<String, Boolean> userview){
		
			Element view = getViewElement(userID);
			ArrayList<Element> houseElements = getHouseElements(view);
			updateHousesIncluded(houseElements, userview);
	}
	
	//-------------- UPDATE THE HOUSE STRUCTURE IN VIEW ----------------------
	
	public void updateTheHouseStructureInView(String userID){
		
		if( userHasView(userID) ){
			
			//NodeList houses = viewsXML.getElementsByTagName();
			
		} else {
			
			//TODO Create view?
			
		}
	}
	
	//--------------- UPDATE THE HOUSES -------------------------------
	public void updateHousesIncluded(ArrayList<Element> houseElements, Hashtable<String, Boolean> userview){
		//Iterate through houses
		if( !houseElements.isEmpty() ){
			for(int i = 0; i < houseElements.size(); i++){
				
				//Rooms
				ArrayList<Element> roomElements = getRoomElements(houseElements.get(i));
				
				//inView = true/false?
				if(houseElements.get(i).getAttribute(houseIDTag) != null){
						
					Boolean included = userview.get( houseElements.get(i).getAttribute(houseIDTag) );
					houseElements.get(i).setAttribute(inView, included.toString());

					if(included == false){
						
						//TODO ?
						
					} else if(included == true) {
						updateRoomsIncluded(roomElements, userview);
					}
				}
			}
		}
	}
	
	//--------------- UPDATE THE ROOMS ---------------------------------
	
	public void updateRoomsIncluded(ArrayList<Element> roomElements, Hashtable<String, Boolean> userview){
		
		if( !roomElements.isEmpty() ){
			for(int j = 0; j < roomElements.size(); j++){
				
				//inView = true/false?
				if(roomElements.get(j).getAttribute(roomIDTag) != null){

					Boolean included = userview.get( roomElements.get(j).getAttribute(roomIDTag));
					roomElements.get(j).setAttribute(inView, included.toString());
				
					ArrayList<Element> itemElements = getItemElements( roomElements.get(j) );
					
					if(included == false){
						//If the room is not included in the view, the items in it can't be either
						itemsNotIncluded(itemElements);
						
					}
					else {
						//Update the items
						updateItemsIncluded(itemElements, userview);
					}
				}				
			}
		}
	}
	//--------------- UPDATE THE ITEMS --------------------------------
							
	public void updateItemsIncluded(ArrayList<Element> itemElements, Hashtable<String, Boolean> userview){

		if( !itemElements.isEmpty() ){
			
			for(int k = 0; k < itemElements.size(); k++){
				//inView = true/false?
				if(itemElements.get(k).hasAttribute(lightIDTag)){
						Boolean included = userview.get( itemElements.get(k).getAttribute(lightIDTag));
						itemElements.get(k).setAttribute(inView, included.toString());
						
				} else if(itemElements.get(k).hasAttribute(sensorIDTag)){
						Boolean included = userview.get( itemElements.get(k).getAttribute(sensorIDTag));
						itemElements.get(k).setAttribute(inView, included.toString());
						
				} else if(itemElements.get(k).hasAttribute(applianceIDTag)){
						Boolean included = userview.get( itemElements.get(k).getAttribute(applianceIDTag));
						itemElements.get(k).setAttribute(inView, included.toString());
				}
			}
		} else {
			//TODO ?
		}
	}
	
	// <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
	
	//--------------- SET HOUSES NOT INCLUDE IN VIEW -------------------------------
	public void housesNotIncluded(ArrayList<Element> houseElements){
		//Iterate through houses
		System.out.println("Is houseElements empty? " + houseElements.isEmpty());
		
		if( !houseElements.isEmpty() ){
			for(int i = 0; i < houseElements.size(); i++){
				//inView = true/false?
				houseElements.get(i).setAttribute(inView, "false");
			}
		} else {
			//TODO ?
		}
	}
	
	//-------------------- SET ROOMS NOT INCLUDED IN VIEW ---------------------------------
	
	public void roomsNotIncluded(ArrayList<Element> roomElements){
		
		if( !roomElements.isEmpty() ){
			for(int j = 0; j < roomElements.size(); j++){
				
				//inView = true/false?
				if(roomElements.get(j).getAttribute(roomIDTag) != null){
					roomElements.get(j).setAttribute(inView, "false");
				}				
			}
		} else {
			//TODO ?
		}
	}
	//-------------------- SET ITEMS NOT INCLUDED IN VIEW --------------------------------
	
	public void itemsNotIncluded(ArrayList<Element> itemElements){

		if( !itemElements.isEmpty() ){
			
			for(int k = 0; k < itemElements.size(); k++){
				itemElements.get(k).setAttribute(inView, "false");
			}
		} else {
			//TODO ?
		}
	}

	// <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
	
	//--------------- RETURN THE USERVIEW STRUCTURE --------------------
	
	public Hashtable<String, Boolean> getUserView(String userID){
		Hashtable<String, Boolean> userView = new Hashtable<String, Boolean>();
		
		//Iterate through views, find userID == userIDTag.getTextContent();
		if( userHasView(userID) ){

			Element view = getViewElement(userID);
			ArrayList<Element> houseElements = getHouseElements(view);
			
			//Iterate through houses
			if( !houseElements.isEmpty() ){
				for(int i = 0; i < houseElements.size(); i++){
					
					//inView = true/false?
					if(houseElements.get(i).getAttribute(houseIDTag) != null && houseElements.get(i).getAttribute(inView) != null){
						//Add house info (ID, inView) to hashtable 
						userView.put(houseElements.get(i).getAttribute(houseIDTag), parseBoolean( houseElements.get(i).getAttribute(inView).trim() ) );
					}
					//Rooms
					ArrayList<Element> roomElements = getRoomElements(houseElements.get(i));
					
					if( !roomElements.isEmpty() ){
						for(int j = 0; j < roomElements.size(); j++){
							
							//inView = true/false?
							if(roomElements.get(i).getAttribute(roomIDTag) != null && roomElements.get(i).getAttribute(inView) != null){
								userView.put(roomElements.get(i).getAttribute(roomIDTag), parseBoolean(roomElements.get(i).getAttribute(inView)) );
							}
							
							//Lights, sensors and appliance
							ArrayList<Element> itemElements = getItemElements( roomElements.get(j) );
							if( !itemElements.isEmpty() ){
								
								for(int k = 0; k < itemElements.size(); k++){
									//inView = true/false?
									if(itemElements.get(k).hasAttribute(lightIDTag)){
										if(itemElements.get(k).getAttribute(inView) != null){
											userView.put(itemElements.get(k).getAttribute(lightIDTag), parseBoolean(itemElements.get(k).getAttribute(inView)) );
										}	
									} else if(itemElements.get(k).hasAttribute(sensorIDTag)){
										if(itemElements.get(k).getAttribute(inView) != null){
											userView.put(itemElements.get(k).getAttribute(sensorIDTag), parseBoolean(itemElements.get(k).getAttribute(inView)) );
										}
									} else if(itemElements.get(k).hasAttribute(applianceIDTag)){
										if(itemElements.get(k).getAttribute(inView) != null){
											userView.put(itemElements.get(k).getAttribute(applianceIDTag), parseBoolean(itemElements.get(k).getAttribute(inView)) );
										}
									}
								}
							}
						}
					}
				}	
			}				
		} else {

			//TODO 
			//return list where for all: inView = false
			
	
		}
		
		return userView;
	}
	
	
// o.o.o.o.o.o.o.o.o.o.o.o.o.o.o HELP METHODS o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o
	
	private boolean parseBoolean(String b){
		if(b.equals("0") || b.equalsIgnoreCase("true")){
			return true;
		} else {
			return false;
		}
	}
	
	private void updateViewNodeList(){
		viewNodeList = viewsXML.getElementsByTagName(viewTag);
	}
	
	//.o.o.o.o.o.o.o.o.o.o.o. USER HAS VIEW? .o.o.o.o.o.o.o.o.o.o.o.

	private boolean userHasView(String userID){
		boolean hasView = false;

		if( getViewElement(userID) != null){
			hasView = true;
		} 
		
		System.out.println("userHasView checked for user " + userID + ". Result: " + hasView); //REMOVE
		return hasView;
	}
	
	// .o.o.o.o.o.o.o.o.o.o.o. GET VIEW ELEMENT .o.o.o.o.o.o.o.o.o.o.o.
	/**
	 * 
	 * @param userID
	 * @return
	 */
	private Element getViewElement(String userID){
		
		Element returnViewElement = null;
		updateViewNodeList();
		
		//Iterate through views
		for(int viewIndex = 0; viewIndex < viewNodeList.getLength(); viewIndex++){
			
			//Check that viewnode is element node (for type casting)
			if( viewNodeList.item(viewIndex) != null && viewNodeList.item(viewIndex).getNodeType() == Node.ELEMENT_NODE ){
				
				Element viewElement = (Element) viewNodeList.item(viewIndex);
				NodeList userNodes = viewElement.getElementsByTagName(userTag);
				
				for( int userNodeIndex = 0; userNodeIndex < userNodes.getLength(); userNodeIndex++ ){
					//Check that usernode is element node (for type casting)
					if( userNodes.item(userNodeIndex) != null && userNodes.item(userNodeIndex).getNodeType() == Node.ELEMENT_NODE){
						
						Element userElement = (Element) userNodes.item(userNodeIndex);
						
						//Check if the userID matches userNodes userID
						if(userElement.hasAttribute(userIDTag)){
							
							System.out.println("User has ID!");
							
							if( userElement.getAttribute(userIDTag).equals(userID) ){
							
								//Correct view found
								System.out.println("Correct view found! Is it null? " + (returnViewElement == null)); //TODO REMOVE
								
								returnViewElement = viewElement;
								break;
								
							}
						}
					}
				}
			}
		}
		
		System.out.println("Is view null?" + (returnViewElement == null));
		
		return returnViewElement;
	}
	
	// o o o o o o o o o o o o GET HOUSES IN VIEW o o o o o o o o o o o o 
	/**
	 *
	 * @param view
	 * @return
	 */ //TODO Kuvaus
	private ArrayList<Element> getHouseElements(Element view){
		ArrayList<Element> houseElements = new ArrayList<Element>();
		updateViewNodeList();
		
		NodeList houses = view.getElementsByTagName(housesTag);

		//There's actually just one 'houses' element but I'm going for the more general solution just in case.
		for(int i = 0; i < houses.getLength(); i++){
			if(houses.item(i).getNodeType() == Node.ELEMENT_NODE){
				NodeList houseNodes = ((Element)houses.item(i)).getElementsByTagName(houseTag);
				
				for(int j = 0; j < houseNodes.getLength(); j++){
					if(houseNodes.item(j).getNodeType() == Node.ELEMENT_NODE){
						houseElements.add( (Element)houseNodes.item(j) );
					}
				}
			}
		}

		return houseElements;
	}
	
	// o o o o o o o o o o o o GET ROOMS o o o o o o o o o o o o 
	private ArrayList<Element> getRoomElements(Element house){
		ArrayList<Element> roomElements = new ArrayList<Element>();
		updateViewNodeList();
		
		NodeList rooms = house.getElementsByTagName(roomTag);
		
		//There's actually just one 'houses' element but I'm going for the more general solution just in case.
		for(int i = 0; i < rooms.getLength(); i++){
			if(rooms.item(i).getNodeType() == Node.ELEMENT_NODE && rooms.item(i) != null){
				roomElements.add( (Element) rooms.item(i));
			}
		}
		return roomElements;
	}
	
	// o o o o o o o o o o o o GET ITEMS o o o o o o o o o o o o 
	private ArrayList<Element> getItemElements(Element room){
		ArrayList<Element> itemElements = new ArrayList<Element>();
		
		//Lights
		NodeList lightNodes = room.getElementsByTagName(lightTag);
		for(int i = 0; i < lightNodes.getLength(); i++){
			if(lightNodes.item(i).getNodeType() == Node.ELEMENT_NODE && lightNodes.item(i) != null){
				itemElements.add( (Element) lightNodes.item(i) );
			}
		}
		//Sensors
		NodeList sensorNodes = room.getElementsByTagName(sensorTag);
		for(int i = 0; i < sensorNodes.getLength(); i++){
			if(sensorNodes.item(i).getNodeType() == Node.ELEMENT_NODE && sensorNodes.item(i) != null){
				itemElements.add( (Element) sensorNodes.item(i) );
			}
		}
		//Appliances
		NodeList applianceNodes = room.getElementsByTagName(applianceTag);
		for(int i = 0; i < applianceNodes.getLength(); i++){
			if(applianceNodes.item(i).getNodeType() == Node.ELEMENT_NODE && applianceNodes.item(i) != null){
				itemElements.add( (Element) applianceNodes.item(i) );
			}
		}
		return itemElements;
	}
	
}
