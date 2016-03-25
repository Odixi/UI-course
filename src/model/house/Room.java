package model.house;

import java.util.ArrayList;
import java.util.Hashtable;

import model.items.Appliance;
import model.items.Light;
import model.items.Sensor;
import model.items.SmartItem;

public class Room {

	private String name;
	private final String roomID; 
	
	private Hashtable<String, SmartItem> items;
	
	//--------- CONSTUCTORS --------------------------
	
	public Room(String roomID){
		this.roomID = roomID;
	}
	
	public Room(String roomID, String name){
		this.roomID = roomID;
		this.name = name;
	}
	
	//----------- ID & NAME ---------------------
	
	public String getID(){
		return roomID;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	//---------- SET & GET ITEMS ----------------
	
	public void setItems(Hashtable<String, SmartItem> items){
		this.items = items;
	}

	public Hashtable<String, SmartItem> getItems(){
		return items;
	}
	
	//---------- GET LIGHTS ----------------
	public Hashtable<String, Light> getLights(){
		Hashtable<String, Light> lights = new Hashtable<String, Light>();
		
		for(String itemID : lights.keySet()){
			if(lights.get(itemID) instanceof Light){
				lights.put(itemID, (Light)lights.get(itemID));
			}
		}
		return lights;
	}
	
	//---------- GET SENSORS ----------------
	public Hashtable<String, Sensor> getSensors(){
		Hashtable<String, Sensor> sensors = new Hashtable<String, Sensor>();
		
		for(String itemID : sensors.keySet()){
			if(sensors.get(itemID) instanceof Sensor){
				sensors.put(itemID, (Sensor)sensors.get(itemID));
			}
		}
		return sensors;
	}
	
	//---------- GET APPLIANCES ----------------
	public Hashtable<String, Appliance> getAppliances(){
		Hashtable<String, Appliance> appliances = new Hashtable<String, Appliance>();
		
		for(String itemID : appliances.keySet()){
			if(appliances.get(itemID) instanceof Appliance){
				appliances.put(itemID, (Appliance)appliances.get(itemID));
			}
		}
		return appliances;
	}
	
	
}
