package server;

import java.rmi.*;
import java.util.ArrayList;
import java.util.Hashtable;

public interface SmartHSystem extends Remote {

	// --------- FOR TESTING ------------------
	
	public void testPrintConsole(String message) throws RemoteException;
	
// • • • • • • • • • • • • BOTH USERS & ADMINS • • • • • • • • • • • • 
	
	public boolean passwordValid(String password) throws RemoteException;
	
	public boolean usernameValid(String username) throws RemoteException;
	
// • • • • • • • • USERS • • • • • • • • •
	
	public void newUser(String username, String password) throws RemoteException;
	
	public void deleteUser(String username) throws RemoteException;

		// ‹›‹›‹›‹›‹›‹›‹›‹›‹› USERNAME ‹›‹›‹›‹›‹›‹›‹›‹›‹›		
	
	public boolean usernameAvailable(String username) throws RemoteException;
	
	public boolean changeUsername(String oldUsername, String newUsername) throws RemoteException;
	
	public ArrayList<String> getUsernames() throws RemoteException;
	
		// ‹›‹›‹›‹›‹›‹›‹›‹›‹› USER ID ‹›‹›‹›‹›‹›‹›‹›‹›‹›		
	
	public String getUserID(String username) throws RemoteException;
	
		// ‹›‹›‹›‹›‹›‹›‹›‹›‹› USER PASSWORD ‹›‹›‹›‹›‹›‹›‹›‹›‹›	
	
	public boolean changePassword(String username, String oldPassword, String newPassword) throws RemoteException;

		// ‹›‹›‹›‹›‹›‹›‹›‹›‹› USER LOGIN ‹›‹›‹›‹›‹›‹›‹›‹›‹›	

	public boolean userLogin(String username, String password) throws RemoteException;

		// ‹›‹›‹›‹›‹›‹›‹›‹›‹› USER VIEW ‹›‹›‹›‹›‹›‹›‹›‹›‹›
		
	public Hashtable<String, Boolean> getUserView(String userName) throws RemoteException;
	
	public void setUserView(String userID, Hashtable<String, Boolean> userview) throws RemoteException;
	
// • • • • • • • • ADMINS • • • • • • • • •

	public void newAdmin(String adminname, String password) throws RemoteException;
	
	public void deleteAdmin(String adminname) throws RemoteException;
	
	/* >>>>>>>> This is probably going the only one we'll actually need.
	 			The others available... well just because I can make them available *cheeky grin* */
	public boolean adminLogin(String adminname, String password) throws RemoteException;
	
	public boolean changePasswordAdmin(String username, String newPassword) throws RemoteException;
	
	public ArrayList<String> getAdminnames() throws RemoteException;
	
	
	// • • • • • • • • HOUSES • • • • • • • • •
	
	public Hashtable<String, String> getHouseNames() throws RemoteException; // Lista taloista
	
	public Hashtable<String, String> getRoomNames(String houseName) throws RemoteException; // Lista talonhuoneista
	
	public Hashtable<String, String> getItems(String houseID, String roomID) throws RemoteException;
	
	// varmaan sitten myös jotain kyselyitä, mitkä palauttaa itemin typin yms. mutta niitä 
	// ainakaan vielä tarvitse
	
	// • • • • • • • • VIEWS • • • • • • • • •
	
	
}
