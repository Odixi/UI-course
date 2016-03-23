package view;
 
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import server.SmartHSystem;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;
import com.vaadin.ui.Slider;
import com.vaadin.server.FontAwesome;
 
public class UserView extends VerticalLayout implements View{
	
	//Attributes
	private SmartHSystem shsystem;
	private HouseTabSheet htb;
	private HorizontalLayout navigation;
	private ComboBox houseSelect;
	private Hashtable<String, Boolean> userView;
	private SmartUI ui;
 
    public UserView(SmartUI ui, SmartHSystem shsystem){
    	
    	super();
    	this.ui = ui;
		
		//For RMI calls
		this.shsystem = shsystem;
		
		// Let's get the users view from server
		try {
			userView = shsystem.getUserView(ui.getUserID());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		setMargin(true);
		setSpacing(true);
       
        //Luodaan pohja leiskaan tulevat vaakaleiskat
		navigation = new HorizontalLayout();
        navigation.setWidth("100%");
        
     // ----- Kodin valinta----- //   
        houseSelect = new ComboBox();
        houseSelect.setInputPrompt("Select house");
        houseSelect.setCaption("Select house");
        houseSelect.setFilteringMode(FilteringMode.CONTAINS);
        houseSelect.setTextInputAllowed(false);
        houseSelect.setNullSelectionAllowed(false);
        
        Hashtable<String, String> houses = new Hashtable<String, String>();  
        
		try {
			houses = shsystem.getHouseNames();
		} catch (RemoteException e) {e.printStackTrace();}

		for (String houseID : houses.keySet()){
			if (userView.get(houseID)){
				houseSelect.addItem(houseID);
				houseSelect.setItemCaption(houseID, houses.get(houseID));
				houseSelect.select(houseID); // Koska haluan, että joku arvo on valmiiksi valittu, 
			}
		}
       
		// When changing the house, build a view for new one.
       houseSelect.addValueChangeListener(new ValueChangeListener() {	
    	   @Override
			public void valueChange(ValueChangeEvent event) {
    		removeComponent(htb);
    		htb = new HouseTabSheet(shsystem, ui, (String)houseSelect.getValue(), userView); 
			addComponent(htb);
			
		}
       });

       navigation.addComponent(houseSelect);
       navigation.setComponentAlignment(houseSelect, Alignment.MIDDLE_LEFT);
       

       // ------ Smart Home -title ------ //
       Label smartHomeLabel = new Label("<font size=\"7\">Smart Home</font>");
       smartHomeLabel.setContentMode(ContentMode.HTML);
       smartHomeLabel.setSizeUndefined();
       navigation.addComponent(smartHomeLabel);
       navigation.setComponentAlignment(smartHomeLabel, Alignment.MIDDLE_CENTER);
       
       HorizontalLayout rightLayout = new HorizontalLayout();
       rightLayout.setSpacing(true);
       
		// Label that tells the current user
		Label loggedUser = new Label("<font size=\"4\">" + ui.getUsername() + "</font>");
		loggedUser.setContentMode(ContentMode.HTML);
		loggedUser.setSizeUndefined();
		rightLayout.addComponent(loggedUser);
		rightLayout.setComponentAlignment(loggedUser, Alignment.MIDDLE_RIGHT);
		
		// ------ Change Password ------ //
		
		ChangePasswordPopupContent cpContent = new ChangePasswordPopupContent(shsystem);
		PopupView changePassword = new PopupView(cpContent);
		cpContent.setPopupView(changePassword);
		rightLayout.addComponent(changePassword);
		rightLayout.setComponentAlignment(changePassword, Alignment.MIDDLE_RIGHT);
       
		// ------ Logout ------ //
        Button logOut= new Button("Logout",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	ui.getNavigator().removeView(ui.USERVIEW);
                ui.getNavigator().navigateTo(ui.LOGINVIEW);
            }
        });
        logOut.setIcon(FontAwesome.SIGN_OUT);
        rightLayout.addComponent(logOut);
        rightLayout.setComponentAlignment(logOut, Alignment.MIDDLE_RIGHT);
        
        navigation.addComponent(rightLayout);
        navigation.setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
        addComponent(navigation);
        
        // initiate house view if the user has the rights
        if (!houseSelect.isEmpty()){
        	htb = new HouseTabSheet(shsystem, ui, (String)houseSelect.getValue(), userView);
			addComponent(htb);
    	}
    }
       
    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        Notification.show("HI " + ui.getUsername() + "!");
       
    }
   
 
}
