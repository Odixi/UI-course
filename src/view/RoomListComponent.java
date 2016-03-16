package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.google.gwt.dev.util.collect.HashMap;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import server.SmartHSystem;

/*
 *  Hierarkinen ruksiboxi lista talon huoneista ja niiden esineistä
 */
public class RoomListComponent extends CustomComponent {

	VerticalLayout layout;
	HiddenValueCheckBox houseBox;
	HiddenValueCheckBox[][] checkBoxes;
	SmartHSystem shsystem;
	String[] rooms;
	Panel panel;
	AdminView av;
	
		public RoomListComponent(String houseID, String houseName, SmartHSystem shsystem, AdminView av){
			this.av = av;
			panel = new Panel();
		
			layout = new VerticalLayout();
			layout.setSizeFull();
			layout.setMargin(true);		
			
			panel.setContent(layout);
			
			houseBox = new HiddenValueCheckBox(houseID, houseName); // Varsinaisen talon checkBoxi
			layout.addComponent(houseBox);
			layout.setComponentAlignment(houseBox, Alignment.TOP_LEFT);
			
			ValueChangeListener listener = new ValueChangeListener() {
				
				@Override
				public void valueChange(ValueChangeEvent event) {
					updateCbs();
					
				}
			};
			houseBox.addValueChangeListener(listener);
			
			try {
				
				// Haetaan huoneiden nimet listaan
				//ArrayList<String> rooms = shsystem.getRoomNames(houseName);
				Hashtable<String, String> rooms = shsystem.getRoomNames(houseID);
				checkBoxes = new HiddenValueCheckBox[rooms.size()][];
				ArrayList<String> items;
				
				int i = 0;
				
				for (String key : rooms.keySet()){
					
					// Haetaan erikseen jokaisen huoneen esineet
					items = shsystem.getItems(houseName, rooms.get(key)); //TODO Method call going to change
					checkBoxes[i] = new HiddenValueCheckBox[items.size() + 1];
				
					for (int j = 0; j < items.size(); j++){
						
						// checkBox listan [x][0] vastaa aina itse huonetta! (Ei siis esine)
						if (j == 0){
							checkBoxes[i][j] = new HiddenValueCheckBox(key, rooms.get(key));
							layout.addComponent(checkBoxes[i][j]);
							layout.setComponentAlignment(checkBoxes[i][j], Alignment.TOP_CENTER);
							checkBoxes[i][j].addValueChangeListener(listener);
						}
						checkBoxes[i][j+1] = new HiddenValueCheckBox(items.get(j));
						layout.addComponent(checkBoxes[i][j+1]);
						layout.setComponentAlignment(checkBoxes[i][j+1], Alignment.TOP_RIGHT);
						checkBoxes[i][j+1].addValueChangeListener(listener);
					}
					i++;
				}
				
			} catch (RemoteException e) {e.printStackTrace();}
			
			updateCbs();
			setCompositionRoot(panel);
		}
		
		public HiddenValueCheckBox[][] getChackBoxes(){
			return checkBoxes;
		}
		
		// Haetaan käyttäjän oikeudet serveriltä
		public void updateCheckBoxesFromServer(){
			try {
				
				Hashtable<String, Boolean> hm = shsystem.getUserView((String)av.getComboBox().getValue());
				
				for (String key : hm.keySet()){
					for (int i = 0; i < checkBoxes.length; i++){
						for (int j = 0; j < checkBoxes[i].length; j++){
							if (key == checkBoxes[i][j].getHiddenValue()){
								checkBoxes[i][j].setValue(hm.get(key));
							}
						}
					}
				}
				
			} catch (RemoteException e) {e.printStackTrace();}	
		}
		
		// Disabloidaan / enablidaan checkboxit, riippuen sen vanhempien valoinnoista
		public void updateCbs(){
			
			for (int i = 0; i < checkBoxes.length; i++){
				if (!houseBox.getValue()){
					checkBoxes[i][0].setEnabled(false);
				}
				else{
					checkBoxes[i][0].setEnabled(true);
				}
				for (int j = 1; j < checkBoxes[i].length; j++){
					if (!checkBoxes[i][0].getValue() || !houseBox.getValue()){
						checkBoxes[i][j].setEnabled(false);
					}
					else {
						checkBoxes[i][j].setEnabled(true);
					}
				}
			}
		}
}
