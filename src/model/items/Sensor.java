package model.items;

public class Sensor extends SmartItem {

	private String sensorName;
	private SensorType sensorType;
	private SensorUnit sensorUnit;
	private double sensorValue;
	
	private double maxValue;	//This have to be defined for different units.
	private double minValue;
	
	public Sensor(String sensorName, SensorType type){
		this.sensorName = sensorName;
		this.sensorType = type;
		
		switch (sensorType) {
				//Set default values;
				//TODO
			case TEMPERATURE:
				sensorUnit = SensorUnit.CELCIUS;
				//Set maxValue & minValue
			case HUMIDITY:
				sensorUnit = SensorUnit.HUMIDITYPERCENT;
			case LIGHT:
				sensorUnit = SensorUnit.LUMEN;
		}	
		
		
		
	} //constructor

	
	//--------- SENSOR NAME, TYPE & MEASURING UNIT ----------------
	
	public String getSensorName(){
		return sensorName;
	}
	
	// >>>> IMPORTANT: Please do not call this method straight from the client. It fill fuck things up as changes are not made to XML.
	public void setSensorName(String newSensorName){
		sensorName = newSensorName;
	}
	
	public SensorType getSensorType() {
		return sensorType;
	}

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
