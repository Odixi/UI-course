package view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class UserView extends VerticalLayout implements View{

	public UserView(SmartUI ui){
		
		//Nämä vaan testiä varten. Voi poistaa.
        Button button = new Button("Go to LoginView",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ui.getNavigator().navigateTo(ui.LOGINVIEW);
            }
        });
        addComponent(button);
        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        Label text = new Label("UserView");
        addComponent(text);
        setComponentAlignment(text, Alignment.TOP_LEFT);
	}
		
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		Notification.show("userview");
		
	}
	

}
