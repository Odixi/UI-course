package view;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import server.SmartHSystem;

/**
 * Popup view for userview to change password
 * @author Ville
 *
 */
public class ChangePasswordPopupContent implements PopupView.Content {
	
	VerticalLayout layout;
	PopupView pv;
	
	public ChangePasswordPopupContent(SmartHSystem shsystem){
		
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		HorizontalLayout horLayout = new HorizontalLayout();
		
		PasswordField nps1 = new PasswordField("New Password");
		PasswordField nps2 = new PasswordField("New password again");
		PasswordField ops = new PasswordField("Old password");
		
		layout.addComponent(nps1);
		layout.addComponent(nps2);
		layout.addComponent(ops);
		
		layout.addComponent(horLayout);
		
		Button save = new Button("Change password", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
			}
		});
		horLayout.addComponent(save);
		
		Button cancel = new Button("Cancel", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				pv.setPopupVisible(false);
			}
		});
		
		horLayout.addComponent(cancel);
		
	}

	@Override
	public String getMinimizedValueAsHTML() {
		return "Change password";
	}

	@Override
	public Component getPopupComponent() {
		return layout;
	}
	
	/**
	 * Method to get the PopupView that this object is part of.
	 */
	public void setPopupView(PopupView pv){
		this.pv = pv;
	}

}
