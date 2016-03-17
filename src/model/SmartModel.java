package model;

import java.util.ArrayList;

import model.HouseObjectGenerator;
import model.house.House;
import model.house.Room;
/*
 * Logiikan käsittely
 */
public class SmartModel {

	/*
	public SmartModel(){
		
	}*/
	
	public static void main(String args[]){
		
		HouseObjectGenerator generator = new HouseObjectGenerator();
		
		ArrayList<House> houses = generator.buildHouses(); 
		
		printHouseStructure(houses);
		
	}
	
	//JUST FOR TESTING!S
	public static void printHouseStructure(ArrayList<House> houses){
		
		for(House house : houses){
			System.out.println("Housename: " + house.getName());
			System.out.println("HouseID: " + house.getID() + "\n");
			
			ArrayList<Room> rooms = house.getRooms();
			System.out.println("House has " + rooms.size() + " rooms:");
			
			for(Room room : rooms){
				System.out.println("Roomname: " + room.getName() + ", roomID: " + room.getID());

			}
		}
		
	}
	
}
