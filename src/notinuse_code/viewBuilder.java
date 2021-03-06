package notinuse_code;
 
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import server.SmartHSystem;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

import exceptions.ElementNullException;

import com.vaadin.ui.Slider;
import com.vaadin.server.FontAwesome;

public class viewBuilder {
	
	
	public Panel houseViewer(String houseNow, String houseIDNow, SmartHSystem shsystem){
		Panel houseManager= new Panel("");
    	VerticalLayout managerLayout = new VerticalLayout();
    	managerLayout.setSpacing(true);
    	managerLayout.setMargin(true);
    	
    	houseManager.setWidth(800f, Unit.PIXELS);
    	houseManager.setHeight(500f, Unit.PIXELS);
    	
    	managerLayout.setHeight(houseManager.getHeight()*0.95f, Unit.PIXELS);
    	managerLayout.setWidth(houseManager.getWidth()*0.95f, Unit.PIXELS);
    	
    	HorizontalLayout topics = new HorizontalLayout();
        topics.setHeight(houseManager.getHeight(), Unit.PIXELS);
        topics.setWidth(houseManager.getWidth(), Unit.PIXELS);
       
        HorizontalLayout lights =new HorizontalLayout();
        lights.setHeight(houseManager.getHeight(), Unit.PIXELS);
        lights.setWidth(houseManager.getWidth(), Unit.PIXELS);
       
        HorizontalLayout rooms = new HorizontalLayout();
        rooms.setHeight(houseManager.getHeight(), Unit.PIXELS);
        rooms.setWidth(houseManager.getWidth(), Unit.PIXELS);
    	
    	
        Hashtable<String, String> roomNames = new Hashtable<String, String>();
        
        try {
			roomNames = shsystem.getRoomNames(houseIDNow);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ElementNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		topics.addComponent(new Label ("Rooms"));
		lights.addComponent(new Label ("Lights" ));
		rooms.addComponent(new Label(""));
		for(String value : roomNames.values()){
			String currentRoomString = roomNames.get(value);
			Label currentRoom = new Label (currentRoomString+" id "+value);   //welll, here's something that needs to be changed
			topics.addComponent(currentRoom);
		}
		for(String value : roomNames.values()){					//This probably needs to change a little...
			CheckBox lightSelect = new CheckBox("On", true);	//Tying the ID to component would smoothen things.
	        lights.addComponent(lightSelect);
		}
		for(String value : roomNames.values()){
			String currentRoomString = roomNames.get(value);
			
			Button moreButton = new Button("More", new Button.ClickListener() {
	            @Override
	            public void buttonClick(ClickEvent event) {
	            	Window roomManagerWindow = new Window (currentRoomString);
	            	roomManagerWindow.setHeight(400.0f, Unit.PIXELS);
	            	roomManagerWindow.setWidth(600.0f, Unit.PIXELS);
	            	VerticalLayout itemLayout = new VerticalLayout();
	            	
	            	Hashtable<String,String> items = new Hashtable<String,String>();
	            	
	            	try {
	        			items = shsystem.getItems(houseNow, currentRoomString); // Tosiaan muutin tuon ArrayListin Hastableksi -Ville
	        		} catch (RemoteException e) {
	        			e.printStackTrace();
	        		} catch (ElementNullException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	for(String itemKey : items.keySet()){
	            		HorizontalLayout forItem =new HorizontalLayout();
	            		forItem.setHeight(40.0f, Unit.PIXELS);
	            		forItem.setWidth(590.0f, Unit.PIXELS);	
	            		
	            		forItem.addComponent(new Label (items.get(itemKey)));
	            		Slider sample = new Slider();
	                    sample.setImmediate(true);
	                    sample.setMin(0.0);
	                    sample.setMax(100.0);
	                    sample.setValue(50.0);
	                    forItem.addComponent(sample);
	                    
	                    /*if(item instanceof AudioDevice){
	                    	forItem.addComponent(new Label ("item "+(i+1)+" name"));
	                    	Slider volume = new Slider();
		                    volume.setImmediate(true);
		                    volume.setMin(0.0);
		                    volume.setMax(100.0);
		                    volume.setValue(50.0);
		                    forItem.addComponent(volume);
	                    }if(item instanceof ElecDevice){
	                    	forItem.addComponent(new Label ("item "+(i+1)+" name"));
	                    	forItem.addComponent(new Label ("item value now"));
	                    	CheckBox device = new CheckBox("On", true);
	                    	forItem.addComponent(device);
	                    }if(item instanceof Light){
	                    	forItem.addComponent(new Label ("item "+(i+1)+" name"));
	                    	CheckBox device = new CheckBox("On", true);
	                    	forItem.addComponent(device);
	                    }if(item instanceof Sensor){
	                    	forItem.addComponent(new Label ("item "+(i+1)+" name"));
	                    	forItem.addComponent(new Label ("item value"));
	                    }*/
	                    
	            		itemLayout.addComponent(forItem);
	            		
	            	}
	            	roomManagerWindow.setContent(itemLayout);
	            	UI.getCurrent().addWindow(roomManagerWindow);
	            }
	        });
			moreButton.setIcon(FontAwesome.PLUS);
			rooms.addComponent(moreButton);
		}
        
               
        
        //Lis�t��n n�m� paneelin layouttiin
        managerLayout.addComponent(topics);
    	topics.setSizeFull();
        managerLayout.addComponent(lights);
        lights.setSizeFull();
        managerLayout.addComponent(rooms);
        rooms.setSizeFull();
        
        houseManager.setContent(managerLayout);
        
       
        
        return houseManager;
        
	}
}
