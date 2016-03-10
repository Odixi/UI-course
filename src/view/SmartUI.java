package view;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;


@Theme("valo")
public class SmartUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = SmartUI.class)
	public static class Servlet extends VaadinServlet {
	}		
		private Navigator navigator;
		
		//Näkymien nimet:
		protected static final String LOGINVIEW = "loginview";
		protected static final String ADMINVIEW = "adminview";
		protected static final String USERVIEW = "userview";
		protected static final String ADMINLOGINVIEW = "adminloginview";
		
		@Override
		protected void init(VaadinRequest request) {
			
			navigator = new Navigator(this, this);
			
			navigator.addView(LOGINVIEW, new LoginView(this));
			navigator.addView(USERVIEW, new UserView(this));
			navigator.addView(ADMINVIEW, new AdminView(this));
			navigator.addView(ADMINLOGINVIEW, new AdminLoginView(this));
			
			navigator.navigateTo(LOGINVIEW);
		}
	
		public Navigator getNavigator() {
			return navigator;
		}
	
}
