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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

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
    	
        setHeight(ui.getCurrent().getPage().getBrowserWindowHeight()*0.6f, Unit.PIXELS);
       
        //Luodaan pohja leiskaan tulevat vaakaleiskat
        HorizontalLayout navigation = new HorizontalLayout();
        navigation.setHeight("5%");
        navigation.setWidth("60%");
        
        HorizontalLayout topics = new HorizontalLayout();
        topics.setHeight("20%");
        topics.setWidth("60%");
       
        HorizontalLayout lights =new HorizontalLayout();
        lights.setHeight("35%");
        lights.setWidth("60%");
       
        HorizontalLayout rooms = new HorizontalLayout();
        rooms.setHeight("35%");
        rooms.setWidth("60%");
       
       
       
        
       
        
        
     // ----- Kodin valinta----- //   
        ComboBox houseSelect = new ComboBox();
        houseSelect.setInputPrompt("Select house");
        houseSelect.setFilteringMode(FilteringMode.CONTAINS);
//        userSelect.setImmediate(true);
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
        navigation.setComponentAlignment(houseSelect, Alignment.TOP_LEFT);
        navigation.addComponent(logOut);
        navigation.setComponentAlignment(logOut, Alignment.TOP_RIGHT);
        
       
        //Huone "otsikot"
        topics.addComponent(new Label ("Rooms"));
        topics.addComponent(new Label ("Room 1"));
        topics.addComponent(new Label ("Room 2"));
        topics.addComponent(new Label ("Room 3"));
        
        
        //Lights valinnat
        lights.addComponent(new Label ("Lights"));
        CheckBox room_1 = new CheckBox("On", true);
        lights.addComponent(room_1);
        CheckBox room_2 = new CheckBox("On", true);
        lights.addComponent(room_2);
        CheckBox room_3 = new CheckBox("On", true);
        lights.addComponent(room_3);
 
        //Rooms valinnat
        Button room1 = new Button("More", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	
            }
        });
        Button room2 = new Button("More", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
               
            }
        });
        Button room3 = new Button("More", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
               
            }
        });
        rooms.addComponent(new Label(""));
        rooms.addComponent(room1);
        rooms.addComponent(room2);
        rooms.addComponent(room3);
       
       
       
        //Lopuksi lisätään nää kaikki oikeassa järjestyksessä layouttiin
        addComponent(navigation);
        addComponent(topics);
        addComponent(lights);
        addComponent(rooms);
    }
       
    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        Notification.show("userview");
       
    }
   
 
}
