package controller.exception;

/**
 * An exception that provides information on a database access error.
 *
 * @author V.Shramenko
 */
public class DBException extends AppException{

    private static final long serialVersionUID = -6951000889141621233L;

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
