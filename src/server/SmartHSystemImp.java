package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.UserAccountHandler;

/**
 * The purpose of life?
 * @author Pilvi
 *
 */

public class SmartHSystemImp extends UnicastRemoteObject implements SmartHSystem {

	private UserAccountHandler userHandler;
	
	
	public SmartHSystemImp() throws RemoteException {
		super();
		
		//TODO
	
		//Create user account handler
		userHandler = new UserAccountHandler();
		
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

	public void login(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<String> getUsernames() throws RemoteException {
		return userHandler.getUsernameList();
	}


	
	
	
	
	
}
