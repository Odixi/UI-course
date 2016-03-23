package view;

import java.rmi.RemoteException;
import java.util.Hashtable;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;

import server.SmartHSystem;
/**
 * TabSheet component for houses
 * @author Ville
 *
 */
public class HouseTabSheet extends CustomComponent{
	
	TabSheet tabsheet;
	Hashtable<String, String> rooms;
	Hashtable<String, Boolean> userView;
	
	public HouseTabSheet(SmartHSystem shsystem, SmartUI ui, String houseID, Hashtable<String,Boolean> userView){
		
		tabsheet = new TabSheet();
		this.userView = userView;
		
		try {
			rooms = shsystem.getRoomNames(houseID);
			for (String key : rooms.keySet()){
				if (userView.get(key)){
					tabsheet.addTab(new RoomContentComponent(shsystem, ui, key, houseID, userView), rooms.get(key));
				}
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
