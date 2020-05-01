package ua.nure.sharmenko.finaltask.servlets;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Content;
import ua.nure.sharmenko.finaltask.constants.Path;
import ua.nure.sharmenko.finaltask.database.Loader;
import ua.nure.sharmenko.finaltask.entities.db.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        req.setAttribute("content", Content.BASKET);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
