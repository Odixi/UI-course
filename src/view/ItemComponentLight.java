package view;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import model.items.Light;
import server.SmartHSystem;

/**
 * Custom component for light type items.
 * Displays name and checkboxto turn the ligh on or off
 * @author Ville
 *
 */
public class ItemComponentLight extends CustomComponent implements ItemComponent{
	
	private String itemID;
	private String houseID;
	private String roomID;
	
	private SmartHSystem shsystem;
	private Light light;
	
	private Panel panel;
	private VerticalLayout layout;
	private Label name;
	private CheckBox on;
	
	public ItemComponentLight(SmartHSystem shsystem,String houseID, String roomID, String itemID, Light light){
		
		this.light = light;
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
		name = new Label("Light. Just test.");
		on = new CheckBox("Light");
		
		layout.addComponent(name);
		layout.addComponent(on);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Update the state of the component from server
	 */
	public void update(){
		// TODO
	}
	
	public String toString(){
		return "Light";
	}

}
