package ua.nure.sharmenko.finaltask.exception;

/**
 * An exception that provides information on an application error.
 *
 * @author V.Shramenko
 */
public class AppException extends Exception {

    private static final long serialVersionUID = -8047808786569441507L;

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }
}
