package view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;


@Theme("valo")
public class SmartUI extends UI {

	protected Navigator navigator;
	
	//Näkymien nimet:
	protected static final String LOGINVIEW = "loginview";
	protected static final String ADMINVIEW = "adminview";
	protected static final String USERVIEW = "userview";
	
	@Override
	protected void init(VaadinRequest request) {
		
		navigator = new Navigator(this, this);
		
		navigator.addView(LOGINVIEW, new LoginView());
		navigator.addView(USERVIEW, new UserView());
		navigator.addView(ADMINVIEW, new AdminView());
		
		navigator.navigateTo(LOGINVIEW);
	}
	
}
