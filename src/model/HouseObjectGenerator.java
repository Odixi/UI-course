package model;

import java.util.ArrayList;
import java.util.Hashtable;
import org.w3c.dom.*;
import exceptions.ElementNullException;
import model.house.House;
import model.house.Room;
import model.items.Appliance;
import model.items.AudioDevice;
import model.items.Controller;
import model.items.ControllerType;
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
	private static final String lightnameTag = "lightName";
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
	
	private static final String controllerTag = "controller";
	private static final String controllernameTag = "controllerName";
	private static final String controllerIDTag = "controllerID";
	private static final String controllerTypeTag = "controllerType";
	
	public HouseObjectGenerator(){
		super();
	}
	
	//--------------- BUILD HOUSES ---------------------
	public Hashtable<String, House> buildHouses() throws ElementNullException{
		
		ArrayList<Element> houseElements = super.getHouseElements();
		Hashtable<String, House> houses = new Hashtable<String, House>();
		
		for(int i = 0; i < houseElements.size(); i++ ){
			if( houseElements.get(i).hasAttribute(houseIDTag) ){
				String id = houseElements.get(i).getAttribute(houseIDTag);
				
				//Check if the house has a name 
				if( houseElements.get(i).getElementsByTagName(housenameTag) != null){
					String name = houseElements.get(i).getElementsByTagName(housenameTag).item(0).getTextContent();
					
					houses.put(id, new House(id, name));
					
				} else {
					//houses.add( new House(id, "House "+i) );
					houses.put(id, new House(id, "House " +i));
				}
			} 
		}
		
		for(String houseID : houses.keySet()){
			houses.get(houseID).setRooms( buildRooms(houseID) );
		}
		
		return houses;
	}
	
	//--------------- BUILD ROOMS ---------------------
	public Hashtable<String, Room> buildRooms(String houseID) throws ElementNullException{
		
		ArrayList<Element> roomElements = super.getRoomElements(houseID);
		Hashtable<String, Room> rooms = new Hashtable<String, Room>();
		
		for(int i = 0; i < roomElements.size(); i++ ){
			if( roomElements.get(i).hasAttribute(roomIDTag) ){
				String id = roomElements.get(i).getAttribute(roomIDTag);
				
				//Check if the room has a name 
				if( roomElements.get(i).getElementsByTagName(roomnameTag) != null){
					String name = roomElements.get(i).getElementsByTagName(roomnameTag).item(0).getTextContent();
					
					rooms.put(id, new Room(id, name) );
				} else {
					rooms.put(id, new Room(id, "Room "+i) );
				}	
			}
		}
		
		//Create items
		for(String roomID : rooms.keySet()){
			rooms.get(roomID).setItems( buildItems(houseID, roomID) );
		}
		
		return rooms;
	}
	
	//--------------- BUILD ITEMS ---------------------
	public Hashtable<String, SmartItem> buildItems(String houseID, String roomID) throws ElementNullException{
		
		ArrayList<Element> itemElements = super.getItemElements(houseID, roomID);
		Hashtable<String, SmartItem> items = new Hashtable<String, SmartItem>();
		
		for(int itemIndex = 0; itemIndex < itemElements.size(); itemIndex++){
			
			//Element is light
			if(itemElements.get(itemIndex).hasAttribute(lightIDTag)){
				
				String lightID = itemElements.get(itemIndex).getAttribute(lightIDTag);
				
				//If light has a name
				if(itemElements.get(itemIndex).getElementsByTagName(lightnameTag) != null){
					items.put(lightID, new Light( lightID, itemElements.get(itemIndex).getElementsByTagName(lightnameTag).item(0).getTextContent()));
			
				} else {
					//No name specified, light gets default name defined by Light.java
					items.put(lightID, new Light(lightID) );
				}
			}
			//Element is a sensor
			else if(itemElements.get(itemIndex).hasAttribute(sensorIDTag)){
				items.put(itemElements.get(itemIndex).getAttribute(sensorIDTag),
						buildSensor(itemElements.get(itemIndex)) );
			}
			//Element is an appliance
			else if(itemElements.get(itemIndex).hasAttribute(applianceIDTag)){
				items.put(itemElements.get(itemIndex).getAttribute(applianceIDTag),
						buildAppliance(itemElements.get(itemIndex)) );
			}
			//Element is a controller
			else if(itemElements.get(itemIndex).hasAttribute(controllerIDTag)){
				items.put(itemElements.get(itemIndex).getAttribute(controllerIDTag),
						buildController(itemElements.get(itemIndex)));
			}
		}
	
		return items;
	}
	
	//--------------- BUILD SENSOR ---------------------
	public Sensor buildSensor(Element sensorElement){
		//Initialize return value;
		Sensor sensorObject = new Sensor(sensorElement.getAttribute(sensorIDTag));
		
		//Set type
		if(sensorElement.getElementsByTagName(sensorTypeTag) != null){
			String type = sensorElement.getElementsByTagName(sensorTypeTag).item(0).getTextContent().trim();
			
			if(type.equalsIgnoreCase("temperature")){
				sensorObject.setSensorType(SensorType.TEMPERATURE);
			} else if( type.equalsIgnoreCase("humidity") ){
				sensorObject.setSensorType(SensorType.HUMIDITY);
			} else if( type.equalsIgnoreCase("lightsensor") ){
				sensorObject.setSensorType(SensorType.LIGHT);
			}
		}
		
		//Set name
		if(sensorElement.getElementsByTagName(sensornameTag) != null){
			sensorObject.setSensorName( sensorElement.getElementsByTagName(sensornameTag).item(0).getTextContent() );
		
		} else {
			sensorObject.setDefaultName();
		}
		
		return sensorObject;
	}
	
	//--------------- BUILD APPLIANCE ---------------------
	public Appliance buildAppliance(Element applianceElement){
		Appliance applianceObject = null;
		
		//Appliance type
		if(applianceElement.getElementsByTagName(applianceTypeTag) != null){
			String type = applianceElement.getElementsByTagName(applianceTypeTag).item(0).getTextContent().trim();
			
			if(type.equalsIgnoreCase("audioDevice")){
				applianceObject = new AudioDevice(applianceElement.getAttribute(applianceIDTag));
			}
			else if(type.equalsIgnoreCase("turnOnOff")){
				applianceObject = new Appliance(applianceElement.getAttribute(applianceIDTag));
			}
		}
		
		//Appliance name
		if(applianceObject != null){
			if(applianceElement.getElementsByTagName(appliancenameTag) != null){
				applianceObject.setName( applianceElement.getElementsByTagName(appliancenameTag).item(0).getTextContent());
			} else {
				applianceObject.setDefaultname();
			}
		}
		
		return applianceObject;
	}
	
	//--------------- BUILD CONTROLLER ---------------------
	public Controller buildController(Element controllerElement){
		Controller controllerObject = null;
		
		//Controller type
		if(controllerElement.getElementsByTagName(controllerTypeTag) != null){
			String type = controllerElement.getElementsByTagName(controllerTypeTag).item(0).getTextContent().trim();
			
			if(type.equalsIgnoreCase("temperature")){
				controllerObject = new Controller( controllerElement.getAttribute(controllerIDTag), ControllerType.TEMPERATURE );
			}
			else if(type.equalsIgnoreCase("humidity")){
				controllerObject = new Controller( controllerElement.getAttribute(controllerIDTag), ControllerType.HUMIDITY );
			}
			else if(type.equalsIgnoreCase("light")){
				controllerObject = new Controller( controllerElement.getAttribute(controllerIDTag), ControllerType.LIGHT );
			}
			
			//TODO More types?
		}
		
		//Controller name
		if(controllerObject != null){
			if( controllerElement.getElementsByTagName(controllernameTag) != null ){
				controllerObject.setName( controllerElement.getElementsByTagName(controllernameTag).item(0).getTextContent() );
			} else {
				//If controller doesn't have name defined, set default name
				controllerObject.setDefaultName();
			}
		}
		
		return controllerObject;
	}
	
}
