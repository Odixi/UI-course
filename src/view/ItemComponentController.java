package view;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import exceptions.IDMatchNotFoundException;
import exceptions.IDTypeMismatch;

import com.vaadin.ui.Button.ClickEvent;

import model.items.Controller;
import model.items.ControllerType;
import model.items.SensorUnit;
import server.SmartHSystem;
/**
 * Custom component for controller type items
 * @author Ville
 *
 */
public class ItemComponentController extends CustomComponent implements ItemComponent{
	
	private SmartHSystem shsystem;
	private Controller controller;
	
	private Panel panel;
	private VerticalLayout layout;
	private HorizontalLayout layout2;
	private Label name;
	private Button plus;
	private Button minus;
	private Label value;
	
	private DecimalFormat df;
	
	
	public ItemComponentController(SmartHSystem shsystem, Controller control){
		
		this.controller = control;
		this.shsystem = shsystem;
		panel = new Panel();
		
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		layout2 = new HorizontalLayout();
		layout2.setSpacing(true);
		
		panel.setContent(layout);
		
		df = new DecimalFormat("#.#"); // To round doubles
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH)); // To change seperating symbol
		
		// TODO unneccesary atm (this shouldn't ever happen)
		if (controller == null){
			name = new Label("Could not get data from server");
			value = new Label("???");
		}
		else{
			name = new Label(controller.getName());
			value = new Label(df.format(controller.getControllerValue())+ " " + controller.getControllerUnit().getUnit());
		}
		
		// Maybe I'll later add a way to change the values quiker (especially the light controller)
		plus = new Button("+" , new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					
					if (controller.getControllerUnit() == SensorUnit.CELCIUS){
						if (!shsystem.setControllerValue(
								controller.getHouse().getID(), controller.getRoom().getID(), controller.getID(), 
								controller.getControllerValue() + 0.5)){
							Notification.show("Out of range!");
						}
						update();
					}
					else if (controller.getControllerUnit() == SensorUnit.HUMIDITYPERCENT){
						if (!shsystem.setControllerValue(
								controller.getHouse().getID(), controller.getRoom().getID(), controller.getID(),
								controller.getControllerValue() + 5.0)){
							Notification.show("Out of range!");
						}
						update();
					}
					else if (controller.getControllerUnit() == SensorUnit.LUMEN){
						// Lets add value: value + sqrt(value) + value * 0.1 
						// Because why not
						
						double newValue = controller.getControllerValue() 
								+ Math.sqrt(controller.getControllerValue()) + controller.getControllerValue() * 0.1;
						
						if (newValue > controller.getMaxValue()){
							shsystem.setControllerValue(controller.getHouse().getID(), controller.getRoom().getID(), 
									controller.getID(), controller.getMaxValue());
						}
						else if (newValue == 0.0){
							shsystem.setControllerValue(controller.getHouse().getID(), controller.getRoom().getID(), 
									controller.getID(), 1);
						}
						else{
							shsystem.setControllerValue(controller.getHouse().getID(), controller.getRoom().getID(), 
									controller.getID(), newValue);
						}
						update();
					}
				}catch(RemoteException e){
					e.printStackTrace();
				}catch (IDMatchNotFoundException e){
					e.printStackTrace();
					name = new Label("Something went wrong!");
					value = new Label("???");
				}catch(IDTypeMismatch e){
					e.printStackTrace();
					name = new Label("Something went wrong!");
					value = new Label("???");
				}
			}
		});
		
		minus = new Button("-" , new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					if (controller.getControllerUnit() == SensorUnit.CELCIUS){
						if (!shsystem.setControllerValue(
								controller.getHouse().getID(), controller.getRoom().getID(), controller.getID(),
								controller.getControllerValue() - 0.5)){
							Notification.show("Out of range!");
						}
						update();
					}
					else if (controller.getControllerUnit() == SensorUnit.HUMIDITYPERCENT){
						if (!shsystem.setControllerValue(
								controller.getHouse().getID(), controller.getRoom().getID(), controller.getID(),
								controller.getControllerValue() - 5.0)){
							Notification.show("Out of range!");
						}
						update();
					}
					else if (controller.getControllerUnit() == SensorUnit.LUMEN){
						// Lets add value: value + sqrt(value) + value * 0.1 
						// Because why not
						
						double newValue = controller.getControllerValue() 
								- Math.sqrt(controller.getControllerValue()) - controller.getControllerValue() * 0.1;
					
						if (newValue < controller.getMinValue()){
							shsystem.setControllerValue(controller.getHouse().getID(), controller.getRoom().getID(), 
									controller.getID(), controller.getMinValue());
						}else{
							shsystem.setControllerValue(controller.getHouse().getID(), controller.getRoom().getID(), 
									controller.getID(), newValue);
						}
						update();
					}
				}catch(RemoteException e){
					e.printStackTrace();
				}catch (IDMatchNotFoundException e){
					e.printStackTrace();
					name = new Label("Something went wrong! Try to refresh page");
					value = new Label("???");
				}catch(IDTypeMismatch e){
					e.printStackTrace();
					name = new Label("Something went wrong! Try to refresh page");
					value = new Label("???");
				}
				
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
		try {
			controller = (Controller) shsystem.getSmartItem(
					controller.getHouse().getID(), controller.getRoom().getID(), controller.getID());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IDMatchNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		value.setValue(df.format(controller.getControllerValue()) + " " + controller.getControllerUnit().getUnit());
		 
	}
	
	public String toString(){
		return "Controller";
	}

}
