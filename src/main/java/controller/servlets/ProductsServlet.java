package controller.servlets;

import controller.constants.Content;
import controller.constants.Path;
import controller.exception.DBException;
import org.apache.log4j.Logger;
import controller.database.DBManager;
import controller.database.Loader;
import controller.entities.db.Category;
import controller.entities.db.Order;
import controller.entities.db.Product;
import controller.entities.db.ProductOrderInfo;
import controller.entities.web.BasketInfo;
import controller.entities.web.CriteriaSortingProducts;

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

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    private static final long serialVersionUID = -1552490506734236843L;
    private static final Logger LOG = Logger.getLogger(ProductsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("content", Content.PRODUCTS_CONTENT);

        // Load categories from db
        LOG.debug("Try to select categories from DB.");
        List<Category> categories = Loader.loadCategories();
        req.setAttribute("categories", categories);

        // Parse category
        Long categoryId = null;
        try {
            categoryId = Long.parseLong(req.getParameter("categoryId"));
            session.setAttribute("criteriaSortingProducts", new CriteriaSortingProducts());
            session.setAttribute("categoryId", categoryId);
            LOG.trace("Category id - " + categoryId);
        } catch (NumberFormatException e) {
            LOG.trace("Category id invalid.");
        }
        if (categoryId == null && categories != null) {
            if (session.getAttribute("categoryId") != null) {
                categoryId = (Long) session.getAttribute("categoryId");
            } else {
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
            Comparator<Product> comparator = csp.getComparator();

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
            Order order = (Order) session.getAttribute("order");
            if (order == null) {
                LOG.debug("Create a new order");
                order = new Order();
                session.setAttribute("order", order);
            }
            try {
                Product product = DBManager.getInstance().findProductById(productId);
                order.setBill(order.getBill() + product.getPrice());
                List<ProductOrderInfo> list = order.getOrderInfo();
                boolean contain = false;
                for (ProductOrderInfo el : list) {
                    if (el.getProductId() == product.getId()) {
                        el.setQuantity(el.getQuantity() + 1);
                        contain = true;
                        break;
                    }
                }
                if (!contain) {
                    list.add(new ProductOrderInfo(product.getId(), 1));
                }
            } catch (DBException e) {
                LOG.debug(e.getMessage());
            }

            session.setAttribute("basketInfo", BasketInfo.getBasketInfo(order));
        }
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}
