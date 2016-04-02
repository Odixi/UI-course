package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;

import exceptions.ElementNullException;
import exceptions.IDMatchNotFoundException;
import exceptions.IDTypeMismatch;
import model.UserAccountHandler;
import model.ViewHandlerNEW;
import model.items.SmartItem;
import model.HouseHandler;
import model.SmartModel;
import model.UIUpdater;
import model.AdminAccountHandler;

/**
 * The purpose of life?
 * @author Pilvi
 *
 */

public class SmartHSystemImp extends UnicastRemoteObject implements SmartHSystem {

	private static final long serialVersionUID = -4915065514625313433L;
	
	//Handlers
	private UserAccountHandler userHandler;
	private AdminAccountHandler adminHandler;
	private HouseHandler houseHandler;
	private SmartModel model;
	private UIUpdater updater;
	
	private ViewHandlerNEW viewHandler;

	
	public SmartHSystemImp() throws RemoteException, ElementNullException {
		super();
		
		//Crete object that handles activity regarding user accounts
		userHandler = new UserAccountHandler();
		
		//Crete object that handles activity regarding admin accounts
		adminHandler = new AdminAccountHandler();
		
		//Create house handler which handles the XML file representation of the house structures
		houseHandler = new HouseHandler();
		
		//House objects etc. are generated inside the SmartModel
		model = new SmartModel();
		
		//Create viewHandler that handles the xml files representing users' views.
		viewHandler = new ViewHandlerNEW(houseHandler);
		
		updater = UIUpdater.getUpdater(); //gets the only instance
		
		
	} //constructor


	//------------- FOR TESTING ----------------------
	
	public void testPrintConsole(String message) throws RemoteException {
		System.out.println("Message: " + message);

		model.printHouseStructure(model.getHouses());
		
		//Testing testing...
		//viewHandler.createDefaultView("ppp666");
		
	}
	
	// • • • • • • • • • • • UPDATING SYSTEM • • • • • • • • • • • • 
	
	@Override
	public UIUpdater getUpdater() throws RemoteException {
		return updater;
	}
	
	// • • • • • • • • • • • • BOTH USERS & ADMINS • • • • • • • • • • • • 
	
	public boolean passwordValid(String password) throws RemoteException {
		return userHandler.passwordValid(password);
	}

	public boolean usernameValid(String username) throws RemoteException {
		return userHandler.usernameValid(username);
	}
	
	// • • • • • • • • • • • • USERS • • • • • • • • • • • • 

	public void newUser(String username, String password) throws RemoteException {
		userHandler.createUser(username, password);
	}
	
	public void deleteUser(String username) throws RemoteException {
		userHandler.removeUser(username);
	}
	
	public Hashtable<String, String> getUsers() throws RemoteException {
		return userHandler.getUserList();
	}
	
		// ‹›‹›‹›‹›‹›‹›‹›‹›‹› USERNAME ‹›‹›‹›‹›‹›‹›‹›‹›‹›
	
	public boolean usernameAvailable(String username) throws RemoteException {
		return userHandler.usernameInUse(username);
	}

	public boolean changeUsername(String oldUsername, String newUsername) throws RemoteException {
		return userHandler.changeUsername(oldUsername, newUsername); 
	}
	
	public ArrayList<String> getUsernames() throws RemoteException {
		return userHandler.getUsernameList();
	}
	
		// ‹›‹›‹›‹›‹›‹›‹›‹›‹› USER ID ‹›‹›‹›‹›‹›‹›‹›‹›‹›	
	
	public String getUserID(String username) throws RemoteException {
		return userHandler.getUserID(username);
	}
	
		// ‹›‹›‹›‹›‹›‹›‹›‹›‹› USER PASSWORD ‹›‹›‹›‹›‹›‹›‹›‹›‹›
	
	public boolean changePassword(String username, String oldPassword, String newPassword) throws RemoteException {
		return userHandler.changePassword(username, oldPassword, newPassword);
	}
		
		// ‹›‹›‹›‹›‹›‹›‹›‹›‹› USER LOGIN ‹›‹›‹›‹›‹›‹›‹›‹›‹›

	public boolean userLogin(String username, String password) throws RemoteException {
		//Returns true if the password is a match
		return userHandler.passwordMatch(username, password);
	}

