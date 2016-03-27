package model.items;

public enum SensorUnit {
	CELCIUS("°C"),
	HUMIDITYPERCENT("%"),
	LUMEN("lux"); // Or rather lumen / m^3 (Illumination)
	
	/*
	 *  Just a quick introduction for illumination levels:
	 *  
	 *   Outdoors:
	 *   	Overcast Night : 0.0001 lux
	 *   	Fullmoon : 0.1 lux
	 *   	Twilight: 10 lux
	 *   	Very dark day: 100 lux
	 *   	Overcast day: 1000 lux
	 *   	Full daylight: 10 000 lux
	 *   	Sunlight 100 000 lux
	 *   
	 *   Indoors:
	 *   	Relatively dark indoor areas: 20 - 150 lux
	 *   	Quite normal indoor areas: 150 - 750 lux
	 *   	When doing detailed work: 750 - 20 000 lux
	 *   
	 *   For more information http://www.engineeringtoolbox.com/light-level-rooms-d_708.html
	 *   
	 *   I'm not really sure how should I manage the light level control yet (Because it's clearly not linear)
	 *   Maybe I shouldn't think about it too much.
	 *   
	 */
	
	
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
