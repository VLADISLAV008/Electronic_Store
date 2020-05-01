package ua.nure.sharmenko.finaltask.servlets;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Content;
import ua.nure.sharmenko.finaltask.constants.Path;
import ua.nure.sharmenko.finaltask.database.Loader;
import ua.nure.sharmenko.finaltask.entities.db.Category;
import ua.nure.sharmenko.finaltask.entities.db.Order;
import ua.nure.sharmenko.finaltask.entities.db.Product;
import ua.nure.sharmenko.finaltask.entities.db.ProductOrderInfo;
import ua.nure.sharmenko.finaltask.entities.web.BasketInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/basket")
public class BasketServlet extends HttpServlet {
    private static final long serialVersionUID = -4189047669840392049L;
    private static final Logger LOG = Logger.getLogger(BasketServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load categories from db
        LOG.debug("Try to select categories from DB.");
        List<Category> categories = Loader.loadCategories();
        req.setAttribute("categories", categories);

        req.getSession().setAttribute("content", Content.BASKET);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        // Load categories from db
        LOG.debug("Try to select categories from DB.");
        List<Category> categories = Loader.loadCategories();
        req.setAttribute("categories", categories);

        // Delete product from cart
        try {
            long productId = Long.parseLong(req.getParameter("delete"));
            LOG.debug("Try to delete product from cart");
            LOG.trace("Product id for delete - " + productId);

            Order order = (Order) session.getAttribute("order");
            int index = order.getOrderInfo().indexOf(new ProductOrderInfo(productId, 1));
            if (index != -1) {
                LOG.trace("Product with the id is fined in the list of ordered products");
                ProductOrderInfo removed = order.getOrderInfo().remove(index);

                Product product = order.getProductById(productId);
                int price = 0;
                if (product != null) {
                    price = product.getPrice();
                }
                order.setBill(order.getBill() - removed.getQuantity() * price);
                session.setAttribute("basketInfo", BasketInfo.getBasketInfo(order));
            }
        } catch (NumberFormatException e) {
            LOG.trace("Product id invalid.");
        }

        session.setAttribute("content", Content.BASKET);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        requestDispatcher.forward(req, resp);
    }
}
