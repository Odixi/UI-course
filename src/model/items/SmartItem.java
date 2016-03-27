package model.items;

import java.io.Serializable;

public class SmartItem implements Serializable {

	private final String itemID;
	private String name;
	
	//CONSTURCTOR
	public SmartItem(String itemID){
		this.itemID = itemID;
	}
	
	//getID
	public String getID(){
		return itemID;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
}
