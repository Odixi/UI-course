package model.items;

public class AudioDevice extends Appliance {

	private int volume;
	
	public AudioDevice(){
		super();
		
		//By default volume is 0.
		this.volume = 0;
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
		//if... //TODO Set upper limit for volume
		if(newVolume >= 0){
			this.volume = newVolume;
			return true;
		} else {
			return false;
		}
	}
	
}
