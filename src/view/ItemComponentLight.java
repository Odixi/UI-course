package view;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import model.items.Light;
import server.SmartHSystem;

public class ItemComponentLight extends CustomComponent{
	
	private Panel panel;
	private VerticalLayout layout;
	private Light light;
	private Label name;
	private Label value;
	
	public ItemComponentLight(SmartHSystem shsystem, String itemID){
		
		panel = new Panel();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		panel.setContent(layout);
		
		// TODO Get the light object from server
		name = new Label("Light. Just test.");
		value = new Label("on/off");
		
		layout.addComponent(name);
		layout.addComponent(value);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Update the state of the component from server
	 */
	public void update(Light newLight){
		// TODO
	}

}
