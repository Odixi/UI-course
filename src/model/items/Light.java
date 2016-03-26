package model.items;

public class Light extends SmartItem {
	
	private final String defaultname = "Light";
	private boolean on;
	
	public Light(String lightID){
		super(lightID);
		setName(defaultname);
		//Light is off by default. Saving the environment you know.
		this.on = false;
	}
	
	public Light(String lightID, String lightname){
		super(lightID);
		setName(lightname);
		//Light is off by default. Saving the environment you know.
		this.on = false;
	}
	
	//----- Turns lights on and off -------

	public void turnON(){
		this.on = true;}
	
	public void turnOFF(){
		this.on = false;}

	//
	
	public boolean isON(){
		return on;
	}
	
	//
	public String getID(){
		return super.getID();
	}
	
	//--------- Light name ------------
	
	public void setName(String lightname){
		super.setName(lightname);
	}
	
	public String getName(){
		return super.getName();
	}
	
}
