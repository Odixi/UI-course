package model;

import java.util.ArrayList;

import org.w3c.dom.*;

import model.house.House;
import model.house.Room;
import model.items.SmartItem;

public class HouseObjectGenerator extends HouseHandler { //Or should it extend HouseHandler?

	private static final String housefilepath = "src/xmlfiles/houses.xml";
	
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
		
	
	public HouseObjectGenerator(){
		super();
	}
	
	public void buildHouses(){
		
		ArrayList<Element> houseElements = super.getHouseElements();
		ArrayList<House> houses = new ArrayList<House>();
		
		//Create rooms
		
		
		//return
	}
	
	public ArrayList<Room> buildRooms(String houseID){
		
		ArrayList<Element> roomElements = super.getRoomElements(houseID);
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		//Create items
		
		return rooms;
	}
	
	public ArrayList<SmartItem> buildItems(String houseID, String roomID){
		
		ArrayList<Element> itemElements = super.getItemElements(houseID, roomID);
		ArrayList<SmartItem> items = new ArrayList<SmartItem>();
		
		for(int i = 0; i < items.size(); i++){
			
			
			
		}
		
		return items;
	}
	
}
