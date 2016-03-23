package view;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import model.items.AudioDevice;
import server.SmartHSystem;

public class ItemComponentAudioDevice extends CustomComponent {
	private Panel panel;
	private VerticalLayout layout;
	private AudioDevice audioDevice;
	private Label name;
	private Label value;
	
	public ItemComponentAudioDevice(SmartHSystem shsystem, String itemID){
		
		panel = new Panel();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		panel.setContent(layout);
		
		// TODO Get the light object from server
		name = new Label("AudioDevice. Just test.");
		value = new Label("Value");
		
		layout.addComponent(name);
		layout.addComponent(value);
		
		setCompositionRoot(panel);
	}
	
	/**
	 * Update the state of the component from server
	 */
	public void update(AudioDevice newAudioDevice){
		// TODO
	}

}
