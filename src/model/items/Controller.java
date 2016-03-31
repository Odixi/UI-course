package model.items;

import java.io.Serializable;

public class Controller extends SmartItem implements Serializable {

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
		setName(controllerName);
		setControllerType(type);
	}
	
	
	//----------- CONTROLLER NAME ----------------
	
	public void setDefaultName(){
		switch(controllerType) {
			case TEMPERATURE:
				super.setName("Temperature controller");
				break;
				
			case HUMIDITY:
				super.setName("Humidity controller");
				break;
				
			case LIGHT:
				super.setName("Light controller");
				break;
		}
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
				break;
				
			case HUMIDITY:
				unit = SensorUnit.HUMIDITYPERCENT;
				maxValue = 100;
				minValue = 0;
				break;
				
			case LIGHT:
				unit = SensorUnit.LUMEN;
				maxValue = 20000;
				minValue = 0;
				break;
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
