package ua.nure.sharmenko.finaltask.constants;

/**
 * Holder for messages of exceptions.
 *
 * @author V.Shramenko
 */
public final class Messages {

    private Messages() {
    }

    public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection.";

    public static final String ERR_CANNOT_CLOSE_RESULT_SET = "Cannot close a result set.";

    public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement.";

    public static final String ERR_CANNOT_ADD_USER = "A user with this email already exists.";

    public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool.";

    public static final String ERR_LOGIN_OR_PASSWORD_EMPTY = "Login/password cannot be empty.";

    public static final String ERR_USER_NOT_EXIST = "User with such login/password doesn't exist.";

    public static final String ERR_SELECT_PRODUCTS = "Sorry, an error occurred while loading products information.";

    public static final String ERR_SELECT_CATEGORIES = "Sorry, an error occurred while loading categories.";

    public static final String ERR_PRODUCT_NOT_EXIST = "Product with this id doesn't exist.";

    public static final String USER_NOT_LOGIN = "To place an order, please log in.";

    public static final String ERR_CANNOT_PLACE_ORDER = "Sorry, an error occurred while placing your order.";

    public static final String SUCCESSFUL_PLACE_ORDER = "The order is placed successfully. Thank you for your purchase!";
}