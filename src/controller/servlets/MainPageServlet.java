package controller.servlets;

import controller.constants.Content;
import controller.constants.Path;
import controller.entities.db.Category;
import org.apache.log4j.Logger;
import controller.database.Loader;
import ua.nure.sharmenko.finaltask.entities.db.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/mainPage")
public class MainPageServlet extends HttpServlet {
    private static final long serialVersionUID = 7776173346442089585L;
    private static final Logger LOG = Logger.getLogger(MainPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Load categories from db
        LOG.debug("Try to select categories from DB.");
        List<Category> categories = Loader.loadCategories();
        req.setAttribute("categories", categories);

        String content = (String) session.getAttribute("content");
        if (content == null || Content.PLACE_ORDER_RESULT_CONTENT.equals(content)) {
            session.setAttribute("content", Content.PRODUCTS_CONTENT);
            content = Content.PRODUCTS_CONTENT;
        }

        LOG.debug("Main page content = " + content);

        if (Content.PRODUCTS_CONTENT.equals(content)) {
            req.getRequestDispatcher("/products").forward(req, resp);
        } else {
            req.getRequestDispatcher(Path.MAIN_PAGE).forward(req, resp);
        }
    }
}
