package controller.database;

import controller.constants.Messages;
import controller.constants.Urls;
import controller.entities.db.*;
import controller.exception.DBException;
import org.apache.log4j.Logger;

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
            prepState.setInt(i++, user.getRoleId());
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

    public void insertProduct(Product product) throws DBException {
        LOG.debug("Try to insert product " + product + " to database.");
        Connection con = null;
        PreparedStatement prepState = null;

        try {
            con = getConnection();
            prepState = con.prepareStatement(SqlQueries.SQL_INSERT_PRODUCT,
                    Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            prepState.setString(i++, product.getName());
            prepState.setInt(i++, product.getPrice());
            prepState.setBoolean(i++, product.getAvailable());
            prepState.setLong(i++, product.getCategoryId());

            if (prepState.executeUpdate() > 0) {
                ResultSet rs = prepState.getGeneratedKeys();
                if (rs.next()) {
                    Long productId = rs.getLong(1);
                    product.setId(productId);
                }
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_ADD_PRODUCT);
            throw new DBException(Messages.ERR_CANNOT_ADD_PRODUCT, ex);
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

    public Product findProductById(Long id) throws DBException {
        LOG.debug("Try to get product by id = " + id + " from database.");
        Connection con = null;
        PreparedStatement prepState = null;
        ResultSet res = null;
        Product product = null;
        try {
            con = getConnection();
            prepState = con.prepareStatement(SqlQueries.SQL_SELECT_PRODUCT_BY_ID);
            prepState.setString(1, Long.toString(id));
            res = prepState.executeQuery();
            if (res.next()) {
                product = extractProduct(res);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_PRODUCT_NOT_EXIST, ex);
            throw new DBException(Messages.ERR_PRODUCT_NOT_EXIST, ex);
        } finally {
            close(con, prepState, res);
        }
        return product;
    }

    public List<Product> selectProductsByCategory(long categoryId) throws DBException {
        LOG.debug("Try to select products by category with id = " + categoryId + " from database.");
        ArrayList<Product> products = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet res = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SqlQueries.SQL_SELECT_PRODUCTS_BY_CATEGORY);
            preparedStatement.setString(1, Long.toString(categoryId));
            res = preparedStatement.executeQuery();

            while (res.next()) {
                Product product = extractProduct(res);
                products.add(product);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_SELECT_PRODUCTS, ex);
            throw new DBException(Messages.ERR_SELECT_PRODUCTS, ex);
        } finally {
            close(con, preparedStatement, res);
        }
        return products;
    }

    public List<Category> selectAllCategories() throws DBException {
        LOG.debug("Try to select all categories from database.");
        ArrayList<Category> categories = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            con = getConnection();
            statement = con.createStatement();
            res = statement.executeQuery(SqlQueries.SQL_SELECT_ALL_CATEGORIES);

            while (res.next()) {
                Category category = extractCategory(res);
                categories.add(category);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_SELECT_CATEGORIES, ex);
            throw new DBException(Messages.ERR_SELECT_CATEGORIES, ex);
        } finally {
            close(con, statement, res);
        }
        return categories;
    }


    public void insertOrder(Order order) throws DBException {
        LOG.debug("Try to insert order to database.");
        Connection con = null;
        PreparedStatement prepState = null;
        ResultSet res = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            prepState = con.prepareStatement(SqlQueries.SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);

            int j = 1;
            prepState.setLong(j++, order.getUserId());
            prepState.setLong(j++, order.getStatusId());
            prepState.setInt(j++, order.getBill());

            if (prepState.executeUpdate() > 0) {
                res = prepState.getGeneratedKeys();
                if (res.next()) {
                    Long orderId = res.getLong(1);
                    order.setId(orderId);
                }
            }

            LOG.debug("Try to insert ordered products to database.");
            List<ProductOrderInfo> list = order.getOrderInfo();
            for (ProductOrderInfo p : list) {
                insertOrderProduct(order.getId(), p, con);
            }

            con.commit();
            LOG.debug("Commit completed successfully.");
        } catch (SQLException e) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_PLACE_ORDER);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_CANNOT_PLACE_ORDER, e);
        } finally {
            close(con, prepState, res);
        }
    }

    private void insertOrderProduct(Long orderId, ProductOrderInfo product, Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SqlQueries.SQL_INSERT_ORDER_PRODUCT);
        int i = 1;
        pstmt.setLong(i++, orderId);
        pstmt.setLong(i++, product.getProductId());
        pstmt.setInt(i++, product.getQuantity());
        pstmt.executeUpdate();
        pstmt.close();
    }

    public Order selectOrderById(long orderId) throws DBException {
        LOG.debug("Try to select all order by oderId = " + orderId + " from database.");
        Order order = null;
        Connection con = null;
        PreparedStatement prepState = null;
        ResultSet res = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            prepState = con.prepareStatement(SqlQueries.SQL_SELECT_ORDERS_BY_USER_ID);
            prepState.setLong(1, orderId);
            res = prepState.executeQuery();

            if (res.next()) {
                order = extractOrder(res);
                List<ProductOrderInfo> orderInfo = selectOrderedProductsByOrderId(orderId, con);
                order.setOrderInfo(orderInfo);
            }

            con.commit();
            LOG.debug("Commit completed successfully.");
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_SELECT_ORDER);
            LOG.error(ex.getMessage());
            throw new DBException(Messages.ERR_CANNOT_SELECT_ORDER, ex);
        } finally {
            close(con, prepState, res);
        }
        return order;
    }

    public List<Order> selectUserOrders(long userId) throws DBException {
        LOG.debug("Try to select all orders by userId = " + userId + " from database.");
        ArrayList<Order> orders = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepState = null;
        ResultSet res = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            prepState = con.prepareStatement(SqlQueries.SQL_SELECT_ORDERS_BY_USER_ID);
            prepState.setLong(1, userId);
            res = prepState.executeQuery();

            while (res.next()) {
                Order order = extractOrder(res);
                List<ProductOrderInfo> orderInfo = selectOrderedProductsByOrderId(order.getId(), con);
                order.setOrderInfo(orderInfo);
                orders.add(order);
            }

            con.commit();
            LOG.debug("Commit completed successfully.");
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_SELECT_ORDERS);
            LOG.error(ex.getMessage());
            throw new DBException(Messages.ERR_CANNOT_SELECT_ORDERS, ex);
        } finally {
            close(con, prepState, res);
        }
        return orders;
    }

    private List<ProductOrderInfo> selectOrderedProductsByOrderId(long orderId, Connection con) throws SQLException {
        LOG.debug("Try to select ordered products by orderId from database.");
        List<ProductOrderInfo> list = new ArrayList<>();
        PreparedStatement prepState = null;
        ResultSet res = null;
        try {
            prepState = con.prepareStatement(SqlQueries.SQL_SELECT_ORDERED_PRODUCTS_BY_ID);
            prepState.setLong(1, orderId);
            res = prepState.executeQuery();

            while (res.next()) {
                ProductOrderInfo el = extractProductOrderInfo(res);
                list.add(el);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_FAILED_SELECT_ORDERED_PRODUCTS, ex);
        } finally {
            close(null, prepState, res);
        }
        return list;
    }

    private ProductOrderInfo extractProductOrderInfo(ResultSet rs) throws SQLException {
        ProductOrderInfo p = new ProductOrderInfo();
        p.setProductId(rs.getLong("productId"));
        p.setQuantity(rs.getInt("amount"));
        return p;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setUserId(rs.getLong("userId"));
        order.setStatusId(rs.getLong("statusId"));
        order.setBill(rs.getInt("bill"));
        return order;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRoleId(rs.getInt("roleId"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        return user;
    }

    private Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getInt("price"));
        product.setAvailable(rs.getBoolean("available"));
        product.setCategoryId(rs.getInt("categoryId"));
        return product;
    }

    private Category extractCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setName(rs.getString("name"));
        return category;
    }

    /**
     * Rollbacks a connection.
     *
     * @param con Connection to be rollbacked.
     */
    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Error performing rollback transaction", ex);
            }
        }
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
