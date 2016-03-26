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
	
	private String itemID;
	private String houseID;
	private String roomID;
	
	private Panel panel;
	private VerticalLayout layout;
	private Label name;
	private Label value;
	
	public ItemComponentSensor(SmartHSystem shsystem,String houseID, String roomID, String itemID, Sensor sensor){
		
		this.sensor = sensor;
		this.shsystem = shsystem;
		
		this.itemID = itemID;
		this.houseID = houseID;
		this.roomID = roomID;
		
		panel = new Panel();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		panel.setContent(layout);
		
		
		name = new Label(sensor.getName());
		value = new Label(sensor.getSensorValue() + sensor.getSensorUnit().getUnit());
		
		layout.addComponent(name);
		layout.addComponent(value);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Updates sensor value from server
	 */
	public void update(){
		try {
			sensor = (Sensor) shsystem.getSmartItem(itemID);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IDMatchNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		value.setValue(sensor.getSensorValue() + sensor.getSensorUnit().getUnit());
	}
	
	public String toString(){
		return "Sensor";
	}
}
