package ua.nure.sharmenko.finaltask.servlets;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Content;
import ua.nure.sharmenko.finaltask.constants.Path;
import ua.nure.sharmenko.finaltask.database.DBManager;
import ua.nure.sharmenko.finaltask.database.Loader;
import ua.nure.sharmenko.finaltask.entities.db.Category;
import ua.nure.sharmenko.finaltask.entities.db.Order;
import ua.nure.sharmenko.finaltask.entities.db.User;
import ua.nure.sharmenko.finaltask.exception.DBException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = -5241038583390586280L;
    private static final Logger LOG = Logger.getLogger(SignUpServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load categories from db
        LOG.debug("Try to select categories from DB.");
        List<Category> categories = Loader.loadCategories();
        req.setAttribute("categories", categories);

        req.getSession().setAttribute("content", Content.SIGN_UP);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load categories from db
        LOG.debug("Try to select categories from DB.");
        List<Category> categories = Loader.loadCategories();
        req.setAttribute("categories", categories);

        // Sign up a new user
        User user = new User();
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("pass"));
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setRoleId(0);

        LOG.debug("Try to sign up the user " + user + ".");

        try {
            DBManager.getInstance().insertUser(user);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("content", Content.PRODUCTS_CONTENT);
            LOG.trace("The user " + user + " is signed up.");

            Order order = (Order) session.getAttribute("order");
            if (order == null) {
                LOG.debug("Create a new order");
                order = new Order();
                session.setAttribute("order", order);
            }
            order.setUserId(user.getId());

            resp.sendRedirect(req.getContextPath() + "/mainPage");
        } catch (DBException e) {
            LOG.debug("Cannot sign up the user " + user + ".");
            LOG.debug(e.getMessage());

            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(Path.MAIN_PAGE).forward(req, resp);
        }
    }
}
