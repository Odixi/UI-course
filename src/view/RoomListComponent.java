package view;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
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
	
		public RoomListComponent(String houseName, SmartHSystem shsystem){
			
			layout = new VerticalLayout();
			layout.setSizeFull();
			
			houseBox = new CheckBox(houseName); // Varsinaisen talon checkBoxi
			layout.addComponent(houseBox);
			layout.setComponentAlignment(houseBox, Alignment.TOP_LEFT);
			
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
						}
						checkBoxes[i][j+1] = new CheckBox(items.get(j));
						layout.addComponent(checkBoxes[i][j+1]);
						layout.setComponentAlignment(checkBoxes[i][j+1], Alignment.TOP_RIGHT);
						
					}
				}
				
			} catch (RemoteException e) {e.printStackTrace();}
			
			setCompositionRoot(layout);
		}
		
		public CheckBox[][] getChackBoxes(){
			return checkBoxes;
		}
}
