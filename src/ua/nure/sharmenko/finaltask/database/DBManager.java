package ua.nure.sharmenko.finaltask.database;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Messages;
import ua.nure.sharmenko.finaltask.constants.Urls;
import ua.nure.sharmenko.finaltask.entities.db.Product;
import ua.nure.sharmenko.finaltask.entities.db.User;
import ua.nure.sharmenko.finaltask.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DB manager. Works with Apache Derby DB. Only the required DAO methods are
 * defined!
 *
 * @author V.Shramenko
 */
public final class DBManager {

    private final String connectionURL;
    private static final Logger LOG = Logger.getLogger(DBManager.class);
    private static DBManager instance;
/*
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_CREATE_TEAM = "INSERT INTO teams VALUES (DEFAULT, ?)";
    private static final String SQL_FIND_ALL_TEAMS = "SELECT * FROM teams";
    private static final String SQL_GET_USER = "SELECT * FROM users WHERE login=?";
    private static final String SQL_GET_TEAM_BY_ID = "SELECT * FROM teams WHERE id=?";
    private static final String SQL_GET_TEAM = "SELECT * FROM teams WHERE name=?";
    private static final String SQL_INSERT_USER_TEAM = "INSERT INTO users_teams VALUES (?, ?)";
    private static final String SQL_GET_USER_TEAMS = "SELECT * FROM users_teams WHERE user_id=?";
    private static final String SQL_DELETE_TEAM_FROM_USER_TEAMS = "DELETE FROM users_teams WHERE team_id=?";
    private static final String SQL_DELETE_TEAM = "DELETE FROM teams WHERE id=?";
    private static final String SQL_UPDATE_TEAM = "UPDATE teams SET name=? WHERE id=?";
*/

    private DBManager() {
        connectionURL = Urls.connectionUrlDatabase;
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }


    public Connection getConnection() throws DBException {
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(connectionURL);
        } catch (Exception ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    public void insertUser(User user) throws DBException {
        LOG.debug("Try to insert user " + user + " to database.");
        Connection con = null;
        PreparedStatement prepState = null;

        try {
            con = getConnection();
            prepState = con.prepareStatement(SqlQueries.SQL_INSERT_USER,
                    Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            prepState.setString(i++, user.getEmail());
            prepState.setString(i++, user.getPassword());
            prepState.setString(i++, Integer.toString(user.getRoleId()));
            prepState.setString(i++, user.getName());
            prepState.setString(i++, user.getSurname());

            if (prepState.executeUpdate() > 0) {
                ResultSet rs = prepState.getGeneratedKeys();
                if (rs.next()) {
                    Long userId = rs.getLong(1);
                    user.setId(userId);
                }
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_ADD_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_ADD_USER, ex);
        } finally {
            close(con, prepState, null);
        }
    }

    public User findUserByEmail(String email) throws DBException {
        LOG.debug("Try to get user with email = " + email + " from database.");
        Connection con = null;
        PreparedStatement prepState = null;
        ResultSet res = null;
        User user = null;
        try {
            con = getConnection();
            prepState = con.prepareStatement(SqlQueries.SQL_SELECT_USER_BY_EMAIL);
            prepState.setString(1, email);
            res = prepState.executeQuery();
            if (res.next()) {
                user = extractUser(res);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_USER_NOT_EXIST, ex);
            throw new DBException(Messages.ERR_USER_NOT_EXIST, ex);
        } finally {
            close(con, prepState, res);
        }
        return user;
    }

    private static User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRoleId(rs.getInt("roleId"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        return user;
    }

    public List<Product> selectAllProducts() throws DBException {
        LOG.debug("Try to select all products from database.");
        ArrayList<Product> products = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            con = getConnection();
            statement = con.createStatement();
            res = statement.executeQuery(SqlQueries.SQL_SELECT_ALL_PRODUCTS);

            while (res.next()) {
                Product product = extractProduct(res);
                products.add(product);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_SELECT_PRODUCTS, ex);
            throw new DBException(Messages.ERR_SELECT_PRODUCTS, ex);
        } finally {
            close(con, statement, res);
        }
        return products;
    }

    private static Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getInt("price"));
        product.setAmount(rs.getInt("amount"));
        return product;
    }

    /**
     * Closes a connection.
     */
    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Closes a statement object.
     */
    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    /**
     * Closes a result set object.
     */
    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULT_SET, ex);
            }
        }
    }

    /**
     * Closes resources.
     */
    private void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }
}
