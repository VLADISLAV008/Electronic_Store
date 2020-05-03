package ua.nure.sharmenko.finaltask.database;

public final class SqlQueries {
    private SqlQueries() {
    }

    /**
     * SQL queries
     */
    public static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    public static final String SQL_SELECT_PRODUCTS_BY_CATEGORY = "SELECT * FROM products WHERE categoryId=?";
    public static final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
    public static final String SQL_SELECT_PRODUCT_BY_ID = "SELECT * FROM products WHERE id=?";
    public static final String SQL_INSERT_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?, ?)";
    public static final String SQL_INSERT_ORDER_PRODUCT = "INSERT INTO orders_products VALUES (?, ?, ?)";
    public static final String SQL_SELECT_ORDERS_BY_USER_ID = "SELECT * FROM orders WHERE userId=?";
}
