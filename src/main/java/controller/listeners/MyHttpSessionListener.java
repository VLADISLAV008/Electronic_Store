package controller.listeners;

import org.apache.log4j.Logger;
import controller.constants.Names;
import controller.entities.db.Order;
import controller.entities.web.CriteriaSortingProducts;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MyHttpSessionListener implements HttpSessionListener {
    private static final Logger LOG = Logger.getLogger(HttpSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        LOG.debug("Session id  = " + session.getId() + " created.");
        session.setAttribute("criteriaSortingProducts", new CriteriaSortingProducts());
        session.setAttribute("basketInfo", Names.EMPTY_BASKET);
        LOG.debug("Create a new order");
        session.setAttribute("order", new Order());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
