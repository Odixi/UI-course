package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;

import model.UserAccountHandler;
import model.ViewHandler;
import model.HouseHandler;
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
	private ViewHandler viewHandler;
	
	public SmartHSystemImp() throws RemoteException {
		super();
		
		//TODO
	
		//Crete object that handles activity regarding user accounts
		userHandler = new UserAccountHandler();
		//Crete object that handles activity regarding admin accounts
		adminHandler = new AdminAccountHandler();
		//Create house handler
		houseHandler = new HouseHandler();
		//ViewHandler
		viewHandler = new ViewHandler(houseHandler);
		
	} //constructor


	//------------- FOR TESTING ----------------------
	
	public void testPrintConsole(String message) throws RemoteException {
		System.out.println("Message: " + message);
		
		Hashtable<String, String> roomnames = getRoomNames("h001");
		
		for(String key : roomnames.keySet()){
			System.out.println("Room: " + roomnames.get(key) + " ID: " + key);
		}

		/*
		Hashtable<String, String> housenames = getHouseNames();
		
		System.out.println("Test new getHousenames-method:");
		
		for(String key : housenames.keySet()){
			System.out.println("House: " + housenames.get(key) + " ID: " + key);
		}
		*/
		/*
		System.out.println("Houses length: " + houses.size());
		for(String house : houses){
			System.out.println(house);
		}
		
		System.out.println("Let's test username & adminname fetching");
		ArrayList<String> admins = getAdminnames();
		for(String name : admins){
			System.out.println("Admin name: " + name);
		}

		ArrayList<String> users = getUsernames();
		for(String name : users){
			System.out.println("Username: " + name);
		}*/
		
		//TODO Remove, for testing only
		viewHandler.createDefaultView("ppp666");
		
		Hashtable<String, Boolean> pilviView = viewHandler.getUserView("ppp666");
		
		System.out.println(pilviView);
		
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
	
	public Hashtable<String, Boolean> getUserView(String userID) throws RemoteException {
		return viewHandler.getUserView(userID);
	}

	public void setUserView(String userID, Hashtable<String, Boolean> userview) throws RemoteException {
		viewHandler.setUserView(userID, userview);
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
	public Hashtable<String, String> getRoomNames(String houseID) throws RemoteException {
		return houseHandler.getRoomNames(houseID);
	}

	public Hashtable<String, String> getItems(String houseID, String roomID) throws RemoteException {
		return houseHandler.getItemNames(houseID, roomID);
	//	return null;
	}
	
	// • • • • • • • • • • • •  • • • • • • • • • • • • 
	
	
	// • • • • • • • • • • • •  • • • • • • • • • • • • 
}
