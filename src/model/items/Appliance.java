package model.items;

import java.io.Serializable;

public class Appliance extends SmartItem implements Serializable {

	private String defaultname = "Nameless appliance";
	private boolean on;
	
	//CONSTRUCTOR
	public Appliance(String applianceID){
		super(applianceID);
		this.on = false;
		
	} //constructor

	public String getID(){
		return super.getID();
	}
	
	//------- ON & OFF ---------
	
	public void turnON(){
		this.on = true;
	}
	
	public void turnOFF(){
		this.on = false;
	}
	
	public boolean isON(){
		return on;
	}
	
	//--------- Name --------
	
	public void setDefaultname(){
		super.setName(defaultname);
	}
	
	public void setName(String name){
		super.setName(name);
	}
	
	public String getName(){
		return super.getName();
	}
	
}
