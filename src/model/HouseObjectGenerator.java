package model;

import java.util.ArrayList;

import org.w3c.dom.*;

import model.house.House;
import model.house.Room;
import model.items.Appliance;
import model.items.AudioDevice;
import model.items.Light;
import model.items.Sensor;
import model.items.SensorType;
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
	private static final String sensorTypeTag = "sensorType";
		//Appliances
	private static final String applianceTag = "appliance";
	private static final String appliancenameTag = "applianceName";
	private static final String applianceIDTag = "applianceID";
	private static final String applianceTypeTag = "applianceType";
	
	public HouseObjectGenerator(){
		super();
	}
	
	//--------------- BUILD HOUSES ---------------------
	public ArrayList<House> buildHouses(){
		
		ArrayList<Element> houseElements = super.getHouseElements();
		ArrayList<House> houses = new ArrayList<House>();
		
		for(int i = 0; i < houseElements.size(); i++ ){
			if( houseElements.get(i).hasAttribute(houseIDTag) ){
				String id = houseElements.get(i).getAttribute(houseIDTag);
				
				//Check if the house has a name 
				if( houseElements.get(i).getElementsByTagName(housenameTag) != null){
					String name = houseElements.get(i).getElementsByTagName(housenameTag).item(0).getTextContent();
					
					System.out.println("TEST PRINT:" + name);
					
					houses.add( new House(id, name) );
				} else {
					houses.add( new House(id, "House "+i) );
				}
			} 
		}
		
		//Create rooms
		for(int i = 0; i < houses.size(); i++){
			houses.get(i).setRooms( buildRooms(houses.get(i).getID() ));
		}
		
		//return
		return houses;
	}
	
	//--------------- BUILD ROOMS ---------------------
	public ArrayList<Room> buildRooms(String houseID){
		
		ArrayList<Element> roomElements = super.getRoomElements(houseID);
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		for(int i = 0; i < roomElements.size(); i++ ){
			if( roomElements.get(i).hasAttribute(roomIDTag) ){
				rooms.add( new Room(roomElements.get(i).getAttribute(roomIDTag)) );
			}
		}
		
		//Create items
		for(int i = 0; i < rooms.size(); i++ ){
			rooms.get(i).setItems( buildItems(houseID, rooms.get(i).getID()) );
		}
		
		return rooms;
	}
	
	//--------------- BUILD ITEMS ---------------------
	public ArrayList<SmartItem> buildItems(String houseID, String roomID){
		
		ArrayList<Element> itemElements = super.getItemElements(houseID, roomID);
		ArrayList<SmartItem> items = new ArrayList<SmartItem>();
		
		for(int i = 0; i < items.size(); i++){
			//Element is light
			if(itemElements.get(i).hasAttribute(lightIDTag)){
				//If light has a name
				if(itemElements.get(i).getElementsByTagName(lightnameTag) != null){
					
					items.add( new Light( itemElements.get(i).getAttribute(lightIDTag), 
							itemElements.get(i).getElementsByTagName(lightnameTag).item(0).getTextContent().trim() ));
				} else {
					//No name specified, light gets default name defined by Light.java
					items.add(new Light( itemElements.get(i).getAttribute(lightIDTag)) );
				}
			}
			//Element is a sensor
			if(itemElements.get(i).hasAttribute(sensorIDTag)){
				items.add( buildSensor(itemElements.get(i)) );
			}
			//Element is an appliance
			if(itemElements.get(i).hasAttribute(applianceIDTag)){
				items.add( buildAppliance(itemElements.get(i)) );
			}
		}
	
		return items;
	}
	
	//--------------- BUILD SENSOR ---------------------
	public Sensor buildSensor(Element sensorElement){
		//Initialize return value;
		Sensor sensor = new Sensor(sensorElement.getAttribute(sensorIDTag));
		
		//Set type
		if(sensorElement.getElementsByTagName(sensorTypeTag) != null){
			String type = sensorElement.getElementsByTagName(sensorTypeTag).item(0).getTextContent().trim();
			
			if(type.equalsIgnoreCase("temperature")){
				sensor.setSensorType(SensorType.TEMPERATURE);
			} else if( type.equalsIgnoreCase("humidity") ){
				sensor.setSensorType(SensorType.HUMIDITY);
			} else if( type.equalsIgnoreCase("lightsensor") ){
				sensor.setSensorType(SensorType.LIGHT);
			}
		}
		
		//Set name
		if(sensorElement.getElementsByTagName(sensornameTag) != null){
			sensor.setSensorName( sensorElement.getElementsByTagName(sensornameTag).item(0).getTextContent() );
		
		} else {
			sensor.setDefaultName();
		}
		
		return sensor;
	}
	
	//--------------- BUILD APPLIANCE ---------------------
	public Appliance buildAppliance(Element applianceElement){
		Appliance machine = null;
		
		//Appliance type
		if(applianceElement.getElementsByTagName(applianceTypeTag) != null){
			String type = applianceElement.getElementsByTagName(applianceTypeTag).item(0).getTextContent().trim();
			
			if(type.equalsIgnoreCase("audioDevice")){
				machine = new AudioDevice(applianceElement.getAttribute(applianceIDTag));
			}
			else if(type.equalsIgnoreCase("turnOnOff")){
				machine = new Appliance(applianceElement.getAttribute(applianceIDTag));
			}
		}
		
		//Appliance name
		if(applianceElement.getElementsByTagName(appliancenameTag) != null){
			machine.setName( applianceElement.getElementsByTagName(appliancenameTag).item(0).getTextContent());
		} else {
			machine.setDefaultname();
		}
		
		return machine;
	}
	
}
