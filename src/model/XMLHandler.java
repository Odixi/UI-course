package model;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Class for basic methods needed in handling XML files.
 * 
 * @author Pilvi
 */
public class XMLHandler {

	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private DOMSource source;
	
	private Schema schema;
	
	public XMLHandler(){
		
		factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
	}
	
	//---------------- GET DOCUMENT ---------------
	/**
	 * Parse Document-object from the XML file in the given filepath.
	 * @param filepath
	 * @return
	 */
	public Document getDocument(String filepath) {		
		
		try {
		
			return builder.parse(new InputSource(filepath));
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return null;
		
	} //getDocument()
	
	
	//---------------- CREATE DOCUMENT ---------------------

	public Document createDocument(){
		
		try {
			
			return builder.newDocument();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	//--------------- WRITE INTO THE XML FILE -------------
	/**
	 * Write changes made to the Document object to the XML file in the given filepath.
	 * @param doc Document object changes to be written to the XMl file.
	 * @param filepath Filepath to the XML file.
	 */
	public void writeXML(Document doc, String filepath){
		try {
		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			//Make the XML look pretty when stuff is added (pretty = intendented)
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			source = new DOMSource(doc);
			
			//TODO Should this case be handled in here or in separate method?
			File xmlViewFile = new File(filepath);
			
			if( !xmlViewFile.exists() ){ //If the file for the user's view doesn't yet exist/has been deleted, it is created.
				xmlViewFile.createNewFile();
			}
			
			//StreamResult streamResult = new StreamResult(new File(filepath) );
			StreamResult streamResult = new StreamResult(new File(filepath) );
			transformer.transform(source, streamResult);
			
		} catch (TransformerConfigurationException e) {	
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} //writeXML
	
	//-------------- LOAD SCHEMA -------------------------

	public Schema loadSchema(String filepath){
		schema = null;
		
		try {
			String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory schemaFactory = SchemaFactory.newInstance(language);
			schema = schemaFactory.newSchema(new File(filepath));
			
			return schema;
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	//------------ VALIDATE DOC -----------------------------

	public void validateDoc(Schema schema, Document doc){
		Validator validator = schema.newValidator();
		
		try {
			validator.validate(new DOMSource(doc));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
