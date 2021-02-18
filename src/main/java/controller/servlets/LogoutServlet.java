package controller.servlets;

import controller.constants.Content;
import org.apache.log4j.Logger;

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
        LOG.debug("Remove attribute from session");
        session.removeAttribute("user");

        String content = (String) session.getAttribute("content");
        if(Content.ADMIN_CONTROL.equals(content) || Content.PROFILE.equals(content))
        {
            session.setAttribute("content", Content.PRODUCTS_CONTENT);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/mainPage");
        requestDispatcher.forward(req, resp);
    }
}
