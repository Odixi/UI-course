package view;

import java.rmi.RemoteException;
import java.util.Hashtable;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;

import server.SmartHSystem;

public class HouseTabSheet extends CustomComponent{
	
	TabSheet tabsheet;
	Hashtable<String, String> rooms;
	
	public HouseTabSheet(SmartHSystem shsystem, SmartUI ui, String houseID){
		
		tabsheet = new TabSheet();
		
		try {
			rooms = shsystem.getRoomNames(houseID);
			for (String key : rooms.keySet()){
				tabsheet.addTab(new RoomContentComponent(shsystem, ui, key, houseID), rooms.get(key));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		setCompositionRoot(tabsheet);

	}
	/**
	 * Call update methods
	 */
	public void update(){
		
	}
}
