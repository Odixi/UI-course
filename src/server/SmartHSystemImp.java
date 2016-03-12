package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.UserAccountHandler;
import model.HouseHandler;

/**
 * The purpose of life?
 * @author Pilvi
 *
 */

public class SmartHSystemImp extends UnicastRemoteObject implements SmartHSystem {

	private UserAccountHandler userHandler;
	private HouseHandler houseHandler;
	
	public SmartHSystemImp() throws RemoteException {
		super();
		
		//TODO
	
		//Create user account handler
		userHandler = new UserAccountHandler();
		//Create house handler
		houseHandler = new HouseHandler();
		
	} //constructor


	//------------- FOR TESTING ----------------------
	
	public void testPrintConsole(String message) throws RemoteException {
		System.out.println("Message: " + message);
	}
	
	// • • • • • • • • USERS • • • • • • • • •
	
	public void newUser(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
	}

	public void deleteUser(String username) throws RemoteException {
		// TODO Auto-generated method stub
	}

	public boolean login(String username, String password) throws RemoteException {
		//Returns true if the password is a match
		return userHandler.passwordMatch(username, password);
	}

	public ArrayList<String> getUsernames() throws RemoteException {
		return userHandler.getUsernameList();
	}

	
	// • • • • • • • • HOUSES • • • • • • • • •

	@Override
	public String[] getHouses() throws RemoteException {
		//TODO Not the pretties possible way to do this, but oh well...
		return houseHandler.getHouseNameList().toArray(new String[0]);
	}

	@Override
	public String[] getRooms(String houseName) throws RemoteException {
		return houseHandler.getRooms(houseName).toArray(new String[0]);
	}


	@Override
	public String[] getItems(String houseName, String roomName) throws RemoteException {
		
		return null;
	}


	
	
	
	
	
}
