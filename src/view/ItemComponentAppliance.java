package view;

import java.rmi.RemoteException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import exceptions.IDMatchNotFoundException;
import exceptions.IDTypeMismatch;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
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
		
		name = new Label(appliance.getName()); // Sould this just be removed?
		value = new CheckBox(appliance.getName());
		value.setValue(appliance.isON());
		
		value.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// When appliance is turned on / off
				if (value.getValue()){
					try {
						shsystem.turnApplianceOn(houseID, roomID, itemID);
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (IDTypeMismatch e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IDMatchNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					try {
						shsystem.turnApplianceOff(houseID, roomID, itemID);
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (IDTypeMismatch e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IDMatchNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		
		layout.addComponent(name);
		layout.addComponent(value);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Update the state of the component from server
	 */
	public void update(){
		try {
			appliance = (Appliance) shsystem.getSmartItem(houseID, roomID, itemID);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IDMatchNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		value.setValue(appliance.isON());
	}
	
	public String toString(){
		return "Apliance";
	}

}
