package model;

import java.util.LinkedList;

/**
 * Singleton that broadcasts updates to all registered listeners(UIs that need regular updating). 
 * @author elmo
 * 
 */
public class UIUpdater {

	private static final UIUpdater updater = new UIUpdater(); //Singleton
	private static LinkedList<UpdateListener> listeners;
	
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
	 * Sends a DataPack containing update data to all registered listeners.
	 */
	public void broadcastUpdate(){
		//TODO Find out if any item data has been changed,
		//wrap it into a new DataPack, 
		//and send it to all registered listeners
	}
	
	
//******************************--- LISTENER INTERFACE ---**********************************************************
	
	/**
	 * Interface for listening update broadcasts. 
	 * All UIs that need updates from UI sessions should implement this. 
	 * @author elmo
	 */
	public interface UpdateListener{
		
		/**
		 * Use this when registering to broadcasting list.
		 * UI's should implement this inside init() method.
		 */
		public void register();
		
		/**
		 * Use this to unregister from broadcasting list.
		 * UI's should implement this inside detach() method.
		 */
		public void unregister();
		
		/**
		 * Unpack the data elements into corresponding components.
		 * This must happen through access() method. 
		 * @param data Package containing state data from the server (sensors, lights etc.)
		 */
		public void receiveUpdate(DataPack data);
	}
}
