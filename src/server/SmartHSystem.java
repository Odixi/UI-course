package server;

import java.rmi.*;
import java.util.ArrayList;
import java.util.Hashtable;

public interface SmartHSystem extends Remote {

	// --------- FOR TESTING ------------------
	
	public void testPrintConsole(String message) throws RemoteException;
	
	// • • • • • • • • USERS • • • • • • • • •
	
	public void newUser(String username, String password) throws RemoteException;
	
	// Jos password on tyhjä merkkijono, niin sitä ei muuteta
	public boolean changePassword(String username, String oldPassword, String newPassword) throws RemoteException;
	
	public boolean changePasswordAdmin(String username, String newPassword) throws RemoteException;
	
	public boolean changeUsername(String oldUsername, String newUsername) throws RemoteException;
	
	public void deleteUser(String username) throws RemoteException;
	
	public boolean userLogin(String username, String password) throws RemoteException;
	
	public ArrayList<String> getUsernames() throws RemoteException;
	
		// o.o.o.o UserView o.o.o.o.o.o
		
	public Hashtable<String, Boolean> getUserView(String userName) throws RemoteException;
	
	public void setUserView(Hashtable<String, Boolean> userView) throws RemoteException;
	
	// • • • • • • • • ADMINS • • • • • • • • •

	public void newAdmin(String adminname, String password) throws RemoteException;
	
	public void deleteAdmin(String adminname) throws RemoteException;
	
	/* >>>>>>>> This is probably going the only one we'll actually need.
	 			The others available... well just because I can make them available *cheeky grin* */
	public boolean adminLogin(String adminname, String password) throws RemoteException;
	
	public ArrayList<String> getAdminnames() throws RemoteException;
	
	// ---------- Kyselyt taloista --------- //
	
	public Hashtable<String, String> getHouseNames() throws RemoteException; // Lista taloista
	
	public Hashtable<String, String> getRoomNames(String houseName) throws RemoteException; // Lista talonhuoneista
	
	public ArrayList<String> getItems(String houseName, String roomName) throws RemoteException;
	
	// varmaan sitten myös jotain kyselyitä, mitkä palauttaa itemin typin yms. mutta niitä 
	// ainakaan vielä tarvitse
	
}
