package view;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

import model.items.AudioDevice;
import server.SmartHSystem;
/**
 * Custom component for aduio devices.
 * Displays name, checkbox and slider.
 * Checkbox turn on / off the device.
 * Slider changes the volume of the audio device.
 * @author Ville
 *
 */
public class ItemComponentAudioDevice extends CustomComponent implements ItemComponent{
	
	private String itemID;
	private String houseID;
	private String roomID;
	
	private SmartHSystem shsystem;
	private AudioDevice audioDevice;
	
	private Panel panel;
	private VerticalLayout layout;
	private Label name;
	private Slider volume;
	private CheckBox on;
	
	public ItemComponentAudioDevice(SmartHSystem shsystem,String houseID, String roomID, String itemID, AudioDevice audioDevice){
		
		this.audioDevice = audioDevice;
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
		name = new Label("AudioDevice. Just test.");
		on = new CheckBox("AudioDevice");
		
		on.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if (on.getValue()){
					volume.setEnabled(true);
				}
				else{
					volume.setEnabled(false);
					volume.setValue(0.0);
				}
				
			}
		});
		
		volume = new Slider();
		
		volume.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				Notification.show(volume.getValue().toString());
			}
		});
		
		layout.addComponent(name);
		layout.addComponent(volume);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Update the state of the component from server
	 */
	public void update(){
		// TODO
	}
	
	public String toString(){
		return "AudioDevice";
	}

}
