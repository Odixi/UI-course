package view;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

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
	private Panel panel;
	private VerticalLayout layout;
	private Label name;
	private Label value;
	
	public ItemComponentSensor(SmartHSystem shsystem, String itemID){
		
		panel = new Panel();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		panel.setContent(layout);
		
		// TODO Get the sensor object from server
		name = new Label("Sensor. Just test.");
		value = new Label("Value");
		
		layout.addComponent(name);
		layout.addComponent(value);
		
		setCompositionRoot(panel);
	}
	
	// TODO method for updating the item state
	public void update(){
		
	}
	
	public String toString(){
		return "Sensor";
	}
}
