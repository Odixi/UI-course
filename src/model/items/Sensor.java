package model.items;

import java.io.Serializable;

public class Sensor extends SmartItem implements Serializable {

	private SensorType sensorType;
	private SensorUnit sensorUnit;
	private double sensorValue;
	
	private double maxValue;	//This have to be defined for different units.
	private double minValue;
	
	//I'm actually using this constructor in HouseObject Generator
	public Sensor(String sensorID){
		super(sensorID);
	}
	
	//These two other constructors are pretty much useless.
	
	public Sensor(String sensorID, SensorType type){
		super(sensorID);
		setSensorType(type);
	} //constructor
	
	public Sensor(String sensorID, String sensorName, SensorType type){
		super(sensorID);
		setName(sensorName);	
		setSensorType(type);
	} //constructor

	public String getID(){
		return super.getID();
	}

	//--------- SENSOR NAME ----------------

	public void setDefaultName(){
		switch(sensorType) {
			case TEMPERATURE:
				setName("Temperature sensor");
			case HUMIDITY:
				setName("Humidity sensor");;
			case LIGHT:
				setName("Light sensor");
			}
	}

	//--------- SENSOR TYPE ----------------

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorType type){
		this.sensorType = type;
		
		switch (sensorType) {
				
			case TEMPERATURE:
				sensorUnit = SensorUnit.CELCIUS;
				maxValue = 150;
				minValue = -50;
			case HUMIDITY:
				sensorUnit = SensorUnit.HUMIDITYPERCENT;
				maxValue = 100;
				minValue = 0;
				
			case LIGHT:
				sensorUnit = SensorUnit.LUMEN;
				maxValue = 20000;
				minValue = 0;
		}	
	}
	
	//--------- SENSOR MEASURING UNIT ----------------
	
	public SensorUnit getSensorUnit() {
		return sensorUnit;
	}

	//----------------- SENSOR VALUE --------------------------------
	
	public double getSensorValue(){
		return sensorValue;
	}

	/**
	 * Returns true if the value was between set min & max (for the measuring unit) and value was therefore set successfully.
	 * @param newValue
	 * @return
	 */
	public boolean setValue(double newValue){
		if(newValue <= maxValue && newValue >= minValue){
			sensorValue = newValue;
			return true;
		} else {
			return false;
		}
	}
	
}
