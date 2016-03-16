package model;

import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ViewHandler extends XMLHandler {

	private String filepath = "src/xmlfiles/views.xml";
	private Document viewsXML;
	private Element rootElement;
	private NodeList viewList;
	
	// >>> XML tags
	
	private static final String housesTag = "houses";
		//House
	private static final String houseTag = "house";
	private static final String housenameTag = "houseName";
	private static final String houseIDTag = "houseID";
		//Room
	private static final String roomnameTag = "roomName";
	private static final String roomTag = "room";
	private static final String roomIDTag = "roomID";
		//Lights
	private static final String lightTag = "light";
	private static final String lightnameTag = "ligthName";
	private static final String lightIDTag = "lightID";
		//Sensors
	private static final String sensorTag = "sensor";
	private static final String sensornameTag = "sensorName";
	private static final String sensorIDTag = "sensorID";
		//Appliances
	private static final String applianceTag = "appliance";
	private static final String appliancenameTag = "applianceName";
	private static final String applianceIDTag = "applianceID";
	
	//CONSTRUCTOR
	
	public ViewHandler(){

		viewsXML = getDocument(filepath);
		viewsXML.getDocumentElement().normalize();
		
		rootElement = viewsXML.getDocumentElement();
		
	} //constructor
	
	
	//--------------- SAVE THE USERVIEW TO THE XML FOR THE FIRST TIME --------------------
	
	public void createUserView(){	//Could also be called setUserView()

	}
	
	
	//--------------- UPDATE THE USERVIEW --------------------
	
	public void updateUserView(){
		
	}
	
	//--------------- RETURN THE USERVIEW STRUCTURE --------------------
	
	public Hashtable<String, Boolean> getUserView(String userID){
		Hashtable<String, Boolean> userView = new Hashtable<String, Boolean>();
		
		
		
		return null;
	}
	
	
// o.o.o.o.o.o.o.o.o.o.o.o.o.o.o HELP METHODS o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o
	
	
	
}
