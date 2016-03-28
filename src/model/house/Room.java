package model.house;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import com.sun.corba.se.impl.io.TypeMismatchException;

import exceptions.IDMatchNotFoundException;
import exceptions.IDTypeMismatch;
import model.items.Appliance;
import model.items.AudioDevice;
import model.items.Controller;
import model.items.Light;
import model.items.Sensor;
import model.items.SmartItem;

public class Room implements Serializable {

	private String name;
	private final String roomID; 
	
	private House house;
	private Hashtable<String, SmartItem> items;
	
	//--------- CONSTUCTORS --------------------------
	
	public Room(String roomID){
		this.roomID = roomID;
		this.house = house;
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
	
	//---------- HOUSE WHERE ROOM IS LOCATED ------------

	public House getHouse(){
		return house;
	}
	
	public void setHouse(House house){
		this.house = house;
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
	
	//---------- GET AUDIO DEVICES ----------------
		public Hashtable<String, Appliance> getAudioDevices(){
			Hashtable<String, Appliance> audiodevices = new Hashtable<String, Appliance>();
			
			for(String itemID : audiodevices.keySet()){
				if(audiodevices.get(itemID) instanceof AudioDevice){
					audiodevices.put(itemID, (AudioDevice)audiodevices.get(itemID));
				}
			}
			return audiodevices;
		}
	
//-------------- GET LONE OBJECTS --------------

	/**
	 * 
	 * @param itemID
	 * @return
	 * @throws IDMatchNotFoundException
	 */
	public SmartItem getItem(String itemID) throws IDMatchNotFoundException{
		
		if(items.containsKey(itemID)){
			return items.get(itemID);
		} else {
			throw new IDMatchNotFoundException("SmartItem matching id " + itemID + " not found in the room " + getID());
		}
	}
	
	/** 
	 * @param itemID
	 * @return
	 * @throws IDTypeMismatch 
	 */
	public Light getLight(String itemID) throws IDTypeMismatch {
		
		if( items.get(itemID) instanceof Light){
			return (Light)items.get(itemID);
		} else {
			throw new IDTypeMismatch("Item " + itemID + " is not a light.");
		}
	}
	
	//------------- GET SENSOR BY ITEM ID -------------------
	public Sensor getSensor(String itemID) throws IDTypeMismatch{
		
		if( items.get(itemID) instanceof Sensor){
			return (Sensor)items.get(itemID);
		} else {
			throw new IDTypeMismatch("Item " + itemID + " is not a sensor.");
		}
	}
	
	//------------- GET CONTROLLER BY ITEM ID -------------------
	public Controller getController(String itemID) throws IDTypeMismatch{
		
		if( items.get(itemID) instanceof Controller){
			return (Controller)items.get(itemID);
		} else {
			throw new IDTypeMismatch("Item " + itemID + " is not a controller.");
		}
	}
	
	//------------- GET APPLIANCE BY ITEM ID -------------------
	public Appliance getAppliance(String itemID) throws IDTypeMismatch{
		
		if( items.get(itemID) instanceof Appliance){
			return (Appliance)items.get(itemID);
		} else {
			throw new IDTypeMismatch("Item " + itemID + " is not an appliance.");
		}
	}
	
	//------------- GET AUDIO DEVICE BY ITEM ID -------------------
	public AudioDevice getAudioDevice(String itemID) throws IDTypeMismatch {
		
		if( items.get(itemID) instanceof AudioDevice ){
			return (AudioDevice)items.get(itemID);
		} else {
			throw new IDTypeMismatch("Item " + itemID + " is not an audio device.");
		}	
	}
	
}
