package ua.nure.sharmenko.finaltask.listeners;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.database.DBManager;
import ua.nure.sharmenko.finaltask.entities.db.Product;
import ua.nure.sharmenko.finaltask.entities.web.CriteriaSortingProducts;
import ua.nure.sharmenko.finaltask.exception.DBException;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

@WebListener
public class MyHttpSessionListener implements HttpSessionListener {
    private static final Logger LOG = Logger.getLogger(HttpSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOG.debug("Session created.");
        LOG.debug("Try to select products from DB.");
        HttpSession session = se.getSession();
        session.setAttribute("criteriaSortingProducts", new CriteriaSortingProducts());

        try {
            List<Product> products = DBManager.getInstance().selectAllProducts();
            session.setAttribute("products", products);
            LOG.trace("List of products is added to the session scope.");
        } catch (DBException e) {
            session.setAttribute("errorSelectProducts", e.getMessage());
            LOG.debug("Cannot select products from DB.");
            LOG.debug(e.getMessage());
        }
    }
}
