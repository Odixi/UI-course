package view;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import model.items.Appliance;
import server.SmartHSystem;

public class ItemComponentAppliance extends CustomComponent implements ItemComponent{
	
	private Panel panel;
	private VerticalLayout layout;
	private Appliance appliance;
	private Label name;
	private CheckBox value;
	
	public ItemComponentAppliance(SmartHSystem shsystem, String itemID, Appliance appliance){
		
		this.appliance = appliance;
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
