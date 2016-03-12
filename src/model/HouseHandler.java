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
	private NodeList houseList;
	private Document housesXML;
	private Element rootElement;
	private ArrayList<String> houseNames;
	
	private ArrayList<String> rooms;
//	private ArrayList<String> 
	
	//CONSTRUCTOR
	public HouseHandler(){
		
		housesXML = getDocument(filepath);
		housesXML.getDocumentElement().normalize();
		rootElement = housesXML.getDocumentElement();
		
		//ArrayLists
		houseNames = new ArrayList<String>();
		rooms = new ArrayList<String>();
		
	} //constructor
	
	
	//-------- LIST OF HOUSES (NAMES) -------------
	public ArrayList<String> getHouseNameList(){
		
		//In case changes have been made
		updateHouseList();
		houseNames.clear();
		
		for(int i = 0; i < houseList.getLength(); i++){
			//TODO Quite likely going to change...
			houseNames.add( houseList.item(i).getTextContent() );
		}

		return houseNames;
	}
	
	//----------- LIST OF ROOMS --------------------
	
	//TODO MUUTA
	
	public ArrayList<String> getRooms(String housename){
		updateHouseList();
		rooms.clear();
		
		Element house = getHouse(housename);
		
		if(house == null){
			//TODO Do something
			
		} else {
		
			NodeList roomNodes = house.getElementsByTagName("room");
			
			for(int i = 0; i < roomNodes.getLength(); i++){
				rooms.add( roomNodes.item(i).getTextContent() );
			}
		}
			
		return rooms;
		
	}
	
	//----------- LIST OF ITEMS --------------------
	
	
	
	
	// o-o-o-o-o-o-o-o-o HELP METHODS o-o-o-o-o-o-o-o-o-o-o-o
	
	private void updateHouseList(){
		houseList = housesXML.getElementsByTagName("house");
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
