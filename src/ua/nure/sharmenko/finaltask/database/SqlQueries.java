package ua.nure.sharmenko.finaltask.database;

public final class SqlQueries {
    private SqlQueries() {
    }

    /**
     * SQL queries
     */
    public static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    public static final String SQL_SELECT_ALL_PRODUCTS = "SELECT * FROM products";
    public static final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
}
