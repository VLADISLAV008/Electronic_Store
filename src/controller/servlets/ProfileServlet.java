package controller.servlets;

import controller.constants.Content;
import controller.exception.DBException;
import org.apache.log4j.Logger;
import controller.database.DBManager;
import controller.entities.db.Order;
import controller.entities.db.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = -7971582229649274973L;
    private static final Logger LOG = Logger.getLogger(ProfileServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String profileContent = req.getParameter("content");
        LOG.trace("Profile content = " + profileContent);
        if (!"orders".equals(profileContent)) {
            LOG.trace("Profile content changed to info.");
            profileContent = "info";
        } else {
            try {
                LOG.trace("Try to select orders by user id from database.");
                List<Order> orders = DBManager.getInstance().selectUserOrders(user.getId());
                orders.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
                
                req.setAttribute("orders", orders);
                LOG.trace("List of orders is added to the request scope.");
            } catch (DBException e) {
                LOG.debug("Cannot select orders from DB.");
                LOG.debug(e.getMessage());
                req.setAttribute("errorMessage", e.getMessage());
            }
        }

        req.setAttribute("profileContent", profileContent);
        session.setAttribute("content", Content.PROFILE);
        req.getRequestDispatcher("/mainPage").forward(req, resp);
    }
}
