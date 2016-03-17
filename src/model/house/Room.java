package model.house;

import java.util.ArrayList;

import model.items.Appliance;
import model.items.Light;
import model.items.Sensor;
import model.items.SmartItem;

public class Room {

	private String name;
	private final String roomID; 
	
	private ArrayList<SmartItem> items;
	
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
	
	public void setItems(ArrayList<SmartItem> items){
		this.items = items;
	}

	public ArrayList<SmartItem> getItems(){
		return items;
	}
	
	//---------- GET LIGHTS ----------------
	public ArrayList<Light> getLights(){
		ArrayList<Light> lights = new ArrayList<Light>();
		
		for(SmartItem item : items){
			if(item instanceof Light){
				lights.add((Light)item);
			}
		}
		return lights;
	}
	
	//---------- GET SENSORS ----------------
	public ArrayList<Sensor> getSensors(){
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		
		for(SmartItem item : items){
			if(item instanceof Sensor){
				sensors.add((Sensor)item);
			}
		}
		return sensors;
	}
	
	//---------- GET APPLIANCES ----------------
	public ArrayList<Appliance> getAppliances(){
		ArrayList<Appliance> appliances = new ArrayList<Appliance>();
		
		for(SmartItem item : items){
			if(item instanceof Appliance){
				appliances.add((Appliance)item);
			}
		}
		return appliances;
	}
	
	
}
