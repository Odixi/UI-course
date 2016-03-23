package view;

import java.rmi.RemoteException;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
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
	
	public ChangePasswordPopupContent(SmartHSystem shsystem, String username){
		
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
				if (!nps1.getValue().equals(nps2.getValue())){
					Notification.show("The passwords don't match!");
				}
				else{
					if (nps1.getValue().length() >= 8 && nps1.getValue().length() <= 25){
						try {
							if (shsystem.changePassword(username, ops.getValue(), nps1.getValue())){
								Notification.show("Password changed");
								pv.setPopupVisible(false);
								nps1.setValue("");
								nps2.setValue("");
								ops.setValue("");
							}
							else{
								Notification.show("Old password is wrong!");
							}
						} catch (RemoteException e) {
						e.printStackTrace();
						}
					}
					else{
						Notification.show("New password must be 8-25 characters long!");
					}
				}
			}
		});
		horLayout.addComponent(save);
		
		Button cancel = new Button("Cancel", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				pv.setPopupVisible(false);
				nps1.setValue("");
				nps2.setValue("");
				ops.setValue("");
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
