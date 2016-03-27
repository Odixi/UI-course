package model.items;

import java.io.Serializable;

import model.house.House;
import model.house.Room;

public class SmartItem implements Serializable {

	private final String itemID;
	private String name;

	private House house;
	private Room room;
	
	//CONSTURCTOR
	public SmartItem(String itemID){
		this.itemID = itemID;
	}
	
	public SmartItem(String itemID, House house, Room room){
		this.itemID = itemID;
		this.house = house;
		this.room = room;
	}
	
	//------- ITEM ID -------------------------
	
	//getID
	public String getID(){
		return itemID;
	}
	
	//------- ITEM NAME -------------------------
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	//------- HOUSE & ROOM -------------------------
	
	public Room getRoom(){
		return room;
	}
	
	public void setRoom(Room room){
		this.room = room;
	}
	
	public House getHouse(){
		return house;
	}
	
	public void setHouse(House house){
		this.house = house;
	}
	
}
