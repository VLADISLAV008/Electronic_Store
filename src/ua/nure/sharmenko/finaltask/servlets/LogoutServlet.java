package ua.nure.sharmenko.finaltask.servlets;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 4912977694444703624L;
    private static final Logger LOG = Logger.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        LOG.debug("Session invalidate.");
        req.getSession().setAttribute("content", Path.PRODUCTS);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        requestDispatcher.forward(req, resp);
    }
}
