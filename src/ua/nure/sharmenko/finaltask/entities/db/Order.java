package ua.nure.sharmenko.finaltask.entities.db;

import org.apache.log4j.Logger;
import ua.nure.sharmenko.finaltask.database.DBManager;
import ua.nure.sharmenko.finaltask.exception.DBException;
import ua.nure.sharmenko.finaltask.servlets.BasketServlet;

import java.util.ArrayList;
import java.util.List;

public class Order extends Entity {
    private static final long serialVersionUID = 8310183578704202886L;
    private static final Logger LOG = Logger.getLogger(Order.class);

    private Long userId;
    private Long statusId;
    private int bill;
    private List<ProductOrderInfo> orderInfo;

    public Order() {
        orderInfo = new ArrayList<>();
    }

    public int getNumberProducts() {
        int number = 0;
        for (ProductOrderInfo p : orderInfo) {
            number += p.getQuantity();
        }
        return number;
    }

    public Product getProductById(long productId) {
        LOG.debug("Load ordered product by id from db");
        Product product = null;
        try {
            product = DBManager.getInstance().findProductById(productId);
        } catch (DBException e) {
            LOG.debug(e.getMessage());
        }
        return product;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public List<ProductOrderInfo> getOrderInfo() {
        return orderInfo;
    }
}
