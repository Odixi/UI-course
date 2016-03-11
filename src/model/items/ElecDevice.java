package model.items;

public class ElecDevice extends SmartItem {

	private boolean on;
	
	//CONSTRUCTOR
	public ElecDevice(){
		
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
	
}
