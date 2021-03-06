package view;

import java.rmi.RemoteException;
import java.util.Hashtable;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import exceptions.ElementNullException;
import exceptions.IDMatchNotFoundException;
import model.items.Appliance;
import model.items.AudioDevice;
import model.items.Controller;
import model.items.Light;
import model.items.Sensor;
import model.items.SmartItem;
import server.SmartHSystem;

/**
 * A custom component which holds the rooms item components
 * @author Ville
 *
 */
public class RoomContentComponent extends CustomComponent {
	
	private VerticalLayout layout;
	private Hashtable<String, String> items;
	private Hashtable<String, Boolean> userView;
	
	public RoomContentComponent(SmartHSystem shsystem, String roomID, String houseID, Hashtable<String, Boolean> userView){
		
		layout = new VerticalLayout();
		this.userView = userView;
		
		try {
			
			items = shsystem.getItems(houseID, roomID);
			for (String key : items.keySet()){
				if (userView.get(key)){
					
					SmartItem si = shsystem.getSmartItem(houseID, roomID, key);
					if (si instanceof Sensor){
						layout.addComponent(new ItemComponentSensor(shsystem, (Sensor)si));
					}
					else if (si instanceof AudioDevice){
						layout.addComponent(new ItemComponentAudioDevice(shsystem, (AudioDevice)si));
					}
					else if (si instanceof Appliance){
						layout.addComponent(new ItemComponentAppliance(shsystem, (Appliance)si));
					}
					else if (si instanceof Light){
						layout.addComponent(new ItemComponentLight(shsystem, (Light)si));
					}
					else if (si instanceof Controller){
						layout.addComponent(new ItemComponentController(shsystem, (Controller)si));
					}
				}
				
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IDMatchNotFoundException e) {
			layout.addComponent(new Label("Could not get item from server. Something bad happend :("));
			e.printStackTrace();
		} catch (ElementNullException e) {
			layout.addComponent(new Label("Could not get item from server. Something bad happend :("));
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
