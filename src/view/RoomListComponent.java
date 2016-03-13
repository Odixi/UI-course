package view;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import server.SmartHSystem;

public class RoomListComponent extends CustomComponent {

	VerticalLayout layout;
	CheckBox houseBox;
	CheckBox[][] checkBoxes;
	SmartHSystem shsystem;
	String[] rooms;
	
		public RoomListComponent(String houseName){
			
			layout = new VerticalLayout();
			layout.setSizeFull();
			
			layout.addComponent(new Label("test"));
			
			houseBox = new CheckBox("house");
			layout.addComponent(houseBox);
			layout.setComponentAlignment(houseBox, Alignment.TOP_LEFT);
			
			try {
				
				ArrayList<String> rooms = shsystem.getRoomNames(houseName);
				checkBoxes = new CheckBox[rooms.size()][];
				ArrayList<String> items;
				
				for (int i = 0; i < rooms.size();i++){
					
					items = shsystem.getItems(houseName, rooms.get(i));
					checkBoxes[i] = new CheckBox[items.size() + 1];
					
					for (int j = 0; j < items.size() + 1; j++){
						
						if (j == 0){
							checkBoxes[i][j] = new CheckBox(rooms.get(i)); 
							layout.addComponent(checkBoxes[i][j]);
							layout.setComponentAlignment(checkBoxes[i][j], Alignment.TOP_CENTER);
						}
						else {
							checkBoxes[i][j] = new CheckBox(items.get(j+1));
							layout.addComponent(checkBoxes[i][j]);
							layout.setComponentAlignment(checkBoxes[i][j], Alignment.TOP_RIGHT);
						}
						
					}
				}
				
			} catch (RemoteException e) {e.printStackTrace();}
			
			setCompositionRoot(layout);
		}
}
