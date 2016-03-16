package model;

import org.xml.sax.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.w3c.dom.*;

//TODO
/*
 * 	public String[] getHouses() throws RemoteException; // Lista taloista
	
	public String[] getRooms(String houseName) throws RemoteException; // Lista talonhuoneista
	
	public String[] getItems(String houseName, String roomName) throws RemoteException;
 */


public class HouseHandler extends XMLHandler {

	private String filepath = "src/xmlfiles/houses.xml";
	private Document housesXML;
	private Element rootElement;
	private NodeList houseList;
	
	// >>> XML tags
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
	private static final String lightnameTag = "ligthName";
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
	public HouseHandler(){
		
		housesXML = getDocument(filepath);
		housesXML.getDocumentElement().normalize();
		rootElement = housesXML.getDocumentElement();
		
	} //constructor
	
	
	//-------- LIST OF HOUSES (NAMES) -------------
	
	public Hashtable<String, String> getHouseNameList(){
		
		//In case changes have been made
		updateHouseList();
		
		//Hashtable<Key, Value>
		Hashtable<String, String> houseNames = new Hashtable<String, String>();
		ArrayList<Element> houseElements = getHouseElements();
		
		for(int i = 0; i < houseElements.size(); i++){
			if(houseElements.get(i).getElementsByTagName(housenameTag).item(0) != null && houseElements.get(i).getAttribute(houseIDTag) != null){
				houseNames.put(houseElements.get(i).getAttribute(houseIDTag), 
						houseElements.get(i).getElementsByTagName(housenameTag).item(0).getTextContent());	
			}
		}
		return houseNames;
	}

	//---------- LIST OF HOUSES (ELEMENTS) -----------------------------
	
	public ArrayList<Element> getHouseElements(){
		ArrayList<Element> houseElements = new ArrayList<Element>();
		updateHouseList();
		
		for(int i = 0; i < houseList.getLength(); i++){
			
			if(houseList.item(i).getNodeType() == Node.ELEMENT_NODE){
				houseElements.add( (Element) houseList.item(i) );
			}
		}
		return houseElements; 
	}
 	
	//----------- LIST OF ROOMS (NAMES) -----------------------
	/**
	 * Returns a hashtable where roomID is the key and room name value.
	 * @param houseID
	 * @return
	 */
	public Hashtable<String, String> getRoomNames(String houseID){
		
		Hashtable<String, String> roomNames = new Hashtable<String, String>();
		ArrayList<Element> rooms = getRoomElements(houseID);
		
		for(int i = 0; i < rooms.size(); i++){
			if(rooms.get(i).getElementsByTagName(roomnameTag).item(0) != null && rooms.get(i).getAttribute(roomIDTag) != null){
				roomNames.put(rooms.get(i).getAttribute(roomIDTag),
						rooms.get(i).getElementsByTagName(roomnameTag).item(0).getTextContent() );
			}
		}
		
		return roomNames;
	}
	
	//----------- LIST OF ROOMS (ELEMENTS) --------------------
	
	public ArrayList<Element> getRoomElements(String housename){
		updateHouseList();
		ArrayList<Element> rooms = new ArrayList<Element>();
		
		Element house = getHouseElement(housename);
		
		if(house == null){
			//TODO Do something
			
		} else {
			NodeList roomNodes = house.getElementsByTagName(roomTag);
			
			for(int i = 0; i < roomNodes.getLength(); i++){
				rooms.add( (Element) roomNodes.item(i));
			}
		}	
		return rooms;
	}
	
	//----------- LIST OF ITEMS (NAMES) --------------------
	
	public Hashtable<String, String> getItemNames(String houseID, String roomID){		//TODO What parameters are needed for getting correct items?
		
		Hashtable<String, String> itemNames = new Hashtable<String, String>();
		ArrayList<Element> items = getItemElements(houseID, roomID);
		
		for(int i = 0; i < items.size(); i++){
			if( items.get(i).getElementsByTagName(lightnameTag).item(0) != null && items.get(i).getAttribute(lightIDTag) != null){
				itemNames.put(items.get(i).getAttribute(lightIDTag),
						items.get(i).getElementsByTagName(lightnameTag).item(0).getTextContent() );
			}
		}
		
		return itemNames;
	}
	
	//----------- LIST OF ITEMS (ELEMENTS) --------------------
	
	public ArrayList<Element> getItemElements(String houseID, String roomID){
		
		ArrayList<Element> items = new ArrayList<Element>();
		Element room = getRoomElement(houseID, roomID);
		
		if(room == null){	//TODO Handle this situation better.
			System.out.println("Room " + roomID + " not found. Hence items can't be found either.");
			return null; 
		} else {
			NodeList lightNodes = room.getElementsByTagName(lightTag);
			
			//TODO
			
		}

		return items;
	}
	
	
	// o-o-o-o-o-o-o-o-o HELP METHODS o-o-o-o-o-o-o-o-o-o-o-o
	
	private void updateHouseList(){
		houseList = rootElement.getElementsByTagName(houseTag);
	}

	
	//--------------- GET HOUSE BY ID (ELEMENT) ---------------------------
	public Element getHouseElement(String houseID){
		
		updateHouseList();
		Element houseElement = null;
		
		for(int i = 0; i < houseList.getLength(); i++){
			//Find the house that matches the name
			if(houseList.item(i).getNodeType() == Node.ELEMENT_NODE){
				houseElement = (Element) houseList.item(i);
				
				if(houseElement.getAttribute(houseIDTag) != null && houseElement.getAttribute(houseIDTag).equals(houseID) ){
					break;	//Right element found;
				}
			}
		}
		
		if(houseElement == null){
			System.out.println("houseElement with ID " + houseID + " not found.");
		}
		
		return houseElement;
	}
	
	
	//--------------- GET ROOM BY ID (ELEMENT) ---------------------------
	public Element getRoomElement(String houseID, String roomID){
		
		Element houseElement = getHouseElement(houseID);
		Element roomElement = null;
		
		if(houseElement == null){
			System.out.println("Because house " + houseID + " can't be found, room " + roomID + " can't be found either.");
			return null;
		} else {
			NodeList roomList = houseElement.getElementsByTagName(roomTag);
			
			for(int i = 0; i < roomList.getLength(); i++){
				if(roomList.item(i).getNodeType() == Node.ELEMENT_NODE){
					roomElement = (Element) roomList.item(i);
					
					if(roomElement.getAttribute(roomIDTag) != null && roomElement.getAttribute(roomIDTag).equals(roomID) ){
						break;	//Right element found;
					}
				}
			}
		}
		return roomElement;
	}
	
}
