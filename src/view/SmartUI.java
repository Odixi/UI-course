package view;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import server.SmartHSystem;

@Theme("valo")
public class SmartUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = SmartUI.class)
	public static class Servlet extends VaadinServlet {}
	
	private Navigator navigator;
	
	//Näkymien nimet:
	protected static final String LOGINVIEW = "loginview";
	protected static final String ADMINVIEW = "adminview";
	protected static final String USERVIEW = "userview";
	protected static final String ADMINLOGINVIEW = "adminloginview";
	
	//RMI
	private Registry registry;
	private SmartHSystem shsystem;
	
	@Override
	protected void init(VaadinRequest request) {
		
		formRMIConnection();
		
		navigator = new Navigator(this, this);
		
		navigator.addView(LOGINVIEW, new LoginView(this, shsystem));
		navigator.addView(USERVIEW, new UserView(this, shsystem));
		navigator.addView(ADMINVIEW, new AdminView(this, shsystem));
		navigator.addView(ADMINLOGINVIEW, new AdminLoginView(this, shsystem));
		
		navigator.navigateTo(LOGINVIEW);
		

	}//init

	public Navigator getNavigator() {
		return navigator;
	}

	// • • • FORMING RMI-CONNECTION • • • 
	private void formRMIConnection() {
		
		String RMIosoite ="shsystem";	
		String osoite = "localhost";
		
    	try {
    		registry = LocateRegistry.getRegistry(osoite, 2020);
    		shsystem = (SmartHSystem) registry.lookup(RMIosoite); 	
    	} catch (Exception e){
    		System.out.println(e);
    	}
		
    	// --------- TESTING! ------------
    	try {
			shsystem.testPrintConsole("Testing, testing... from SmartUI");
		} catch (RemoteException e) {	e.printStackTrace();}
	}	
}
