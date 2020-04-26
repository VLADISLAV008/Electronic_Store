package ua.nure.sharmenko.finaltask.servlets;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.constants.Path;
import ua.nure.sharmenko.finaltask.entities.web.CriteriaSortingProducts;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/mainPage")
public class MainPageServlet extends HttpServlet {
    private static final long serialVersionUID = 7776173346442089585L;
    private static final Logger LOG = Logger.getLogger(MainPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sortMethod = req.getParameter("sortMethod");
        LOG.trace("Sorting criterion - " + sortMethod);

        HttpSession session = req.getSession();
        CriteriaSortingProducts csp = (CriteriaSortingProducts) session.getAttribute("criteriaSortingProducts");
        csp.changeSelectedCriterion(sortMethod);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.MAIN_PAGE);
        requestDispatcher.forward(req, resp);
    }
}
