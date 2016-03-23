package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
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
	ArrayList<RoomContentComponent> componentList;
	
	public HouseTabSheet(SmartHSystem shsystem, SmartUI ui, String houseID, Hashtable<String,Boolean> userView){
		
		tabsheet = new TabSheet();
		this.userView = userView;
		
		try {
			RoomContentComponent temp;
			rooms = shsystem.getRoomNames(houseID);
			for (String key : rooms.keySet()){
				if (userView.get(key)){
					temp = new RoomContentComponent(shsystem, ui, key, houseID, userView);
					tabsheet.addTab(temp, rooms.get(key));
					componentList.add(temp);
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
		for (RoomContentComponent comp : componentList){
			comp.update();
		}
	}
}
