package model.items;

public class Appliance extends SmartItem {

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
	
}
