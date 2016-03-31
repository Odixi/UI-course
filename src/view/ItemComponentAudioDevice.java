package view;

import java.rmi.RemoteException;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

import exceptions.IDMatchNotFoundException;
import exceptions.IDTypeMismatch;
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
	
	private SmartHSystem shsystem;
	private AudioDevice audioDevice;
	
	private Panel panel;
	private VerticalLayout layout;
	private Label name;
	private Slider volume;
	private CheckBox on;
	
	public ItemComponentAudioDevice(SmartHSystem shsystem, AudioDevice audioDevice){
		
		this.audioDevice = audioDevice;
		this.shsystem = shsystem;
		
		panel = new Panel();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		panel.setContent(layout);
		
		name = new Label(audioDevice.getName()); // Should this be removed?
		on = new CheckBox(audioDevice.getName());
		on.setValue(audioDevice.isON());
		
		on.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (on.getValue()){
					volume.setEnabled(true);
					try {
						shsystem.turnApplianceOn(
								audioDevice.getHouse().getID(), audioDevice.getRoom().getID(), audioDevice.getID());
						
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (IDTypeMismatch e) {
						//TODO
						e.printStackTrace();
					} catch (IDMatchNotFoundException e) {
						//TODO
						e.printStackTrace();
					}
				}
				else{
					volume.setEnabled(false);
					volume.setValue(0.0);
					try{
						shsystem.turnApplianceOff(
								audioDevice.getHouse().getID(), audioDevice.getRoom().getID(), audioDevice.getID());
						shsystem.setAudioVolume(
								audioDevice.getHouse().getID(), audioDevice.getRoom().getID(), audioDevice.getID(), 0);
						
					}catch (RemoteException e){
						e.printStackTrace();
					} catch (IDMatchNotFoundException e) {
						//TODO
						e.printStackTrace();
					} catch (IDTypeMismatch e) {
						//TODO
						e.printStackTrace();
					}
					update(); // // TODO Should not be neede after UI.push is implemented
				}
				
			}
		});
		
		volume = new Slider();
		volume.setCaption("Volume " + audioDevice.getVolume() + "%");
		volume.setWidth(300f, Unit.PIXELS);
		volume.setValue((double) audioDevice.getVolume());
		volume.setEnabled(audioDevice.isON());
		
		volume.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
//				Notification.show(volume.getValue().toString());
				try {
					shsystem.setAudioVolume(
							audioDevice.getHouse().getID(), audioDevice.getRoom().getID(), audioDevice.getID(),
							volume.getValue().intValue());
					
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (IDMatchNotFoundException e) {
					//TODO
					e.printStackTrace();
				} catch (IDTypeMismatch e) {
					//TODO
					e.printStackTrace();
				}
				update(); // TODO Should not be neede after UI.push is implemented
			}
		});
		
//		layout.addComponent(name);
		layout.addComponent(on);
		layout.addComponent(volume);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Update the state of the component from server
	 */
	public void update(){
		try {
			audioDevice = (AudioDevice) shsystem.getSmartItem(
					audioDevice.getHouse().getID(), audioDevice.getRoom().getID(), audioDevice.getID());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IDMatchNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		on.setValue(audioDevice.isON());
		audioDevice.setVolume(volume.getValue().intValue());
		volume.setCaption("Volume " + audioDevice.getVolume() + "%");
	}
	
	public String toString(){
		return "AudioDevice";
	}

}
