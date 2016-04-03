package exceptions;

/**
 * The structure of the XML file is broken and doesn't match the schema.
 * @author Pilvi
 *
 */

public class XMLBrokenException extends Exception {
	public XMLBrokenException(String message){
		super(message);
	}
}
