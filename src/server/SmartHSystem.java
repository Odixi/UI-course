package server;

import java.rmi.*;
import java.util.ArrayList;

public interface SmartHSystem extends Remote {

	// --------- FOR TESTING ------------------
	
	public void testPrintConsole(String message) throws RemoteException;
	
	// • • • • • • • • USERS • • • • • • • • •
	
	public void newUser(String username, String password) throws RemoteException;
	
	public void deleteUser(String username) throws RemoteException;
	
	public void login(String username, String password) throws RemoteException;
	
	public ArrayList<String> getUsernames() throws RemoteException;
	
}
