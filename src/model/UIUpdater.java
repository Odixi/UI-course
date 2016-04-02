package model;

import java.util.LinkedList;
import server.SmartHSystemImp;

/**
 * Singleton that broadcasts updates to all registered listeners(UIs that need regular updating). 
 * @author elmo
 * 
 */
public class UIUpdater {

	private static final UIUpdater updater = new UIUpdater(); //Singleton
	private static LinkedList<UpdateListener> listeners;
	private SmartHSystemImp system; //System from which the data is collected.
	
	/**
	 * Returns the UIUpdater instance.
	 * @return UIUpdater instance
	 */
	public static UIUpdater getUpdater() {
		return updater;
	}


	//Singleton constructor
	private UIUpdater(){
		listeners = new LinkedList<UpdateListener>();
	}
	
	/**
	 * 
	 * @param system
	 */
	public void setSystem(SmartHSystemImp system) {
		this.system = system;
	}
	
//**********************************--- METHODS ---****************************************************************************

	/**
	 * Adds the listener to broadcasting list making it able to receive updates. 
	 * @param listener Listener that is registered.
	 */
	public void register(UpdateListener listener){
		listeners.add(listener);
	}
	
	
	/**
	 * Removes the listener from broadcasting list making it unable to receive updates. 
	 * @param listener Listener that is unregistered.
	 */
	public void unregister(UpdateListener listener){
		listeners.remove(listener);
	}
	
	
	/**
	 * Tells all registered listeners to update themselves.
	 * TODO Updates should happen only if data has been changed.
	 */
	public void broadcastUpdate(){
		for(UpdateListener listener : listeners){
			listener.receiveUpdate();
		}
	}
	
	
//******************************--- LISTENER INTERFACE ---**********************************************************
	
	/**
	 * Interface for listening update broadcasts. 
	 * All UIs that need updates from other UI sessions should implement this. 
	 * @author elmo
	 */
	public interface UpdateListener{
		
		/**
		 * Used when registering to broadcasting list.
		 * UI's should implement this inside init() method.
		 */
		public void register();
		
		/**
		 * Used when unregistering from broadcasting list.
		 * UI's should implement this inside detach() method.
		 */
		public void unregister();
		
		/**
		 * Called by updater on regular intervals to inform the UI to update itself.
		 * This should happen through access() method.
		 */
		public void receiveUpdate();
	}
}
