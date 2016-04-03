package exceptions;

/**
 * Thrown when document object is null.
 * @author Pilvi
 *
 */

public class DocumentNullException extends Exception {
	public DocumentNullException(String message) {
		super(message);
	}
}
