package model;

import org.xml.sax.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.w3c.dom.*;

//TODO
/*
 * 	public String[] getHouses() throws RemoteException; // Lista taloista
	
	public String[] getRooms(String houseName) throws RemoteException; // Lista talonhuoneista
	
	public String[] getItems(String houseName, String roomName) throws RemoteException;
 */


public class HouseHandler extends XMLHandler {

	private String filepath = "src/xmlfiles/HouseBuild.xml";
	private Document housesXML;
	private Element rootElement;
	private NodeList houseList;
	
	private ArrayList<Element> houses;
	private ArrayList<String> houseNames;
	
	private ArrayList<Element> rooms;
	private ArrayList<String> roomNames;
	
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
	public ArrayList<String> getHouseNameList(){
		
		//In case changes have been made
		updateHouseList();
		houseNames.clear();
		
		for(int i = 0; i < houseList.getLength(); i++){
		
			if(houseList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element houseElement = (Element) houseList.item(i);
				
				if(houseElement.getElementsByTagName("houseName").item(0) != null){
					//Add housename to the list
					houseNames.add( houseElement.getElementsByTagName("houseName").item(0).getTextContent() );
				}
			}
		}

		return houseNames;
	}
	
	//----------- LIST OF ROOMS (NAMES) -----------------------
	
	public ArrayList<String> getRoomNames(String housename){
		roomNames.clear();
		rooms.clear();
		rooms = getRooms(housename);
		
		for(int i = 0; i < rooms.size(); i++){
			if(rooms.get(i).getElementsByTagName("roomName").item(0) != null){
				roomNames.add( rooms.get(i).getElementsByTagName("roomName").item(0).getTextContent() );
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
			NodeList roomNodes = house.getElementsByTagName("room");
			
			for(int i = 0; i < roomNodes.getLength(); i++){
				rooms.add( (Element) roomNodes.item(i));
			}
		}	
		
		return rooms;
		
	}
	
	//----------- LIST OF ITEMS (NAMES) --------------------
	
	
	
	//----------- LIST OF ITEMS (ELEMENTS) --------------------
	
	
	
	// o-o-o-o-o-o-o-o-o HELP METHODS o-o-o-o-o-o-o-o-o-o-o-o
	
	private void updateHouseList(){
		System.out.println("rootElement is: " + rootElement.getTagName());
		NodeList houses = rootElement.getElementsByTagName("house");
		
		//TODO EI VAAN TOIMI SAATABA...
		
		for(int i = 0; i < houses.getLength(); i++){
			System.out.println("Node name should be house:" + houses.item(i).getNodeName());
		}
		
		houseList = rootElement.getElementsByTagName("house");
		//houseList = rootElement.getChildNodes();
		/*
		System.out.println("updateHouseList ran. Should print node names"); //TODO Remove, for testing
		
		for(int i = 0; i < houseList.getLength(); i++){
			System.out.println( ((Node)houseList.item(i)).getNodeName() );
		}*/
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
