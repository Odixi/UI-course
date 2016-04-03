package exceptions;

/**
 * Element retrieved from XML is null.
 * @author Pilvi
 */

public class ElementNullException extends Exception {
	public ElementNullException(String message){
		super(message);
	}
}
