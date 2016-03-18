package model.house;

import java.util.ArrayList;

public class House {

	private String name;
	private final String houseID;
	private ArrayList<Room> rooms;

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
	
	public void setRooms(ArrayList<Room> rooms){
		this.rooms = rooms;
	}
	
	public ArrayList<Room> getRooms(){
		return rooms;
	}
	
	
	
}
