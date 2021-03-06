package view;
 
import java.rmi.RemoteException;
import java.util.Hashtable;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import server.SmartHSystem;
import com.vaadin.ui.ComboBox;

import exceptions.ElementNullException;

import com.vaadin.server.FontAwesome;

/**
 * A view for user to observe and mangage the current state of the items in the houses.
 * @author Ville
 *
 */

public class AdminHouseView extends VerticalLayout implements View{
	
	//Attributes
	private SmartHSystem shsystem;
	private HouseTabSheet htb;
	private HorizontalLayout navigation;
	private ComboBox houseSelect;
	private Hashtable<String, Boolean> userView;
	private SmartUI ui;
 
	// ------ Construktor ------ //
	
	/**
	 * Construktor to build the view
	 * @param ui, the UI, to which the view is build
	 * @param shsystem, SmartHSystem for RMI calls to interact with the backend
	 */
    public AdminHouseView(SmartUI ui, SmartHSystem shsystem){
    	
    	super();
    	this.ui = ui;
		
		//For RMI calls
		this.shsystem = shsystem;
		
		// Lets make a view with rights to everything for admin
		// Because I'm lazy, I'll just take first users view and modify it to have the rights to everything	

		try {
			String uID = shsystem.getUsers().keys().nextElement(); 
			userView = shsystem.getUserView(uID);
			for (String key : userView.keySet()){
				userView.replace(key, true);
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (ElementNullException e) {
			e.printStackTrace();
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
        houseSelect.setIcon(FontAwesome.HOME);
        
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
    		htb = new HouseTabSheet(shsystem, (String)houseSelect.getValue(), userView); 
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
		Label loggedUser = new Label(FontAwesome.USER.getHtml() + "<font size=\"4\"> " + ui.getUsername() + "</font>");
		loggedUser.setContentMode(ContentMode.HTML);
		loggedUser.setSizeUndefined();
		rightLayout.addComponent(loggedUser);
		rightLayout.setComponentAlignment(loggedUser, Alignment.MIDDLE_RIGHT);

		
		// ------ Button forgoing back to AdminView ------ //
		
		Button adminViewButton = new Button("Edit users", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ui.getNavigator().navigateTo(ui.ADMINVIEW);
				ui.getNavigator().removeView(ui.ADMINHOUSEVIEW);
			}
		});
		rightLayout.addComponent(adminViewButton);
       
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
        	htb = new HouseTabSheet(shsystem, (String)houseSelect.getValue(), userView);
			addComponent(htb);
    	}
        else{
        	Label nLabel = new Label("<font size=\"5\">You don't have rights to any houses! Please, contact system admin.</font>");
        	nLabel.setContentMode(ContentMode.HTML);
        	addComponent(nLabel);
        }
    }
      
    /**
     * This method is run when user enters the view
     */
    @Override
    public void enter(ViewChangeEvent event) {
        Notification.show("HI " + ui.getUsername() + "!");
       
    }
   /**
    * This should be run when server push happens. Updates every item in the view.
    */
    public void update(){
    	htb.update();
    }
 
}
