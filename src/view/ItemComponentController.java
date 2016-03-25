package view;

import java.rmi.RemoteException;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import model.items.AudioDevice;
import model.items.Controller;
import server.SmartHSystem;

public class ItemComponentController extends CustomComponent implements ItemComponent{

	private Panel panel;
	private VerticalLayout layout;
	private HorizontalLayout layout2;
	private Controller controller;
	private Label name;
	private Button plus;
	private Button minus;
	private Label value;
	private String unit;
	
	
	public ItemComponentController(SmartHSystem shsystem, String itemID, Controller controller){
		
		this.controller = controller;
		panel = new Panel();
		
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		layout2 = new HorizontalLayout();
		layout2.setSpacing(true);
		
		panel.setContent(layout);
		
		try {
			controller = (Controller) shsystem.getSmartItem(itemID);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (controller == null){
			name = new Label("Could not get data from server");
			value = new Label("???");
		}
		else{
			name = new Label(controller.getName());
			value = new Label(controller.getControllerValue() + unit);
		}
		plus = new Button("+" , new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		minus = new Button("-" , new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
			
			
			
		layout.addComponent(name);
		layout.addComponent(layout2);
		layout2.addComponent(minus);
		layout2.addComponent(value);
		layout2.addComponent(plus);
			
		setCompositionRoot(panel);
	}
	
	/**
	 * Update the state of the component from server
	 */
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
