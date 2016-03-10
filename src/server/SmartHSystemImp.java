package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import model.UserAccountHandler;

/**
 * The purpose of 
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


	
	// • • • • • • • • USERS • • • • • • • • •
	
	public void newUser(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
	}

	public void deleteUser(String username) throws RemoteException {
		// TODO Auto-generated method stub
	}
	
	
	
	
	
}
