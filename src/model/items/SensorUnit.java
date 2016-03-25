package model.items;

public enum SensorUnit {
	CELCIUS("°C"),
	HUMIDITYPERCENT("%"),
	LUMEN("lm");
	
	private String unit;
	
	private SensorUnit(String unit){
		this.unit = unit;
	}
	
	public String getUnit(){
		return unit;
	}
	
	//Come up with more if you want to.
	
	// Added units -Ville
	
}
