package model;

import java.util.ArrayList;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;

public class ViewHandler extends XMLHandler {

	private String filepath = "src/xmlfiles/views.xml";
	private Document viewsXML;
	private Element rootElement;
	private NodeList viewNodeList;
	
	// >>> XML tags
	
	private static final String inView = "inView";
	
	private static final String viewTag = "view";	
	private static final String viewIDTag = "viewID";

	private static final String userIDTag = "userID";
	
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
		
		//Iterate through views, find userID == userIDTag.getTextContent();
		if( userHasView(userID) ){

			Element view = getViewElement(userID);
			
			ArrayList<Element> houseElements = getHouseElements(view);
			//Iterate through houses
			for(int i = 0; i < houseElements.size(); i++){
				//inView = true/false?
				if(houseElements.get(i).getAttribute(houseIDTag) != null && houseElements.get(i).getAttribute(inView) != null){
					
					//Add house info (ID, inView) to hashtable 
					userView.put(houseElements.get(i).getAttribute(houseIDTag), parseBoolean( houseElements.get(i).getAttribute(inView).trim() ) );
					
					//Rooms
					ArrayList<Element> roomElements = getRoomElements(houseElements.get(i));
					
					for(int j = 0; j < roomElements.size(); j++){
						
						//inView = true/false?
						
						//Lights, sensors and appliance
						
							//inView = true/false?
	
					}
				}	
			}				
		} else {
			//return list where for all: inView = false
			
			
		}
		
		return null;
	}
	
	
// o.o.o.o.o.o.o.o.o.o.o.o.o.o.o HELP METHODS o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o.o
	
	private boolean parseBoolean(String b){
		if(b.equals("0") || b.equalsIgnoreCase("true")){
			return true;
		} else {
			return false;
		}
	}
	
	private void updateViewNodeList(){
		viewNodeList = viewsXML.getElementsByTagName(viewTag);
	}
	
	//Pretty studip and probably unnecessary method.
	private boolean userHasView(String userID){
		boolean hasView = false;
		
		for(int i = 0; i < viewNodeList.getLength(); i++){
			if(viewNodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element viewElement = (Element) viewNodeList.item(i);
			
				if(viewElement.getAttribute(userIDTag) != null && viewElement.getAttribute(userIDTag).equals(userID) ){
					hasView = true;
					break;
				}
			}
		}
		return hasView;	
	}
	

	private Element getViewElement(String userID){
		Element viewElement = null;
		
		for(int i = 0; i < viewNodeList.getLength(); i++){
			if(viewNodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element e = (Element) viewNodeList.item(i);
			
				if(e.getAttribute(userIDTag) != null && e.getAttribute(userIDTag).equals(userID) ){
					viewElement = e;
					break;
				}
			}
		}
		return viewElement;
	}
	
	
	// o o o o o o o o o o o o GET HOUSES o o o o o o o o o o o o 
	private ArrayList<Element> getHouseElements(Element view){
		ArrayList<Element> houseElements = new ArrayList<Element>();
		updateViewNodeList();
		
		NodeList houses = view.getElementsByTagName(housesTag);
		
		//There's actually just one 'houses' element but I'm going for the more general solution just in case.
		for(int i = 0; i < houses.getLength(); i++){
			if(houses.item(i).getNodeType() == Node.ELEMENT_NODE){
				NodeList houseNodes = ((Element)houses.item(i)).getElementsByTagName(houseTag);
				
				for(int j = 0; j < houseNodes.getLength(); j++){
					if(houseNodes.item(i).getNodeType() == Node.ELEMENT_NODE && houseNodes.item(i) != null){
						houseElements.add( (Element)houseNodes.item(i) );
					}
				}
			}
		}
		return houseElements;
	}
	
	// o o o o o o o o o o o o GET ROOMS o o o o o o o o o o o o 
	private ArrayList<Element> getRoomElements(Element house){
		return null;
	}
	
	
	// o o o o o o o o o o o o GET ITEMS o o o o o o o o o o o o 
	private ArrayList<Element> getItemElements(Element room){
		return null;
	}
	
}
