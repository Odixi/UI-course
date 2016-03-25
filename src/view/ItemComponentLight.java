package view;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import model.items.Light;
import server.SmartHSystem;

public class ItemComponentLight extends CustomComponent implements ItemComponent{
	
	private Panel panel;
	private VerticalLayout layout;
	private Light light;
	private Label name;
	private CheckBox on;
	
	public ItemComponentLight(SmartHSystem shsystem, String itemID, Light light){
		
		this.light = light;
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
