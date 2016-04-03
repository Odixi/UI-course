package view;

import java.rmi.RemoteException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import exceptions.IDMatchNotFoundException;
import exceptions.IDTypeMismatch;
import model.items.Light;
import server.SmartHSystem;

/**
 * Custom component for light type items.
 * Displays name and checkboxto turn the ligh on or off
 * @author Ville
 *
 */
public class ItemComponentLight extends CustomComponent implements ItemComponent{
	
	private SmartHSystem shsystem;
	private Light light;
	
	private Panel panel;
	private VerticalLayout layout;
	private Label name;
	private CheckBox on;
	
	/**
	 * 
	 * @param shsystem
	 * @param light
	 */
	public ItemComponentLight(SmartHSystem shsystem, Light l){
		
		this.light = l;
		this.shsystem = shsystem;
		
		panel = new Panel();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		panel.setContent(layout);
		
		name = new Label(light.getName());
		on = new CheckBox("On / Off");
		on.setValue(light.isON());
		
		on.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// When turning lights on / off...
				if (on.getValue()){
					try {
						shsystem.turnLightOn(light.getHouse().getID(), light.getRoom().getID(), light.getID());
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (IDMatchNotFoundException e) {
						on.setCaption("Could not get data from server!");
						e.printStackTrace();
					} catch (IDTypeMismatch e) {
						on.setCaption("Could not get data from server!");
						e.printStackTrace();
					}
				}
				else {
					try {
						shsystem.turnLightOff(light.getHouse().getID(), light.getRoom().getID(), light.getID());
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (IDTypeMismatch e) {
						on.setCaption("Could not get data from server!");
						e.printStackTrace();
					} catch (IDMatchNotFoundException e) {
						on.setCaption("Could not get data from server!");
						e.printStackTrace();
					}
				}
			}
		});
		
		layout.addComponent(name);
		layout.addComponent(on);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Update the state of the component from server
	 */
	public void update(){
		try {
			light = (Light) shsystem.getSmartItem(light.getHouse().getID(), light.getRoom().getID(), light.getID());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IDMatchNotFoundException e) {
			e.printStackTrace();
		}
		on.setValue(light.isON());
	}
	
	public String toString(){
		return "Light";
	}

}
