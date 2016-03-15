package model.items;

public class Sensor extends SmartItem {

	private SensorType sensorType;
	private SensorUnit sensorUnit;
	private double sensorValue;
	
	private double maxValue;	//This have to be defined for different units.
	private double minValue;
	
	public Sensor(SensorType type){
		this.sensorType = type;
		
		switch (sensorType) {
			case TEMPERATURE:
				sensorUnit = SensorUnit.CELCIUS;
				//Set maxValue & minValue
			case HUMIDITY:
				sensorUnit = SensorUnit.HUMIDITYPERCENT;
			case LIGHT:
				sensorUnit = SensorUnit.LUMEN;
		}	
		
		//Set default values;
		
	} //constructor

	
	//--------- SENSOR TYPE & MEASURING UNIT ----------------
	
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
