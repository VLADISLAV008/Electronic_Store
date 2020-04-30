package ua.nure.sharmenko.finaltask.database;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.entities.db.Category;
import ua.nure.sharmenko.finaltask.entities.db.Entity;
import ua.nure.sharmenko.finaltask.entities.db.Product;
import ua.nure.sharmenko.finaltask.exception.DBException;

import java.util.Comparator;
import java.util.List;

public final class Loader {
    private static final Logger LOG = Logger.getLogger(Loader.class);

    static public List<Category> loadCategories() {
        // Load categories from db
        List<Category> categories = null;
        try {
            categories = DBManager.getInstance().selectAllCategories();
            categories.sort(Comparator.comparing(Entity::getId));
            LOG.trace("List of categories is added to the request scope.");
        } catch (DBException e) {
            LOG.debug("Cannot select categories from DB.");
            LOG.debug(e.getMessage());
        }
        return categories;
    }

    static public List<Product> loadProducts(Long categoryId) {
        // Load products from db
        List<Product> products = null;
        if (categoryId != null) {
            try {
                products = DBManager.getInstance().selectProductsByCategory(categoryId);
                LOG.trace("List of products is added to the request scope.");

            } catch (DBException e) {
                LOG.debug("Cannot select products from DB.");
                LOG.debug(e.getMessage());
            }
        }
        return products;
    }
}
