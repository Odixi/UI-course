package model.items;

public class Light extends SmartItem {
	
	private String lightname;
	private boolean on;
	
	public Light(String lightID){
		super(lightID);
		//Light is off by default. Saving the environment you know.
		this.on = false;
	}
	
	//----- Turns lights on and off -------

	public void turnON(){
		this.on = true;}
	
	public void turnOFF(){
		this.on = false;}

	//
	public String getID(){
		return super.getID();
	}
	
	//--------- Light name ------------
	
	public void setName(String lightname){
		this.lightname = lightname;
	}
	
	public String getName(){
		return lightname;
	}
	
}
