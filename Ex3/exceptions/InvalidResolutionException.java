package exceptions;


/**
 * An exception that is thrown when the resolution of the image is invalid.
 */
public class InvalidResolutionException extends Exception {

    /**
     * Creates a new InvalidResolutionException with the specified message.
     *
     * @param messageError The message to be displayed when the exception is thrown.
     */
    public InvalidResolutionException(String messageError) {
        super(messageError);
    }
}

