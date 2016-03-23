package view;

import java.rmi.RemoteException;
import java.util.Hashtable;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

import server.SmartHSystem;

public class RoomContentComponent extends CustomComponent {
	
	VerticalLayout layout;
	Hashtable<String, String> items;
	
	public RoomContentComponent(SmartHSystem shsystem, SmartUI ui, String roomID, String houseID){
		
		layout = new VerticalLayout();
		
		try {
			items = shsystem.getItems(houseID, roomID);
			for (String key : items.keySet()){
				layout.addComponent(new ItemComponentAppliance(shsystem, key));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		setCompositionRoot(layout);
		
	}

}
