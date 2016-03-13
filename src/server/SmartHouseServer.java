package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SmartHouseServer {

	public static void main(String args[]){
		
		try {
			
			SmartHSystemImp shsystem = new SmartHSystemImp();
			
			Registry registry = LocateRegistry.createRegistry(2020);
			System.out.println(registry);
			
			Naming.rebind("//localhost:2020/shsystem", shsystem); //TODO Change address
			
			
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	} // main
}
