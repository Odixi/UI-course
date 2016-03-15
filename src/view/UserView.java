/*TO DO
 * 
 * Kunt taloa vaihdetaan, niin houseNow:n pit‰‰ vaihtua
 * 
 */


package view;
 
import java.rmi.RemoteException;
import java.util.ArrayList;

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
 
public class UserView extends VerticalLayout implements View{
	
	//Attributes
	private SmartHSystem shsystem;
	
 
    public UserView(SmartUI ui, SmartHSystem shsystem){
    	
    	super();
		
		//For RMI calls
		this.shsystem = shsystem;
		
		setMargin(true);
		setSpacing(true);
    	
        //setHeight(ui.getCurrent().getPage().getBrowserWindowHeight(), Unit.PIXELS);
		
		String houseNow= new String ("Talo1");
       
        //Luodaan pohja leiskaan tulevat vaakaleiskat
        HorizontalLayout navigation = new HorizontalLayout();
        //navigation.setHeight("20%");
        navigation.setWidth("100%");
      
       
        Panel houseManager= new Panel("");
    	VerticalLayout managerLayout = new VerticalLayout();
    	managerLayout.setSpacing(true);
    	managerLayout.setMargin(true);
    	
    	houseManager.setWidth(ui.getCurrent().getPage().getBrowserWindowWidth()*0.8f, Unit.PIXELS);
    	houseManager.setHeight(500f, Unit.PIXELS);
    	
    	managerLayout.setHeight(houseManager.getHeight()*0.95f, Unit.PIXELS);
    	managerLayout.setWidth(houseManager.getWidth()*0.95f, Unit.PIXELS);
    	
    	
    	 //Vaakaleiskat jotka voidaan lis‰t‰ paneeliin        
        HorizontalLayout topics = new HorizontalLayout();
        topics.setHeight(houseManager.getHeight(), Unit.PIXELS);
        topics.setWidth(houseManager.getWidth(), Unit.PIXELS);
       
        HorizontalLayout lights =new HorizontalLayout();
        lights.setHeight(houseManager.getHeight(), Unit.PIXELS);
        lights.setWidth(houseManager.getWidth(), Unit.PIXELS);
       
        HorizontalLayout rooms = new HorizontalLayout();
        rooms.setHeight(houseManager.getHeight(), Unit.PIXELS);
        rooms.setWidth(houseManager.getWidth(), Unit.PIXELS);
       
    	

        
     // ----- Kodin valinta----- //   
        ComboBox houseSelect = new ComboBox();
        houseSelect.setInputPrompt("Change home");
        houseSelect.setFilteringMode(FilteringMode.CONTAINS);
        houseSelect.setTextInputAllowed(false);
        houseSelect.setNullSelectionAllowed(false);
        
        ArrayList<String> homes = new ArrayList<String>();
                
		try {
			homes = shsystem.getHouseNames();
		} catch (RemoteException e) {e.printStackTrace();}

        houseSelect.addItems(homes);
        
        
        
        Button logOut= new Button("LogOut",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ui.getNavigator().navigateTo(ui.LOGINVIEW);
            }
        });
        navigation.addComponent(houseSelect);
        navigation.setComponentAlignment(houseSelect, Alignment.MIDDLE_LEFT);
        navigation.addComponent(logOut);
        navigation.setComponentAlignment(logOut, Alignment.MIDDLE_RIGHT);
        
       
        //Huone "otsikot"
        
        ArrayList<String> roomNames = new ArrayList<String>();
		try {
			roomNames = shsystem.getRoomNames(houseNow);
		} catch (RemoteException e) {e.printStackTrace();}
		
		topics.addComponent(new Label ("Rooms"));
		lights.addComponent(new Label ("Lights"));
		rooms.addComponent(new Label(""));
		for(int i=0; i<roomNames.size(); i++){
			String currentRoomString = roomNames.get(i);
			Label currentRoom = new Label (currentRoomString);
			topics.addComponent(currentRoom);
		}
		for(int i=0; i<roomNames.size(); i++){
			CheckBox lightSelect = new CheckBox("On", true);
	        lights.addComponent(lightSelect);
		}
		for(int i=0; i<roomNames.size(); i++){
			String currentRoomString = roomNames.get(i);
			Button moreButton = new Button("More ... ", new Button.ClickListener() {
	            @Override
	            public void buttonClick(ClickEvent event) {
	            	Window roomManagerWindow = new Window (currentRoomString);
	            	roomManagerWindow.setHeight(300.0f, Unit.PIXELS);
	            	roomManagerWindow.setWidth(300.0f, Unit.PIXELS);
	            	VerticalLayout itemLayout = new VerticalLayout();
	            	
	            	ArrayList<String> items = new ArrayList<String>();
	            	
	            	try {
	        			items = shsystem.getItems(houseNow, currentRoomString);
	        		} catch (RemoteException e) {e.printStackTrace();}
	            	
	            	for(int i=0; i<items.size(); i++){
	            		
	            		itemLayout.addComponent(new Label ("item "+i));
	            	}
	            	roomManagerWindow.setContent(itemLayout);
	            	UI.getCurrent().addWindow(roomManagerWindow);
	            }
	        });
			rooms.addComponent(moreButton);
		}
	
               
        
        //Lis‰t‰‰n n‰m‰ paneelin layouttiin
        managerLayout.addComponent(topics);
    	topics.setSizeFull();
        managerLayout.addComponent(lights);
        lights.setSizeFull();
        managerLayout.addComponent(rooms);
        rooms.setSizeFull();
        
        houseManager.setContent(managerLayout);
       
        //Lopuksi lis√§t√§√§n n√§√§ kaikki oikeassa j√§rjestyksess√§ layouttiin
        addComponent(navigation);
        addComponent(houseManager);
       
        setComponentAlignment(houseManager, Alignment.MIDDLE_CENTER);
        
    }
       
    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        Notification.show("userview");
       
    }
   
 
}
