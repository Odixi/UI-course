package exceptions;

/**
 * Asked ID and asked type of object do not match.
 * @author Pilvi
 * 
 * On sidenote: Is this exception ever even used in the project?
 * 
 */

public class IDTypeMismatch extends Exception {

	public IDTypeMismatch(String message){
		super(message);
	}
}
