package exceptions;


/**
 * An exception that is thrown when the parameters for a method are invalid.
 */
public class InvalidParametersException extends Exception {

    /**
     * Creates a new InvalidParametersException with the specified message.
     *
     * @param message The message to be displayed when the exception is thrown.
     */
    public InvalidParametersException(String message) {
        super(message);
    }
}

