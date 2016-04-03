package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;

import exceptions.ElementNullException;
import server.SmartHSystem;
/**
 * TabSheet component for houses. Makes a tab for every room in corresponding house.
 * @author Ville
 *
 */
public class HouseTabSheet extends CustomComponent{
	
	private TabSheet tabsheet;
	private Hashtable<String, String> rooms;
	private Hashtable<String, Boolean> userView;
	private ArrayList<RoomContentComponent> componentList;
	
	/**
	 * 
	 * @param shsystem for RMI calls
	 * @param houseID
	 * @param userView
	 */
	public HouseTabSheet(SmartHSystem shsystem, String houseID, Hashtable<String,Boolean> userView){
		
		tabsheet = new TabSheet();
		this.userView = userView;
		componentList = new ArrayList<RoomContentComponent>();
		
		try {
			RoomContentComponent temp;
			rooms = shsystem.getRoomNames(houseID);

			for (String key : rooms.keySet()){
				if (userView.get(key)){
					temp = new RoomContentComponent(shsystem, key, houseID, userView);
					tabsheet.addTab(temp, rooms.get(key));
					componentList.add(temp);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			
		} catch (ElementNullException e) {
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
