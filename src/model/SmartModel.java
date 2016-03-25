package model;

import java.util.ArrayList;
import java.util.Hashtable;

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
	
	public ArrayList<House> getHouses(){
		return houses;
	}
	
	
	
	
	
	
	//<o><o><o><o><o><o><o> JUST FOR TESTING! <o><o><o><o><o><o><o>
	
	public static void printHouseStructure(ArrayList<House> houses){
		
		for(House house : houses){
			System.out.println("Housename: " + house.getName());
			System.out.println("HouseID: " + house.getID() + "\n");
			
			ArrayList<Room> rooms = house.getRooms();
			System.out.println("House has " + rooms.size() + " rooms:");
			
			for(Room room : rooms){
				System.out.println("Roomname: " + room.getName() + ", roomID: " + room.getID());

				ArrayList<SmartItem> items = room.getItems();
				
				System.out.println("items-list length: " + items.size());
				
				for(SmartItem item : items){
					System.out.println("Itemname: " + item.getName() + ", itemID: " + item.getID()); 
				}
			}
		}	
	}
	
}
