package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.google.gwt.cell.client.ButtonCellBase.DefaultAppearance.Style;
import com.google.gwt.user.client.ui.ClickListener;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import server.SmartHSystem;
//import org.jasypt.util.text.BasicTextEncryptor;

import com.vaadin.ui.Button.ClickEvent;

/**
 *  Initial view for users to login
 * @author Ville
 *
 */
public class LoginView extends VerticalLayout implements View{

	//Attributes
	private SmartHSystem shsystem;
	private Panel loginPanel;	
	private ComboBox userSelect;
	private PasswordField passwordField;
	private SmartUI ui;
	

	/**
	 * 
	 * @param ui
	 * @param shsystem for RMI calls
	 */
	public LoginView(SmartUI ui, SmartHSystem shsystem){
		super();
		this.ui = ui;
		
		//For RMI calls
		this.shsystem = shsystem;
		
		//For encrypting and decrypting passwords
		//cryptor = new BasicTextEncryptor();
		
		setMargin(true);
		setSpacing(true);
		
	// ------ SmartHome label ------ //
	
	HorizontalLayout topRow = new HorizontalLayout();
	addComponent(topRow);
		
	Label smartHomeLabel = new Label("<font size=\"8\">Smart Home</font>");
	smartHomeLabel.setContentMode(ContentMode.HTML);
    topRow.addComponent(smartHomeLabel);   
	
     // ----- Login as admin button ----- //   
		Button toAdminLogin = new Button("Login as admin",
				new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ui.getNavigator().navigateTo(ui.ADMINLOGINVIEW);
			}
		});
		topRow.addComponent(toAdminLogin);
		topRow.setComponentAlignment(toAdminLogin, Alignment.TOP_RIGHT);
		topRow.setSizeFull();
		
	// ---- desc. ----- //
		
	Label desc = new Label("Remote house management"); 
	addComponent(desc);
          
    	// -------- Login Panel -------- //
    
    	loginPanel = new Panel("Login");
    	addComponent(loginPanel);
    	VerticalLayout panelLayout = new VerticalLayout();
    	panelLayout.setSpacing(true);
    	panelLayout.setMargin(true);
    	loginPanel.setWidth(300f, Unit.PIXELS);
    
    	// ----- Käyttäjän valinta ----- // 
    	
        userSelect = new ComboBox("Select user");
        userSelect.setInputPrompt("No user selected");
        userSelect.setFilteringMode(FilteringMode.CONTAINS);
        userSelect.setImmediate(true);
        userSelect.setTextInputAllowed(false);
        userSelect.setNullSelectionAllowed(false);
        
        

        Hashtable<String, String> userList = new Hashtable<String, String>();
        
		try {

			//Fetch usernames & IDs from the server
			userList = shsystem.getUsers();
			
		} catch (RemoteException e) {e.printStackTrace();}

		//Set userIDs as items to combobox and add usernames as caption (what is actually shown in combobox)
		for(String userID : userList.keySet()){
			userSelect.addItem(userID);
			userSelect.setItemCaption( userID, userList.get(userID) );
		}
		
        panelLayout.addComponent(userSelect);
        userSelect.setSizeFull();
        
      // ----- Salasana ----- //  
        passwordField = new PasswordField("Password");
        panelLayout.addComponent(passwordField);
        passwordField.setSizeFull();
        
      // ----- Login nappi ----- //
        Button loginButton = new Button("Login",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	login();
            } 
        }); //login-button listener
        
        panelLayout.addComponent(loginButton);
        loginPanel.setContent(panelLayout);
        setComponentAlignment(loginPanel, Alignment.TOP_CENTER);
        
        // ------ Shorcut listener for enter -> login ------ //
        
        this.addShortcutListener(new ShortcutListener("login", KeyCode.ENTER, null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				login();
				
			}
		});
        
//		// ---------- Navigaatio nappulat - Väliaikaiset ---------- //
//		
//        Button userButton = new Button("Go to UserView",
//                new Button.ClickListener() {
//            @Override
//            public void buttonClick(ClickEvent event) {
//            	ui.setUser(getSelectedUserID(), getSelectedUsername());
//            	ui.getNavigator().addView(ui.USERVIEW, new UserView(ui, shsystem));
//                ui.getNavigator().navigateTo(ui.USERVIEW);
//            }
//        });
//        Button adminButton = new Button("Go to AdminView",
//                new Button.ClickListener() {
//            @Override
//            public void buttonClick(ClickEvent event) {
//                ui.getNavigator().navigateTo(ui.ADMINVIEW);
//            }
//        });
//        addComponent(userButton);
//        setComponentAlignment(userButton, Alignment.MIDDLE_LEFT);
//        addComponent(adminButton);
//        setComponentAlignment(adminButton, Alignment.MIDDLE_LEFT);
//        
//        
//     // ---------- Navigaatio nappulat /END---------- //
        
	} //constructor
	
	//GETTERS FOR SELECTED USER AND THEIR ID
	/**
	 * Returns the username of the currently selected user
	 * @return
	 */
	public String getSelectedUsername(){
		
		if(userSelect.getValue() == null){
			return null;
		}
		return userSelect.getItemCaption( userSelect.getValue().toString() );
	}
	
	/**
	 * Returns the userID of the currently selected user
	 * @return
	 */
	public String getSelectedUserID(){
		
		if(userSelect.getValue() == null){
			return null;
		}
		return userSelect.getValue().toString();
		
	}
	
	/*
	 * This is run when users try to login
	 */
	private void login(){
    	// if username field is empty
    	if (userSelect.getValue() == null){
    		Notification.show("Select a user first");
    		return;
 		
    	} else if(userSelect.getValue() != null) { //User selected
    		
    		if(passwordField.getValue() == null || passwordField.isEmpty()){ //Password not given
    			Notification.show("Input the password please.");
        		return;
        		
    		} else if (passwordField.getValue() != null){
    			boolean match = false;
    			
    			//Encrypt the password
    			//String securePassword = cryptor.encrypt(passwordField.getValue());
    			
    			try {
					match = shsystem.userLogin( getSelectedUsername(), passwordField.getValue().trim());
					
    			} catch (RemoteException e) { e.printStackTrace();}
    			
    			if(match == true){
    				ui.setUser(getSelectedUserID(), getSelectedUsername());
    				ui.getNavigator().addView(ui.USERVIEW, new UserView(ui, shsystem));
    				ui.getNavigator().navigateTo(ui.USERVIEW);
    				
    			} else if(match == false) {
    				Notification.show("Incorrect password. Please try again.");
            		return;
    			}
    		}
    	}
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}
	
}
