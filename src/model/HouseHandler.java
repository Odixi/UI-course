package model;

import org.xml.sax.*;
import org.w3c.dom.*;


public class HouseHandler extends XMLHandler {

	private String filepath = "src/xmlfiles/HouseBuild.xml";
	private NodeList houseList;
	private Document housesXML;
	private Element rootElement;
//	private ArrayList<String> houses;
//	private ArrayList<String> 
	
	//CONSTRUCTOR
	public HouseHandler(){
		
		housesXML = getDocument(filepath);
		housesXML.getDocumentElement().normalize();
		rootElement = housesXML.getDocumentElement();
		
	} //constructor
	
	
	
}
