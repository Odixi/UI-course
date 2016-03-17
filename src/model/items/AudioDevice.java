package model.items;

public class AudioDevice extends Appliance {

	private final String defaultname = "audio device";
	private int volume;
	
	private static final int MAX_VOLUME = 100;
	private static final int MIN_VOLUME = 0;
	
	public AudioDevice(String applianceID){
		super(applianceID);
		
		//By default volume is 0.
		this.volume = 0;
	}
	
	public String getID(){
		return super.getID();
	}
	
	public int getVolume(){
		return volume;
	}
	
	/**
	 * Change the audiodevices volume.
	 * @param newVolume
	 * @return boolean telling whether the volume could be change ( newVolume >= 0)
	 */
	public boolean setVolume(int newVolume){	
		if(newVolume >= 0 && newVolume <= 100){
			this.volume = newVolume;
			return true;
		} else {
			return false;
		}
	}
	

	public int getMaxVolume(){
		return MAX_VOLUME;
	}
	
	public int getMinVolume(){
		return MIN_VOLUME;
	}
		
	//--------- Name -------------------
	
	public void setName(String name){
		super.setName(name);
	}
	
	public String getName(){
		return super.getName();
	}
	
	public void setDefaultname(){
		super.setName(defaultname);
	}
	
}
