package view;

import java.rmi.RemoteException;
import java.util.ArrayList;

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
	CheckBox houseBox;
	CheckBox[][] checkBoxes;
	SmartHSystem shsystem;
	String[] rooms;
	Panel panel;
	
		public RoomListComponent(String houseName, SmartHSystem shsystem){
			
			panel = new Panel();
		
			layout = new VerticalLayout();
			layout.setSizeFull();
			layout.setMargin(true);		
			
			panel.setContent(layout);
			
			houseBox = new CheckBox(houseName); // Varsinaisen talon checkBoxi
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
				ArrayList<String> rooms = shsystem.getRoomNames(houseName);
				checkBoxes = new CheckBox[rooms.size()][];
				ArrayList<String> items;
				
				for (int i = 0; i < rooms.size();i++){
					
					// Haetaan erikseen jokaisen huoneen esineet
					items = shsystem.getItems(houseName, rooms.get(i));
					checkBoxes[i] = new CheckBox[items.size() + 1];
					
					for (int j = 0; j < items.size(); j++){
						
						// checkBox listan [x][0] vastaa aina itse huonetta! (Ei siis esine)
						if (j == 0){
							checkBoxes[i][j] = new CheckBox(rooms.get(i)); 
							layout.addComponent(checkBoxes[i][j]);
							layout.setComponentAlignment(checkBoxes[i][j], Alignment.TOP_CENTER);
							checkBoxes[i][j].addValueChangeListener(listener);
						}
						checkBoxes[i][j+1] = new CheckBox(items.get(j));
						layout.addComponent(checkBoxes[i][j+1]);
						layout.setComponentAlignment(checkBoxes[i][j+1], Alignment.TOP_RIGHT);
						checkBoxes[i][j+1].addValueChangeListener(listener);
					}
				}
				
			} catch (RemoteException e) {e.printStackTrace();}
			
			updateCbs();
			setCompositionRoot(panel);
		}
		
		public CheckBox[][] getChackBoxes(){
			return checkBoxes;
		}
		
		public void updateCheckBoxesFromServer(){
			//TODO haetaan käyttäjän oikeudet serveriltä
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
