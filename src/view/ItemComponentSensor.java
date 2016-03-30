package view;

import java.rmi.RemoteException;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import exceptions.IDMatchNotFoundException;
import model.items.Sensor;
import server.SmartHSystem;

/**
 * 
 * @author Ville
 * Custom component for sensor items
 * Displays sensor name and value inside panel (at least for now)
 */

public class ItemComponentSensor extends CustomComponent implements ItemComponent{
	
	private Sensor sensor;
	private SmartHSystem shsystem;
	
	private Panel panel;
	private VerticalLayout layout;
	private Label name;
	private Label value;
	
	/**
	 * 
	 * @param shsystem
	 * @param sensor
	 */
	public ItemComponentSensor(SmartHSystem shsystem, Sensor s){
		
		this.sensor = s;
		this.shsystem = shsystem;
		
		panel = new Panel();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		panel.setContent(layout);
		
		
		name = new Label(sensor.getName());
		value = new Label(sensor.getSensorValue() + " " +  sensor.getSensorUnit().getUnit());
		
		layout.addComponent(name);
		layout.addComponent(value);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Updates sensor value from server
	 */
	public void update(){
		try {
			sensor = (Sensor) shsystem.getSmartItem(sensor.getHouse().getID(), sensor.getRoom().getID(), sensor.getID());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IDMatchNotFoundException e) {
			name.setValue("Could not get data from server!");
			value.setValue("-");
			e.printStackTrace();
		}
		value.setValue(sensor.getSensorValue() + " " + sensor.getSensorUnit().getUnit());
	}
	
	public String toString(){
		return "Sensor";
	}
}
