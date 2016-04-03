package view;

import java.rmi.RemoteException;

import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import exceptions.IDMatchNotFoundException;
import server.SmartHSystem;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
/**
 * A login view for andmins to login.
 * @author Ville
 *
 */
public class AdminLoginView extends VerticalLayout implements View{
	
	private SmartHSystem shsystem;
	private SmartUI ui;
	
	private TextField usernameField;
	private PasswordField passwordField;
	
	/**
	 * Construktor to build the view.
	 * @param ui The UI, in which the view is build to
	 * @param shsystem SmartHSystem for RMI calls to interact with the backend
	 */
	public AdminLoginView(SmartUI ui, SmartHSystem shsystem){
		
		this.shsystem = shsystem;
		this.ui = ui;
		
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
			
			usernameField = new TextField("Admin username");
			addComponent(usernameField);
			setComponentAlignment(usernameField, Alignment.MIDDLE_CENTER);
			
	        
	      // ----- Salasana ----- //  
        passwordField = new PasswordField("Password");
        addComponent(passwordField);
        setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);
        
        
      // ----- Login nappi ----- //
        Button loginButton = new Button("Login",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	login();
            }
        });
        addComponent(loginButton);
        setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);
        
        // ----- Shortcut listerner: enter -> login ------ //
        
        this.addShortcutListener(new ShortcutListener("login", KeyCode.ENTER, null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				login();		
			}
		});
        
	}
	
	private void login(){
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
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (IDMatchNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		ui.getNavigator().navigateTo(ui.ADMINVIEW);
    	}
    	else{
    		Notification.show("Wrong username/password!");
    	}
    } // login
	
	/**
	 * This method is run when user enters this view
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	

}
