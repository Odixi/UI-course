package view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;

public class AdminLoginView extends VerticalLayout implements View{

	public AdminLoginView(SmartUI ui){
		
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
            	if (usernameField.getValue() == null){
            		Notification.show("Select a user first");
            		return;
            	}
            	Notification.show((String)usernameField.getValue());
                //TODO Logic for pressing login
            	
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
