package exceptions;

/**
 * User matching the given ID can't be found. Thrown when information regarding should be returned,
 * but can't be found - for the reason mentioned in the first sentence.
 * @author Pilvi
 */

public class IDMatchNotFoundException extends Exception {
	public IDMatchNotFoundException(String message){
		super(message);
	}
}
