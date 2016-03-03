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
	protected static final String ADMINNVIEW = "adminview";
	protected static final String USERVIEW = "userview";
	
	@Override
	protected void init(VaadinRequest request) {
		
		navigator = new Navigator(this, this);
		
		
	}
	
}
