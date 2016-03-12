package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import com.google.gwt.cell.client.ButtonCellBase.DefaultAppearance.Style;
import com.google.gwt.user.client.ui.ClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import server.SmartHSystem;
//import org.jasypt.util.text.BasicTextEncryptor;

import com.vaadin.ui.Button.ClickEvent;


public class LoginView extends VerticalLayout implements View{

	//Attributes
	private SmartHSystem shsystem;
	//private BasicTextEncryptor cryptor;
	

	//CONSTRUCTOR
	public LoginView(SmartUI ui, SmartHSystem shsystem){
		super();
		
		//For RMI calls
		this.shsystem = shsystem;
		
		//For encrypting and decrypting passwords	//TODO Doesn't find the class
		//cryptor = new BasicTextEncryptor();
		
		setHeight(ui.getCurrent().getPage().getBrowserWindowHeight()*0.6f, Unit.PIXELS);
		
		// ---------- Navigaatio nappulat - Väliaikaiset ---------- //
		
        Button userButton = new Button("Go to UserView",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ui.getNavigator().navigateTo(ui.USERVIEW);
            }
        });
        Button adminButton = new Button("Go to AdminView",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ui.getNavigator().navigateTo(ui.ADMINVIEW);
            }
        });
        addComponent(userButton);
        setComponentAlignment(userButton, Alignment.MIDDLE_CENTER);
        addComponent(adminButton);
        setComponentAlignment(adminButton, Alignment.MIDDLE_CENTER);
        
        
     // ---------- Navigaatio nappulat /END---------- //
		
        
     // ----- Login as admin button ----- //   
		Button toAdminLogin = new Button("Login as admin",
				new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ui.getNavigator().navigateTo(ui.ADMINLOGINVIEW);
			}
		});
		addComponent(toAdminLogin);
		setComponentAlignment(toAdminLogin, Alignment.TOP_LEFT);
        
        
     // ----- Käyttäjän valinta ----- //   
        ComboBox userSelect = new ComboBox("Select user");
        userSelect.setInputPrompt("No user selected");
        userSelect.setFilteringMode(FilteringMode.CONTAINS);
//        userSelect.setImmediate(true);
        userSelect.setTextInputAllowed(false);
        userSelect.setNullSelectionAllowed(false);
        
        	//Testiksi lista
        	//TODO Käyttäjien hakeminen serverin tiedostoista
        //String[] kayt = {"Ville", "Pilvi", "Jenni", "Elmo"};
        
        ArrayList<String> kayt = new ArrayList<String>();
		try {
			kayt = shsystem.getUsernames();
		} catch (RemoteException e) {e.printStackTrace();}

        userSelect.addItems(kayt);
        addComponent(userSelect);
        setComponentAlignment(userSelect, Alignment.MIDDLE_CENTER);
        
      // ----- Salasana ----- //  
        PasswordField passwordField = new PasswordField("Password");
        addComponent(passwordField);
        setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);
        
      // ----- Login nappi ----- //
        Button loginButton = new Button("Login",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	// if username field is empty
            	if (userSelect.getValue() == null){
            		Notification.show("Select a user first");
            		return;
            		
            		//Notification.show((String)userSelect.getValue());
                    //TODO Logic for pressing login
                	
                	/*
                	 * Esim.
                	 * model.login(userSelect.getValue(), passwordField.getValue()); //heittää virheen jos ei täsmää
                	 * Vai mitenköhän se kannattas tehdä?
                	 */
            		
            	} else if(userSelect.getValue() != null) { //User selected
            		
            		if(passwordField.getValue() == null){ //Password not given
            			Notification.show("Input the password please.");
                		return;
                		
            		} else if (passwordField.getValue() != null){
            			boolean match = false;
            			
            			//Encrypt the password
            			//String securePassword = cryptor.encrypt(passwordField.getValue());
            			
            			try {
							match = shsystem.login( userSelect.getValue().toString(), passwordField.getValue().trim());
							//TODO For testing
							System.out.println("Username: " + userSelect.getValue().toString());
							System.out.println("Password written: " + passwordField.getValue().trim());
							
            			} catch (RemoteException e) { e.printStackTrace();}
            			
            			if(match == true){
            				//Let the user in
            				//TODO Open the user-specific view
            				ui.getNavigator().navigateTo(ui.USERVIEW);
            				
            			} else if(match == false) {
            				Notification.show("Incorrect password. Please try again.");
                    		return;
            			}
            		}
            	}
            } 
        }); //login-button listener
        
        addComponent(loginButton);
        setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);
        
	} //constructor
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
	
}
