package model.items;

public class Controller extends SmartItem {

	private ControllerType controllerType; //TODO Or should SensorType be used? Propably -Ville
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
			case HUMIDITY:
				super.setName("Humidity controller");
			case LIGHT:
				super.setName("Light controller");
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
				maxValue = 150;
				minValue = -50;
			case HUMIDITY:
				unit = SensorUnit.HUMIDITYPERCENT;
				maxValue = 100;
				minValue = 0;
			case LIGHT:
				unit = SensorUnit.LUMEN;
				maxValue = 20000;
				minValue = 0;
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
	
	/**
	 * 
	 * @param newValue
	 * @return
	 */
	public boolean setValue(double newValue){

		if(newValue <= maxValue && newValue >= minValue){
			this.value = newValue;
			return true;
		} else {
			return false;
		}
	}
	
}
