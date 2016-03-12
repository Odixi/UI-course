package model;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XMLHandler {

	public XMLHandler(){
		
	}
	
	//---------------- GET DOCUMENT ---------------
	
	public Document getDocument(String filepath) {		
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(true);
			
			DocumentBuilder builder = factory.newDocumentBuilder();

			return builder.parse(new InputSource(filepath));
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return null;
		
	} //getDocument()
	
	
	//--------------- WRITE INTO THE XML FILE -------------
	
	public void writeXML(Document doc, String filepath){
		try {
		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath) );
			transformer.transform(source, result);
			
		} catch (TransformerConfigurationException e) {	
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
	} //writeXML
	
}
