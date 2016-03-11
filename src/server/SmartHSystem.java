package server;

import java.rmi.*;

public interface SmartHSystem extends Remote {


	// • • • • • • • • USERS • • • • • • • • •
	
	public void newUser(String username, String password) throws RemoteException;
	
	public void deleteUser(String username) throws RemoteException;
	
	public void login(String username, String password) throws RemoteException;
	
	
	
}
