package model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import exceptions.ElementNullException;
import exceptions.IDMatchNotFoundException;
import exceptions.IDTypeMismatch;
import model.HouseObjectGenerator;
import model.house.House;
import model.house.Room;
import model.items.Sensor;
import model.items.SmartItem;

/**
 * SmartModel holds the information regarding the state of different items (sensors, appliances etc)
 * 
 * @author Pilvi
 *
 */

public class SmartModel {

	private HouseObjectGenerator generator = new HouseObjectGenerator();
	private Hashtable<String, House> houses;
	private boolean dataChanged; //Used by UIUpdater to see if there's a need for an update.
	
	
	//CONSTRUCTOR 
	
	public SmartModel() throws ElementNullException{
	
		HouseObjectGenerator generator = new HouseObjectGenerator();
		houses = generator.buildHouses(); 
		dataChanged = false;
		
	}

	
	// -<>-<>-<>-<>-<>-<>-<>-<>- GETTERS -<>-<>-<>-<>-<>-<>-<>-<>-<>-<>-
	
	/**
	 * Get list of houseIDs and House-objects.
	 * @return Hashtable<String houseID, House-object>
	 */
	public Hashtable<String, House> getHouses(){
		return houses;
	}
	
	/**
	 * 
	 * @param houseID
	 * @return
	 * @throws IDMatchNotFoundException
	 */
	public House getHouse(String houseID) throws IDMatchNotFoundException{
		if(houses.containsKey(houseID)){
			return houses.get(houseID);
		} else {
			throw new IDMatchNotFoundException("House matching id " + houseID + " not found.");
		}
	}
	
	//TODO These two need synchronization
	/**
	 * 
	 * @param dataChanged
	 */
	public void setDataChanged(boolean dataChanged) {
		this.dataChanged = dataChanged;
	}

	/**
	 * 
	 * @return dataChanged
	 */
	public boolean isDataChanged() {
		return dataChanged;
	}
	
	//<o><o><o><o><o><o><o> JUST FOR TESTING! <o><o><o><o><o><o><o>

	public static void printHouseStructure(Hashtable<String, House> houses){
		
		for( String houseID : houses.keySet() ){
			System.out.println("Housename: " + houses.get(houseID).getName());
			System.out.println("HouseID: " + houses.get(houseID).getID() + "\n");
			
			Hashtable<String, Room> rooms = houses.get(houseID).getRooms();
			System.out.println("House has " + rooms.size() + " rooms:");
			
			for(String roomID : rooms.keySet()){
				System.out.println("Roomname: " + rooms.get(roomID).getName() + ", roomID: " + roomID);

				Hashtable<String, SmartItem> items = rooms.get(roomID).getItems();
				
				System.out.println("items-list length: " + items.size());
				
				for(String itemID : items.keySet()){
					System.out.println("Itemname: " + items.get(itemID).getName() + ", itemID: " + itemID); 
					
					if(items.get(itemID) instanceof Sensor){
						System.out.println("Item is sensor, sensor type: " + ((Sensor)items.get(itemID)).getSensorType().toString() );
					}
				}
			}
		}	
	}
	
	// x.x.x.x.x.x.x.x.x.x.x.x.x.x.x GET SMART ITEM  x.x.x.x.x.x.x.x.x.x.x.x.x.x.x 
	/**
	 * Retrive the SmartItem object matching the given itemID.
	 * 
	 * @param itemID The ID of the asked SmartItem.
	 * @return SmartItem-object
	 * @throws IDMatchNotFoundException
	 */
	public SmartItem getSmartItem(String itemID) throws IDMatchNotFoundException{
		
		Hashtable<String, SmartItem> allItems = getAllItems();
		
		if(allItems.contains(itemID)){
			return allItems.get(itemID);
		} else {
			throw new IDMatchNotFoundException("SmartItem matching id " + itemID + " not found.");
		}
		
	}
	
	/**
	 * Retrieve the SmartItem object found in the given house and room and matching the given itemID.
	 * 
	 * @param houseID The ID of the house where the SmartItem is located.
	 * @param roomID The ID of the room where the SmartItem is located.
	 * @param itemID The ID of the asked SmartItem.
	 * @return SmartItem-object
	 * @throws IDMatchNotFoundException 
	 */
	public SmartItem getSmartItem(String houseID, String roomID, String itemID) throws IDMatchNotFoundException{
	
		return getHouse(houseID).getRoom(roomID).getItem(itemID);
	}
	
	//<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x> LIGHTS <x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>
	
