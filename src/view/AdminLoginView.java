package view;

import java.rmi.RemoteException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import server.SmartHSystem;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;

public class AdminLoginView extends VerticalLayout implements View{

	public AdminLoginView(SmartUI ui, SmartHSystem shsystem){
		
		setMargin(true);
		setSpacing(true);
		
	     // ----- Login as user button ----- //   
			Button toAdminLogin = new Button("Login as user",
					new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					ui.getNavigator().navigateTo(ui.LOGINVIEW);
				}
			});
			addComponent(toAdminLogin);
			setComponentAlignment(toAdminLogin, Alignment.TOP_RIGHT);
			
		// ----- Login as admin -label ----- //
			
			Label desc = new Label("<font size=\"5\">Login as admin</font>");
			desc.setContentMode(ContentMode.HTML);
			desc.setSizeUndefined();
			addComponent(desc);
			setComponentAlignment(desc, Alignment.MIDDLE_CENTER);
			
			
		// ----- Username field ----- //
			
			TextField usernameField = new TextField("Admin username");
			addComponent(usernameField);
			setComponentAlignment(usernameField, Alignment.MIDDLE_CENTER);
			
	        
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
            	if (usernameField.getValue() == "" || usernameField.getValue() == null){
            		Notification.show("Input a username please.");
            		return;
            	}
            	if (passwordField.getValue() == "" || passwordField.getValue() == null){
            		Notification.show("Input a password please");
            		return;
            	}
            	boolean match = false;
            	try{
            		match = shsystem.adminLogin(usernameField.getValue(), passwordField.getValue()); // TODO tuleeko adminille joku oma login metodi?
            	} catch (RemoteException e){e.printStackTrace();}
            	if (match){
            		try {
						ui.setUser(shsystem.getUserID(usernameField.getValue()), usernameField.getValue());
					} catch (RemoteException e) {e.printStackTrace();}
            		ui.getNavigator().navigateTo(ui.ADMINVIEW);
            	}
            	else{
            		Notification.show("Wrong username/password!");
            	}
            }
        });
        addComponent(loginButton);
        setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);
        
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	

}
