package controller.servlets;

import controller.constants.Content;
import controller.constants.Messages;
import controller.constants.Path;
import controller.exception.AppException;
import org.apache.log4j.Logger;
import controller.database.DBManager;
import controller.database.Loader;
import controller.entities.db.Category;
import controller.entities.db.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 8314341917285670311L;
    private static final Logger LOG = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load categories from db
        LOG.debug("Try to select categories from DB.");
        List<Category> categories = Loader.loadCategories();
        req.setAttribute("categories", categories);

        req.getSession().setAttribute("content", Content.LOGIN);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load categories from db
        LOG.debug("Try to select categories from DB.");
        List<Category> categories = Loader.loadCategories();
        req.setAttribute("categories", categories);

        // Log in a new user
        String email = req.getParameter("email");
        String password = req.getParameter("pass");
        LOG.debug("Try to login the user with email = " + email + ", password = " + password + ".");

        try {
            if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                throw new AppException(Messages.ERR_LOGIN_OR_PASSWORD_EMPTY);
            }

            User user = DBManager.getInstance().findUserByEmail(email);
            LOG.trace("Found in DB: user --> " + user);

            if (user == null || !password.equals(user.getPassword())) {
                throw new AppException(Messages.ERR_USER_NOT_EXIST);
            }

            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            session.setAttribute("content", Content.PRODUCTS_CONTENT);
            resp.sendRedirect(req.getContextPath() + "/mainPage");
            LOG.trace("The user " + user + " is log in.");
        } catch (AppException e) {
            LOG.debug("Cannot log in the user.");
            LOG.debug(e.getMessage());

            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(Path.MAIN_PAGE).forward(req, resp);
        }
    }
}