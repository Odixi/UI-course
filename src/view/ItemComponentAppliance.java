package view;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import model.items.Appliance;
import server.SmartHSystem;
/**
 * Custom component for appliance type item.
 * Displays name and a checkbox to turn the appliance on or off
 * @author Ville
 *
 */
public class ItemComponentAppliance extends CustomComponent implements ItemComponent{
	
	private String itemID;
	private String houseID;
	private String roomID;
	
	private SmartHSystem shsystem;
	private Appliance appliance;
	
	private Panel panel;
	private VerticalLayout layout;
	private Label name;
	private CheckBox value;
	
	/**
	 * 
	 * @param shsystem
	 * @param houseID
	 * @param roomID
	 * @param itemID
	 * @param appliance
	 */
	public ItemComponentAppliance(SmartHSystem shsystem,String houseID, String roomID, String itemID, Appliance appliance){
		
		this.appliance = appliance;
		this.shsystem = shsystem;
		
		this.itemID = itemID;
		this.houseID = houseID;
		this.roomID = roomID;
		
		panel = new Panel();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		panel.setContent(layout);
		
		// TODO Get the light object from server
		name = new Label("Appliance. Just test.");
		value = new CheckBox("Appliance");
		
		layout.addComponent(name);
		layout.addComponent(value);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Update the state of the component from server
	 */
	public void update(){
		// TODO
	}
	
	public String toString(){
		return "Apliance";
	}

}
