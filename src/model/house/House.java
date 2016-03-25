package model.house;

import java.util.ArrayList;
import java.util.Hashtable;

public class House {

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
	
	public void setRooms(Hashtable<String, Room> rooms){
		this.rooms = rooms;
	}
	
	public Hashtable<String, Room> getRooms(){
		return rooms;
	}
	
	
	
}
