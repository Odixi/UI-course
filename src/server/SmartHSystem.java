package server;

import java.rmi.*;
import java.util.ArrayList;

public interface SmartHSystem extends Remote {

	// --------- FOR TESTING ------------------
	
	public void testPrintConsole(String message) throws RemoteException;
	
	// • • • • • • • • USERS • • • • • • • • •
	
	public void newUser(String username, String password) throws RemoteException;
	
	public void deleteUser(String username) throws RemoteException;
	
	public boolean login(String username, String password) throws RemoteException;
	
	public ArrayList<String> getUsernames() throws RemoteException;
	
	// ---------- Kyselyt taloista --------- //
	
	public ArrayList<String> getHouses() throws RemoteException; // Lista taloista
	
	public String[] getRooms(String houseName) throws RemoteException; // Lista talonhuoneista
	
	public String[] getItems(String houseName, String roomName) throws RemoteException;
	
	// varmaan sitten myös jotain kyselyitä, mitkä palauttaa itemin typin yms. mutta niitä 
	// ainakaan vielä tarvitse
	
}
