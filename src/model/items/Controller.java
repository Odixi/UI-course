package model.items;

public class Controller extends SmartItem {

	private ControllerType controllerType; //TODO Or should SensorType be used?
	private SensorUnit unit;
	private double value;

	private double maxValue;
	private double minValue;
	
	//----- CONSTURCTORS -------------------
	
	public Controller(String controllerID){
		super(controllerID);
	}
	
	public Controller(String controllerID, ControllerType type){
		super(controllerID);
		setControllerType(type);
	}
	
	public Controller(String controllerID, String controllerName, ControllerType type){
		super(controllerID);
		setControllerName(controllerName);
		setControllerType(type);
	}
	
	
	//----------- CONTROLLER NAME ----------------
	
	public void setDefaultName(){
		switch(controllerType) {
			case TEMPERATURE:
				super.setName("Temperature controller");
		}
	}
	
	public String getSensorName(){
		return super.getName();
	}
	
	// >>>> IMPORTANT: Please do not call this method straight from the client. It fill fuck things up as changes are not made to XML.
	public void setControllerName(String newControllerName){
		super.setName(newControllerName);
	}
	
	//---------------- SENSOR TYPE ----------------------
	
	public ControllerType getControllerType(){
		return controllerType;
	}
	
	public void setControllerType(ControllerType type){
		this.controllerType = type;
		
		switch(type){
			case TEMPERATURE:
				unit = SensorUnit.CELCIUS;	
				//TODO Min & max value?
				//minValue = 
				//maxValue = 
		}
		
	}
	
	//-------------- CONTROLLER MEASURING UNIT -------------
	
	public SensorUnit getControllerUnit(){
		return unit;
	}
	
	//-------------- CONTROLLER VALUE ? ----------------------
	
	public double getControllerValue(){
		return value;
	}
	
	public boolean setValue(double newValue){
		//if... //TODO
		
		this.value = newValue;
		
		return true; //TODO
	}
	
}