		// ‹›‹›‹›‹›‹›‹›‹›‹›‹› USER VIEW ‹›‹›‹›‹›‹›‹›‹›‹›‹›
	
	public Hashtable<String, Boolean> getUserView(String userID) throws RemoteException, ElementNullException {
		return viewHandler.getUserView(userID);
	}

	public boolean setUserView(String userID, Hashtable<String, Boolean> userview) throws RemoteException, ElementNullException {
		return viewHandler.setUserView(userID, userview);
	}

	// • • • • • • • • • • • • ADMINS • • • • • • • • • • • • 

	public void newAdmin(String username, String password) throws RemoteException {
		adminHandler.createUser(username, password);
	}

	public void deleteAdmin(String adminname) throws RemoteException {
		adminHandler.removeUser(adminname);
	}

	public boolean adminLogin(String adminname, String password) throws RemoteException {
		return adminHandler.passwordMatch(adminname, password);
	}

	public ArrayList<String> getAdminnames() throws RemoteException {
		return adminHandler.getUsernameList();
	}

	public boolean changePasswordAdmin(String username, String newPassword) throws RemoteException {
		return userHandler.changePasswordAdmin(username, newPassword);
	}
	
	// • • • • • • • • • • • •  HOUSES • • • • • • • • • • • • 

	@Override
	public Hashtable<String, String> getHouseNames() throws RemoteException {
		return  houseHandler.getHouseNameList();
	}

	@Override
	public Hashtable<String, String> getRoomNames(String houseID) throws RemoteException, ElementNullException {
		return houseHandler.getRoomNames(houseID);
	}

	public Hashtable<String, String> getItems(String houseID, String roomID) throws RemoteException, ElementNullException {
		return houseHandler.getItemNames(houseID, roomID);
	}
	
	// • • • • • • • • • • • •  VIEWS  • • • • • • • • • • • • 
	
	/*
	 * (non-Javadoc)
	 * @see server.SmartHSystem#getSmartItem(java.lang.String)
	 * 
	 * Basically all of the methods to be used in the following methods throw an exception or a few.
	 * I'm torn between two options:
	 * 1) Handle the exception/s in here.
	 * 2) Throw the exception/s meaning that they need to be handled in the UI.
	 * 
	 */
	
	// ------------ getSmartItem() - Two ways --------------------------
	@Override
	public SmartItem getSmartItem(String itemID) throws RemoteException, IDMatchNotFoundException {
		return model.getSmartItem(itemID);
	}
	
	@Override
	public SmartItem getSmartItem(String houseID, String roomID, String itemID) throws RemoteException, IDMatchNotFoundException {
		return model.getSmartItem(houseID, roomID, itemID);
	}


	@Override
	public void turnLightOn(String houseID, String roomID, String itemID) throws RemoteException, IDMatchNotFoundException, IDTypeMismatch {
		model.turnLightOn(houseID, roomID, itemID);
	}


	@Override
	public void turnLightOff(String houseID, String roomID, String itemID) throws RemoteException, IDMatchNotFoundException, IDTypeMismatch {
		model.turnLightOff(houseID, roomID, itemID);
	}


	@Override
	public void turnApplianceOn(String houseID, String roomID, String itemID) throws RemoteException, IDMatchNotFoundException, IDTypeMismatch {
		model.turnApplianceOn(houseID, roomID, itemID);
	}
	
	
	@Override
	public void turnApplianceOff(String houseID, String roomID, String itemID) throws RemoteException, IDMatchNotFoundException, IDTypeMismatch {
		model.turnApplianceOff(houseID, roomID, itemID);
	}


	@Override
	public boolean setAudioVolume(String houseID, String roomID, String itemID, int volume) throws RemoteException, IDMatchNotFoundException, IDTypeMismatch {
		return model.setAudioVolume(houseID, roomID, itemID, volume);
	}


	@Override
	public boolean setControllerValue(String houseID, String roomID, String itemID, double value) throws RemoteException, IDTypeMismatch, IDMatchNotFoundException {
		return model.setControllerValue(houseID, roomID, itemID, value);
	}


	// • • • • • • • • • • • •  • • • • • • • • • • • • 
	
	
	// • • • • • • • • • • • •  • • • • • • • • • • • • 
}
