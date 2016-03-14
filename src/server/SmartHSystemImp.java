package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.UserAccountHandler;
import model.HouseHandler;
import model.AdminAccountHandler;

/**
 * The purpose of life?
 * @author Pilvi
 *
 */

public class SmartHSystemImp extends UnicastRemoteObject implements SmartHSystem {

	private UserAccountHandler userHandler;
	private AdminAccountHandler adminHandler;
	private HouseHandler houseHandler;
	
	public SmartHSystemImp() throws RemoteException {
		super();
		
		//TODO
	
		//Crete object that handles activity regarding user accounts
		userHandler = new UserAccountHandler();
		//Crete object that handles activity regarding admin accounts
		adminHandler = new AdminAccountHandler();
		//Create house handler
		houseHandler = new HouseHandler();
		
	} //constructor


	//------------- FOR TESTING ----------------------
	
	public void testPrintConsole(String message) throws RemoteException {
		System.out.println("Message: " + message);
		
		ArrayList<String> houses = getHouseNames();
		
		
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
		}
		
	}
	
	// • • • • • • • • USERS • • • • • • • • •
	
	public void newUser(String username, String password) throws RemoteException {
		userHandler.createUser(username, password);
	}

	public void deleteUser(String username) throws RemoteException {
		userHandler.removeUser(username);
	}

	public boolean userLogin(String username, String password) throws RemoteException {
		//Returns true if the password is a match
		return userHandler.passwordMatch(username, password);
	}

	public ArrayList<String> getUsernames() throws RemoteException {
		return userHandler.getUsernameList();
	}

	
	// • • • • • • • • ADMINS • • • • • • • • •

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

	
	// • • • • • • • • HOUSES • • • • • • • • •

	@Override
	public ArrayList<String> getHouseNames() throws RemoteException {
//		return  houseHandler.getHouseNameList();
		ArrayList<String> list = new ArrayList<String>();
		list.add("Talo1");
		list.add("Talo2");
		return list;
	}

	@Override
	public ArrayList<String> getRoomNames(String houseName) throws RemoteException {
//		return houseHandler.getRoomNames(houseName);
		ArrayList<String> list = new ArrayList<String>();
		list.add("Huone1");
		list.add("Huone2");
		System.out.println(list.toString());
		return list;
	}

	@Override
	public ArrayList<String> getItems(String houseName, String roomName) throws RemoteException {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Esine1");
		list.add("Esine2");
		return list;
	//	return null;
	}


	
	
	
	
	
}
