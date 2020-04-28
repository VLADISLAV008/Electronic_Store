package ua.nure.sharmenko.finaltask.servlets;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Content;
import ua.nure.sharmenko.finaltask.constants.Path;
import ua.nure.sharmenko.finaltask.database.DBManager;
import ua.nure.sharmenko.finaltask.entities.db.Product;
import ua.nure.sharmenko.finaltask.entities.web.CriteriaSortingProducts;
import ua.nure.sharmenko.finaltask.exception.DBException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet("/mainPage")
public class MainPageServlet extends HttpServlet {
    private static final long serialVersionUID = 7776173346442089585L;
    private static final Logger LOG = Logger.getLogger(MainPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sortMethod = req.getParameter("sortMethod");
        LOG.trace("Sorting criterion - " + sortMethod);

        Long categoryId = null;
        try {
            categoryId = Long.parseLong(req.getParameter("categoryId"));
            LOG.trace("Category id - " + categoryId);
        } catch (NumberFormatException e) {
            LOG.trace("Category id invalid.");
        }

        HttpSession session = req.getSession();

        if (categoryId != null) {
            session.setAttribute("content", Content.PRODUCTS_CONTENT);
            List<Product> products;
            try {
                products = DBManager.getInstance().selectProductsByCategory(categoryId);
                session.setAttribute("products", products);
                ((CriteriaSortingProducts) session.getAttribute("criteriaSortingProducts")).changeSelectedCriterion("null");
                LOG.trace("List of products is added to the session scope.");
            } catch (DBException e) {
                LOG.debug("Cannot select products from DB.");
                LOG.debug(e.getMessage());
            }
        }

        CriteriaSortingProducts csp = (CriteriaSortingProducts) session.getAttribute("criteriaSortingProducts");
        csp.changeSelectedCriterion(sortMethod);
        Comparator<Product> comparator = csp.getComparator(sortMethod);

        if (comparator != null) {
            ((List<Product>) session.getAttribute("products")).sort(comparator);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        requestDispatcher.forward(req, resp);
    }
}
