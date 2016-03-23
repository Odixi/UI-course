package view;

import java.rmi.RemoteException;
import java.util.Hashtable;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

import server.SmartHSystem;

/**
 * A component which holds the rooms item components
 * @author Ville
 *
 */
public class RoomContentComponent extends CustomComponent {
	
	VerticalLayout layout;
	Hashtable<String, String> items;
	Hashtable<String, Boolean> userView;
	
	public RoomContentComponent(SmartHSystem shsystem, SmartUI ui, String roomID, String houseID, Hashtable<String, Boolean> userView){
		
		layout = new VerticalLayout();
		this.userView = userView;
		
		try {
			items = shsystem.getItems(houseID, roomID);
			for (String key : items.keySet()){
				if (userView.get(key)){
					layout.addComponent(new ItemComponentAppliance(shsystem, key));
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		setCompositionRoot(layout);
		
	}
	/**
	 * Update item states
	 */
	public void update(){
		ItemComponent c;
		for (int i = 0; i < layout.getComponentCount(); i++){
			if (layout.getComponent(i) instanceof ItemComponent){
				c = (ItemComponent)layout.getComponent(i);
				c.update();
			}
				
		}
		
	}

}
