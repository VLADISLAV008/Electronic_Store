package ua.nure.sharmenko.finaltask.listeners;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.entities.web.CriteriaSortingProducts;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MyHttpSessionListener implements HttpSessionListener {
    private static final Logger LOG = Logger.getLogger(HttpSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOG.debug("Session created.");
        HttpSession session = se.getSession();
        session.setAttribute("criteriaSortingProducts", new CriteriaSortingProducts());
    }
}
