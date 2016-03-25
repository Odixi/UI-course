package model;

import java.util.ArrayList;
import java.util.Hashtable;

import exceptions.IDMatchNotFoundException;
import model.HouseObjectGenerator;
import model.house.House;
import model.house.Room;
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
	
	public SmartModel(){
		
		HouseObjectGenerator generator = new HouseObjectGenerator();
		houses = generator.buildHouses(); 
		
	}
	
	//MAIN: FOR TESTING
	public static void main(String args[]){
	
		//TODO Remove: For testing
		//printHouseStructure();
		
	}
	
	// -<>-<>-<>-<>-<>-<>-<>-<>- GETTERS -<>-<>-<>-<>-<>-<>-<>-<>-<>-<>-
	
	/**
	 * 
	 * @return
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
	
	//<o><o><o><o><o><o><o> JUST FOR TESTING! <o><o><o><o><o><o><o>
	
	public static void printHouseStructure(ArrayList<House> houses){
		
		for(House house : houses){
			System.out.println("Housename: " + house.getName());
			System.out.println("HouseID: " + house.getID() + "\n");
			
			Hashtable<String, Room> rooms = house.getRooms();
			System.out.println("House has " + rooms.size() + " rooms:");
			
			for(String roomID : rooms.keySet()){
				System.out.println("Roomname: " + rooms.get(roomID).getName() + ", roomID: " + roomID);

				Hashtable<String, SmartItem> items = rooms.get(roomID).getItems();
				
				System.out.println("items-list length: " + items.size());
				
				for(String itemID : items.keySet()){
					System.out.println("Itemname: " + items.get(itemID).getName() + ", itemID: " + itemID); 
				}
			}
		}	
	}
	
	// x.x.x.x.x.x.x.x.x.x.x.x.x.x.x GET SMART ITEM  x.x.x.x.x.x.x.x.x.x.x.x.x.x.x 
	/**
	 * 
	 * @param itemID
	 * @return
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
	 * 
	 * @param houseID
	 * @param roomID
	 * @param itemID
	 * @return
	 * @throws IDMatchNotFoundException 
	 */
	public SmartItem getSmartItem(String houseID, String roomID, String itemID) throws IDMatchNotFoundException{
		
		return getHouse(houseID).getRoom(roomID).getItem(itemID);
	}
	
	//<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x> LIGHTS <x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>
	
	public void turnLightOn(String itemID){
		
	}
	
	public void turnLightOff(String itemID){
		
		
		
	}
	
	//<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x> SENSORS <x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>
	
	//<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x> CONTROLLERS <x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>
	
	//<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x> APPLIANCES <x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>-<x>
	
	
// o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o HELP METHODS o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o
	
	/** Returns all the items from the entire house system in one huge Hashtable.
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