	/**
	 * 
	 * @param houseID The ID of the house where the light is located.
	 * @param roomID The ID of the room where the light is located.
	 * @param itemID The ID of the asked light.
	 * @throws IDMatchNotFoundException
	 * @throws IDTypeMismatch
	 */
	public void turnLightOn(String houseID, String roomID, String itemID) throws IDMatchNotFoundException, IDTypeMismatch {
		getHouse(houseID).getRoom(roomID).getLight(itemID).turnON();
		this.setDataChanged(true);
	}

	/**
	 * 
	 * @param houseID The ID of the house where the light is located.
	 * @param roomID The ID of the room where the light is located.
	 * @param itemID The ID of the asked light.
	 * @throws IDMatchNotFoundException
	 * @throws IDTypeMismatch
	 */
	public void turnLightOff(String houseID, String roomID, String itemID) throws IDMatchNotFoundException, IDTypeMismatch {
		getHouse(houseID).getRoom(roomID).getLight(itemID).turnOFF();
		this.setDataChanged(true);
	}
	
	//<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x> SENSORS <x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>
	
	//<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x> CONTROLLERS <x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>
	
	/**
	 * 
	 * @param houseID The ID of the house where the controller is located.
	 * @param roomID The ID of the room where the controller is located.
	 * @param itemID The ID of the asked controller.
	 * @param newValue New value for the controller.
	 * @return True if the value was valid and could therefore be changed. Else false.
	 * @throws IDTypeMismatch
	 * @throws IDMatchNotFoundException
	 */
	public boolean setControllerValue(String houseID, String roomID, String itemID, double newValue) throws IDTypeMismatch, IDMatchNotFoundException{
		if(getHouse(houseID).getRoom(roomID).getController(itemID).setValue(newValue)){
			this.setDataChanged(true);
			return true;
		}
		else{
			return false;
		}
	}
	
	//<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x> APPLIANCES <x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>
	
	/**
	 * Turn on the appliance.
	 * @param houseID The ID of the house where the appliance is located.
	 * @param roomID The ID of the room where the appliane is located.
	 * @param itemID The ID of the asked appliance.
	 * @throws IDMatchNotFoundException
	 * @throws IDTypeMismatch
	 */
	public void turnApplianceOn(String houseID, String roomID, String itemID) throws IDMatchNotFoundException, IDTypeMismatch {
		getHouse(houseID).getRoom(roomID).getAppliance(itemID).turnON();
		this.setDataChanged(true);
	}

	/**
	 * Turn off the appliance.
	 * @param houseID The ID of the house where the appliance is located.
	 * @param roomID The ID of the room where the appliance is located.
	 * @param itemID The ID of the asked appliance.
	 * @throws IDMatchNotFoundException
	 * @throws IDTypeMismatch
	 */
	public void turnApplianceOff(String houseID, String roomID, String itemID) throws IDMatchNotFoundException, IDTypeMismatch {
		getHouse(houseID).getRoom(roomID).getAppliance(itemID).turnOFF();
		this.setDataChanged(true);
	}

	/**
	 * Set new volume value for an audio device.
	 * @param houseID The ID of the house where the audio device is located.
	 * @param roomID The ID of the room where the audio device is located.
	 * @param itemID The ID of the asked audio device.
	 * @param newVolume The new volume value for the audio device.
	 * @return Returns true if the given volume value was valid and volume could therefore be changed.
	 * @throws IDMatchNotFoundException
	 * @throws IDTypeMismatch
	 */
	public boolean setAudioVolume(String houseID, String roomID, String itemID, int newVolume) throws IDMatchNotFoundException, IDTypeMismatch {
		if(getHouse(houseID).getRoom(roomID).getAudioDevice(itemID).setVolume(newVolume)){
			this.setDataChanged(true);
			return true;
		}
		else{
			return false;
		}
	}
	
// o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o HELP METHODS o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o
	
	/** 
	 * Returns all the items from the entire house system in one huge Hashtable.
	 * @return Hashtable<String, SmartItem> containing all items from all the houses.
	 */
	public Hashtable<String, SmartItem> getAllItems(){
		
		Hashtable<String, SmartItem> allItems = new Hashtable<String, SmartItem>();
		
		for(String houseID : houses.keySet()){
			Hashtable<String, Room> rooms = houses.get(houseID).getRooms();
			
			for(String roomID : rooms.keySet()){
				allItems.putAll(rooms.get(roomID).getItems());
			}
		}
		
		return allItems;
	}
	
}
