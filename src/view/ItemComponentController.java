package view;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

import model.items.AudioDevice;
import model.items.Controller;
import server.SmartHSystem;

public class ItemComponentController extends CustomComponent implements ItemComponent{

	private Panel panel;
	private VerticalLayout layout;
	private HorizontalLayout layout2;
	private Controller controller;
	private Label name;

	
	public ItemComponentController(SmartHSystem shsystem, String itemID){
		
		panel = new Panel();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		panel.setContent(layout);
		
		// TODO
		name = new Label("Controller. Just test.");

	
		
		layout.addComponent(name);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Update the state of the component from server
	 */
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
