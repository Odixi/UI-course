package model;

import java.util.ArrayList;

import org.w3c.dom.*;

public class HouseObjectGenerator extends HouseHandler { //Or should it extend HouseHandler?

	private static final String housefilepath = "src/xmlfiles/houses.xml";
	
	
	public HouseObjectGenerator(){
		super();
	}
	
	public void buildHouses(){
		ArrayList<Element> houseElements = super.getHouseElements();
		
		
		
	}
	
}
