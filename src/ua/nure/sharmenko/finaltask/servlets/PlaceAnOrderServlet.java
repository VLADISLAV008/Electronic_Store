package ua.nure.sharmenko.finaltask.servlets;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Content;
import ua.nure.sharmenko.finaltask.constants.Messages;
import ua.nure.sharmenko.finaltask.constants.Names;
import ua.nure.sharmenko.finaltask.constants.Path;
import ua.nure.sharmenko.finaltask.database.DBManager;
import ua.nure.sharmenko.finaltask.database.Loader;
import ua.nure.sharmenko.finaltask.entities.db.Category;
import ua.nure.sharmenko.finaltask.entities.db.Order;
import ua.nure.sharmenko.finaltask.entities.db.Status;
import ua.nure.sharmenko.finaltask.entities.db.User;
import ua.nure.sharmenko.finaltask.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/toOrder")
public class PlaceAnOrderServlet extends HttpServlet {
    private static final long serialVersionUID = -4922088223293636758L;
    private static final Logger LOG = Logger.getLogger(PlaceAnOrderServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Load categories from db
        LOG.debug("Try to select categories from DB.");
        List<Category> categories = Loader.loadCategories();
        req.setAttribute("categories", categories);

        User user = (User) session.getAttribute("user");
        if (user == null) {
            LOG.debug("Cannot place an order, because user is not logged in.");
            req.setAttribute("userNotLogin", Messages.USER_NOT_LOGIN);
        } else {
            Order order = (Order) session.getAttribute("order");
            if (order.getNumberProducts() != 0) {
                order.setStatusId(Status.getStatusId(Status.REGISTERED));
                order.setUserId(user.getId());
                LOG.debug("Try to place an order.");
                String placeOrderResult;
                try {
                    DBManager.getInstance().insertOrder(order);
                    session.setAttribute("basketInfo", Names.EMPTY_BASKET);
                    LOG.debug("Create a new order");
                    session.setAttribute("order", new Order());
                    placeOrderResult = Messages.SUCCESSFUL_PLACE_ORDER;
                } catch (DBException e) {
                    LOG.debug("Cannot place an order.");
                    LOG.debug(e.getMessage());

                    placeOrderResult = e.getMessage();
                }
                req.setAttribute("placeOrderResult", placeOrderResult);
                session.setAttribute("content", Content.PLACE_ORDER_RESULT_CONTENT);
            }
        }
        req.getRequestDispatcher(Path.MAIN_PAGE).forward(req, resp);
    }
}
