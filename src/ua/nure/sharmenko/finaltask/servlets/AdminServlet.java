package ua.nure.sharmenko.finaltask.servlets;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Content;
import ua.nure.sharmenko.finaltask.constants.Messages;
import ua.nure.sharmenko.finaltask.database.DBManager;
import ua.nure.sharmenko.finaltask.entities.db.Product;
import ua.nure.sharmenko.finaltask.entities.db.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 6296516575655714751L;
    private static final Logger LOG = Logger.getLogger(AdminServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            super.doGet(req, resp);
            return;
        }
        req.getSession().setAttribute("content", Content.ADMIN_CONTROL);
        req.getRequestDispatcher("/mainPage").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            super.doGet(req, resp);
            return;
        }

        String result;
        try {
            LOG.debug("Try to create a new product.");
            Product product = new Product();
            product.setName(req.getParameter("name"));
            product.setPrice(Integer.parseInt(req.getParameter("price")));
            product.setAvailable(Boolean.parseBoolean(req.getParameter("available")));
            product.setCategoryId(Integer.parseInt(req.getParameter("category")));
            LOG.debug("Received product = " + product);

            DBManager.getInstance().insertProduct(product);

            result = Messages.SUCCESSFUL_ADD_PRODUCT;
        } catch (Exception e) {
            result = Messages.ERR_FAILED_ADD_PRODUCT;
            LOG.error(e.getMessage());
        }
        LOG.error(result);

        session.setAttribute("resultAddProduct", result);
        resp.sendRedirect(req.getContextPath() + "/mainPage");
    }
}
