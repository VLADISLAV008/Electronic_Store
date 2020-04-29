package ua.nure.sharmenko.finaltask.servlets;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Content;
import ua.nure.sharmenko.finaltask.constants.Path;
import ua.nure.sharmenko.finaltask.database.DBManager;
import ua.nure.sharmenko.finaltask.database.Loader;
import ua.nure.sharmenko.finaltask.entities.db.Category;
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
import java.util.Comparator;
import java.util.List;

@WebServlet("/mainPage")
public class MainPageServlet extends HttpServlet {
    private static final long serialVersionUID = 7776173346442089585L;
    private static final Logger LOG = Logger.getLogger(MainPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setAttribute("content", Content.PRODUCTS_CONTENT);

        // Load categories from db
        LOG.debug("Try to select categories from DB.");
        List<Category> categories = Loader.loadCategories();
        req.setAttribute("categories", categories);

        // Parse category
        Long categoryId = null;
        try {
            categoryId = Long.parseLong(req.getParameter("categoryId"));
            session.setAttribute("criteriaSortingProducts", new CriteriaSortingProducts());
            LOG.trace("Category id - " + categoryId);
        } catch (NumberFormatException e) {
            LOG.trace("Category id invalid.");
        }
        if (categoryId == null) {
            if (categories != null) {
                categoryId = categories.get(0).getId();
            }
        }
        LOG.trace("Category id = " + categoryId);

        // Load products from db
        LOG.debug("Try to select products by categoryId = " + categoryId + " from DB.");
        List<Product> products = Loader.loadProducts(categoryId);
        req.setAttribute("products", products);

        // Parse sort method
        if (products != null) {
            String sortMethod = req.getParameter("sortMethod");
            LOG.trace("Sorting criterion - " + sortMethod);

            CriteriaSortingProducts csp = (CriteriaSortingProducts) session.getAttribute("criteriaSortingProducts");
            csp.changeSelectedCriterion(sortMethod);
            Comparator<Product> comparator = csp.getComparator(sortMethod);

            if (comparator != null) {
                products.sort(comparator);
            }
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Add to cart new product
        Long productId = null;
        try {
            productId = Long.parseLong(req.getParameter("productId"));
            LOG.trace("Product id - " + productId);
        } catch (NumberFormatException e) {
            LOG.trace("Product id invalid.");
        }

        if (productId != null) {
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        requestDispatcher.forward(req, resp);
    }
}
