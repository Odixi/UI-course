package model.items;

public class Light extends SmartItem {
	
	private boolean on;
	
	public Light(){
		//Light is off by default. Saving the environment you know.
		this.on = false;
	}
	
	//----- Turns lights on and off -------
	
	public void turnON(){
		this.on = true;}
	
	public void turnOFF(){
		this.on = false;}

	//
	
}
