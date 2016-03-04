package model.items;

public class Light extends SmartItem {
	
	private boolean on;
	
	public Light(){
		
	}
	
	//----- Turns lights on and off -------
	
	public void turnON(){
		this.on = true;}
	
	public void turnOFF(){
		this.on = false;}

	//
	
}
