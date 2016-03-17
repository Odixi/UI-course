package model.house;

import java.util.ArrayList;

import model.items.Appliance;
import model.items.Light;
import model.items.Sensor;
import model.items.SmartItem;

public class Room {

	private ArrayList<SmartItem> items;
	
	public Room(){
		
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
