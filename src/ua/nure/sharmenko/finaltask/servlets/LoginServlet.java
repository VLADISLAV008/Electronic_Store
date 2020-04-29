package ua.nure.sharmenko.finaltask.servlets;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Content;
import ua.nure.sharmenko.finaltask.constants.Messages;
import ua.nure.sharmenko.finaltask.constants.Path;
import ua.nure.sharmenko.finaltask.database.DBManager;
import ua.nure.sharmenko.finaltask.database.Loader;
import ua.nure.sharmenko.finaltask.entities.db.Category;
import ua.nure.sharmenko.finaltask.entities.db.User;
import ua.nure.sharmenko.finaltask.exception.AppException;

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

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        req.setAttribute("content", Content.LOGIN);
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
            req.setAttribute("content", Content.PRODUCTS_CONTENT);
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