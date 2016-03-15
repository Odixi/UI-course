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
	
	private ArrayList<Element> houses;
	private ArrayList<String> houseNames;
	
	//TODO Probably not good to use these?
	private ArrayList<Element> rooms;
	private ArrayList<String> roomNames;
	
	private static final String houseTag = "house";
	private static final String housenameTag = "houseName";
	private static final String roomnameTag = "roomName";
	private static final String roomTag = "room";

	//Lights
	private static final String lightTag = "light";
	private static final String lightnameTag = "ligthName";
	//Sensors
	private static final String sensorTag = "sensor";
	private static final String sensornameTag = "sensorName";
	//Appliances
	private static final String applianceTag = "appliance";
	private static final String appliancenameTag = "applianceName";
	
	
	//CONSTRUCTOR
	public HouseHandler(){
		
		housesXML = getDocument(filepath);
		housesXML.getDocumentElement().normalize();
		rootElement = housesXML.getDocumentElement();
			
		//ArrayLists
		houses = new ArrayList<Element>();
		houseNames = new ArrayList<String>();
		
		rooms = new ArrayList<Element>();
		roomNames = new ArrayList<String>();
		
	} //constructor
	
	
	//-------- LIST OF HOUSES (NAMES) -------------
	
	public Hashtable<String, String> getHouseNameList(){
		
		//In case changes have been made
		updateHouseList();
		
		//Hashtable<Key, Value>
		Hashtable<String, String> houseNames = new Hashtable<String, String>();
		ArrayList<Element> houseElements = getHouseElements();
		
		for(int i = 0; i < houseElements.size(); i++){
			if(houseElements.get(i).getElementsByTagName(housenameTag).item(0) != null && houseElements.get(i).getAttribute("houseID") != null){
				houseNames.put(houseElements.get(i).getAttribute("houseID"), 
						houseElements.get(i).getElementsByTagName(housenameTag).item(0).getTextContent());	
			}
		}

		return houseNames;
	}
	
	/*
	public ArrayList<String> getHouseNameList(){
		
		//In case changes have been made
		updateHouseList();
		ArrayList<String> houseNames = new ArrayList<String>();
		
		ArrayList<Element> houseElements = getHouseElements();
		
		for(int i = 0; i < houseElements.size(); i++){
			if(houseElements.get(i).getElementsByTagName(housenameTag).item(0) != null){
				houseNames.add( houseElements.get(i).getElementsByTagName(housenameTag).item(0).getTextContent() );
			}
		}

		return houseNames;
	}*/

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
	
	public ArrayList<String> getRoomNames(String houseID){
		//TODO Is it good to do it this way? Should I create new AL:s instead?
		roomNames.clear();
		rooms.clear();
		rooms = getRooms(houseID);
		
		for(int i = 0; i < rooms.size(); i++){
			if(rooms.get(i).getElementsByTagName(roomnameTag).item(0) != null){
				roomNames.add( rooms.get(i).getElementsByTagName(roomnameTag).item(0).getTextContent() );
			}
		}
		
		return roomNames;
	}
	
	//----------- LIST OF ROOMS (ELEMENTS) --------------------
	
	public ArrayList<Element> getRooms(String housename){
		updateHouseList();
		rooms.clear();
		
		Element house = getHouse(housename);
		
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
	
	public ArrayList<String> getItemNames(String roomID){		//TODO What parameters are needed for getting correct items?
		ArrayList<String> itenNames = new ArrayList<String>();
		
		
		
		//TODO
		
		return null;
	}
	
	//----------- LIST OF ITEMS (ELEMENTS) --------------------
	
	public ArrayList<Element> getItemElements(){
		
		//TODO
		
		return null;
	}
	
	
	// o-o-o-o-o-o-o-o-o HELP METHODS o-o-o-o-o-o-o-o-o-o-o-o
	
	private void updateHouseList(){
		houseList = rootElement.getElementsByTagName(houseTag);
	}
	
		//----------- GET HOUSE -------------
	public Element getHouse(String housename){
		Element house = null;
		
		for(int i = 0; i < houseList.getLength(); i++){
			//Find the house that matches the name
			if(houseList.item(i).getTextContent().equals(housename)){
				house = (Element) houseList.item(i);
				break;
			}
		}
		return house;
	}
	
}
