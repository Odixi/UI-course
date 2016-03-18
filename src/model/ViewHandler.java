package model;

import java.util.ArrayList;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;

public class ViewHandler extends XMLHandler {

	private String filepath = "src/xmlfiles/views.xml";
	private Document viewsXML;
	private Element rootElement;
	private NodeList viewNodeList;
	
	// >>> XML tags
	
	private static final String inView = "inView";
	
	private static final String viewTag = "view";	
	private static final String viewIDTag = "viewID";

	private static final String userIDTag = "userID";
	
	private static final String housesTag = "houses";
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
	
	//CONSTRUCTOR
	
	public ViewHandler(){

		viewsXML = getDocument(filepath);
		viewsXML.getDocumentElement().normalize();
		
		rootElement = viewsXML.getDocumentElement();
		
	} //constructor
	
	
	//--------------- SAVE THE USERVIEW TO THE XML FOR THE FIRST TIME --------------------
	
	public void createDefaultView(String userID){	//Could also be called setUserView()
		
		
		
		
	}
	
	
	//--------------- UPDATE THE USERVIEW --------------------
	
	public void updateUserView(String userID, Hashtable<String, Boolean> userview){
			
		//Iterate through views, find userID == userIDTag.getTextContent();
		if( userHasView(userID) ){
			
			Element view = getViewElement(userID);
			ArrayList<Element> houseElements = getHouseElements(view);
			updateHouses(houseElements, userview);

		} else {
			
			//TODO
		
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
				if(houseElements.get(i).getAttribute(houseIDTag) != null && houseElements.get(i).getAttribute(inView) != null){
					
					Boolean included = userview.get( houseElements.get(i).getAttribute(houseIDTag) );
					houseElements.get(i).setAttribute(inView, included.toString());

					if(included == false){
						//TODO
					} else if(included == true) {
						
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
				if(roomElements.get(j).getAttribute(roomIDTag) != null && roomElements.get(j).getAttribute(inView) != null){
					Boolean included = userview.get( roomElements.get(j).getAttribute(roomIDTag));
					roomElements.get(j).setAttribute(inView, included.toString());
				
					ArrayList<Element> itemElements = getItemElements( roomElements.get(j) );
					
					if(included == false){
						//TODO
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
		
		//TODO
		
		if( !itemElements.isEmpty() ){
			
			for(int k = 0; k < itemElements.size(); k++){
				//inView = true/false?
				if(itemElements.get(k).hasAttribute(lightIDTag)){
					if(itemElements.get(k).getAttribute(inView) != null){
						 
						Boolean included = userview.get( roomElements.get(j).getAttribute(roomIDTag));
						roomElements.get(j).setAttribute(inView, included.toString());
					}	
				} else if(itemElements.get(k).hasAttribute(sensorIDTag)){
					if(itemElements.get(k).getAttribute(inView) != null){
						
					}
				} else if(itemElements.get(k).hasAttribute(applianceIDTag)){
					if(itemElements.get(k).getAttribute(inView) != null){
					}
				}
			}
		} else {
			//TODO ?
		}
	}
	
	
	
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
	
	//Pretty studip and probably unnecessary method.
	private boolean userHasView(String userID){
		boolean hasView = false;
		
		for(int i = 0; i < viewNodeList.getLength(); i++){
			if(viewNodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element viewElement = (Element) viewNodeList.item(i);
			
				if(viewElement.getAttribute(userIDTag) != null && viewElement.getAttribute(userIDTag).equals(userID) ){
					hasView = true;
					break;
				}
			}
		}
		return hasView;	
	}
	

	private Element getViewElement(String userID){
		Element viewElement = null;
		
		for(int i = 0; i < viewNodeList.getLength(); i++){
			if(viewNodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element e = (Element) viewNodeList.item(i);
			
				if(e.getAttribute(userIDTag) != null && e.getAttribute(userIDTag).equals(userID) ){
					viewElement = e;
					break;
				}
			}
		}
		return viewElement;
	}
	
	
	// o o o o o o o o o o o o GET HOUSES o o o o o o o o o o o o 
	private ArrayList<Element> getHouseElements(Element view){
		ArrayList<Element> houseElements = new ArrayList<Element>();
		updateViewNodeList();
		
		NodeList houses = view.getElementsByTagName(housesTag);
		
		//There's actually just one 'houses' element but I'm going for the more general solution just in case.
		for(int i = 0; i < houses.getLength(); i++){
			if(houses.item(i).getNodeType() == Node.ELEMENT_NODE){
				NodeList houseNodes = ((Element)houses.item(i)).getElementsByTagName(houseTag);
				
				for(int j = 0; j < houseNodes.getLength(); j++){
					if(houseNodes.item(i).getNodeType() == Node.ELEMENT_NODE && houseNodes.item(i) != null){
						houseElements.add( (Element)houseNodes.item(i) );
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
