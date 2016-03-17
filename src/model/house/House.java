package model.house;

import java.util.ArrayList;

public class House {

	private ArrayList<Room> rooms;
	private int numberOfRooms; // Does the number of rooms need to change?
	
	
	public House(int numberOfRooms){
		//numberOfRooms > 0 	
		this.numberOfRooms = numberOfRooms;
		
	}
	
	public House(){
		
	}
	
	
	public void setRooms(ArrayList<Room> rooms){
		this.rooms = rooms;
	}
	
	public ArrayList<Room> getRooms(){
		return rooms;
	}
	
	
	
}
