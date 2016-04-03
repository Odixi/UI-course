package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.google.gwt.dev.util.collect.HashMap;
import com.vaadin.data.Container.ItemSetChangeListener;
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

import exceptions.ElementNullException;
import java_cup.version;
import server.SmartHSystem;

/**
 * Custom component for AdminView for selecting items to users views.
 * Associated with spesific house.
 * @author Ville
 *
 */
public class RoomListComponent extends CustomComponent {

	private VerticalLayout layout;
	private VerticalLayout roomLayouts[];
	private VerticalLayout itemLayouts[];
	private HiddenValueCheckBox houseBox;
	private HiddenValueCheckBox[][] checkBoxes;
	private SmartHSystem shsystem;
	private String[] rooms;
	private Panel panel;
	private AdminView av;
		
	/**
	 * 
	 * @param houseID
	 * @param houseName
	 * @param shsystem
	 * @param adminView
	 */
		public RoomListComponent(String houseID, String houseName, SmartHSystem shsystem, AdminView av){
			
			this.av = av;
			this.shsystem = shsystem;
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
				roomLayouts = new VerticalLayout[rooms.size()];
				itemLayouts = new VerticalLayout[rooms.size()];
				Hashtable<String, String> items;
				
				int i = 0;
				int j = 0;
				
				for (String roomID : rooms.keySet()){
					
					// Haetaan erikseen jokaisen huoneen esineet
					
					//Call getItems with houseID and roomID (Hashtable key)
					items = shsystem.getItems(houseID, roomID);
					
					checkBoxes[i] = new HiddenValueCheckBox[items.size() + 1];
					
					roomLayouts[i] = new VerticalLayout();
					layout.addComponent(roomLayouts[i]);
					
					itemLayouts[i] = new VerticalLayout();
					layout.addComponent(itemLayouts[i]);
					
					roomLayouts[i].setWidth(80f, Unit.PERCENTAGE);
					itemLayouts[i].setWidth(60f, Unit.PERCENTAGE);
					
					layout.setComponentAlignment(roomLayouts[i], Alignment.TOP_RIGHT);
					layout.setComponentAlignment(itemLayouts[i], Alignment.TOP_RIGHT);
				
					for (String itemKey : items.keySet()){
						
						// checkBox listan [x][0] vastaa aina itse huonetta! (Ei siis esine)
						if (j == 0){
							checkBoxes[i][j] = new HiddenValueCheckBox(roomID, rooms.get(roomID));
							roomLayouts[i].addComponent(checkBoxes[i][j]);
							roomLayouts[i].setComponentAlignment(checkBoxes[i][j], Alignment.TOP_LEFT);
							checkBoxes[i][j].addValueChangeListener(listener);
						}
						checkBoxes[i][j+1] = new HiddenValueCheckBox(itemKey,items.get(itemKey));
						itemLayouts[i].addComponent(checkBoxes[i][j+1]);
						itemLayouts[i].setComponentAlignment(checkBoxes[i][j+1], Alignment.TOP_LEFT);
						checkBoxes[i][j+1].addValueChangeListener(listener);
						j++;
					}
					j = 0;
					i++;
				}
				
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (ElementNullException e) {
				e.printStackTrace();
			}
			
			updateCbs();
			setCompositionRoot(panel);
		}
		/**
		 * For getting the intended values of userView
		 * Called from adminView
		 * @return ArrayList of HiddenCheckBoxes 
		 */
		public ArrayList<HiddenValueCheckBox> getCheckBoxes(){
			ArrayList<HiddenValueCheckBox> list = new ArrayList<HiddenValueCheckBox>();
			for (int i = 0; i < checkBoxes.length; i++){
				for (int j = 0; j < checkBoxes[i].length; j++){
					list.add(checkBoxes[i][j]);
				}
			}
			list.add(houseBox);
			return list;
		}
		
		/**
		 * Updates the checkbox values to correspond selected users view
		 * Called from adminView
		 */
		public void updateCheckBoxesFromServer(){
			
			if ( av.getSelectedUserID() != null ){ //If user is selected
				try {	
					
					Hashtable<String, Boolean> hm = shsystem.getUserView( av.getSelectedUserID() );
					
					for (String key : hm.keySet()){
						inner:
						for (int i = 0; i < checkBoxes.length; i++){
							for (int j = 0; j < checkBoxes[i].length; j++){
								if (key.equals(checkBoxes[i][j].getHiddenValue())){
									checkBoxes[i][j].setValue(hm.get(key));
									break inner;
								}
								else if (key.equals(houseBox.getHiddenValue())){
									houseBox.setValue(hm.get(key));
								}
							}
						}
					}
					
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (ElementNullException e) {
					e.printStackTrace();
				}	
			} //if
		}
		
		// Disabloidaan / enablidaan checkboxit, riippuen sen vanhempien valoinnoista
		private void updateCbs(){
			
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
