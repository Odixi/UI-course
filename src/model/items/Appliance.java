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

}
