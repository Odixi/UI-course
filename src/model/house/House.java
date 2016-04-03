package model.house;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import exceptions.IDMatchNotFoundException;

/**
 * Object describing the smart house.
 * 
 * @author Pilvi
 *
 */

public class House implements Serializable {

	private String name;
	private final String houseID;
	private Hashtable<String, Room> rooms;

	//--------- CONSTRUCTORS -------------
	
	public House(String houseID){
		this.houseID = houseID;
	}
	
	public House(String houseID, String name){
		this.houseID = houseID;
		this.name = name;
	}
	
	//----------- NAME & ID---------------
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public String getID(){
		return houseID;
	}
	
	//------- ROOMS ------------------
	/**
	 * Set the rooms in the house
	 * @param rooms Hashtable<String roomID, Room-object>
	 */
	public void setRooms(Hashtable<String, Room> rooms){
		this.rooms = rooms;
	}
	
	/**
	 * Get the rooms in the house (roomIDs and Room-objects).
	 * @return
	 */
	public Hashtable<String, Room> getRooms(){
		return rooms;
	}
	
	/**
	 * Get the room matching the roomID
	 * @param roomID The ID of the searched room.
	 * @return Room-object.
	 * @throws IDMatchNotFoundException Thrown if room matching the roomID-parameter can't be found in the house.
	 */
	public Room getRoom(String roomID) throws IDMatchNotFoundException{
		
		if(rooms.containsKey(roomID)){
			return rooms.get(roomID);
		} else {
			throw new IDMatchNotFoundException("Room matching id " + roomID + "in house " + getID() + " not found.");
		}
	}
	
}
