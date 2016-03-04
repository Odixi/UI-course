package view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class LoginView extends VerticalLayout implements View{

	public LoginView(SmartUI ui){
		
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
                ui.getNavigator().navigateTo(ui.USERVIEW);
            }
        });
        addComponent(userButton);
        setComponentAlignment(userButton, Alignment.MIDDLE_CENTER);
        addComponent(adminButton);
        setComponentAlignment(adminButton, Alignment.MIDDLE_CENTER);
			
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
	
}
