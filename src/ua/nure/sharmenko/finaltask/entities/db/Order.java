package ua.nure.sharmenko.finaltask.entities.db;

import java.util.ArrayList;
import java.util.List;

public class Order extends Entity {
    private static final long serialVersionUID = 8310183578704202886L;

    private Long userId;
    private Long statusId;
    private int bill;
    private List<Long> productsId;
    private List<Integer> quantities;

    public Order() {
        productsId = new ArrayList<>();
        quantities = new ArrayList<>();
    }

    public int getNumberProducts() {
        int number = 0;
        for (int el : quantities) {
            number += el;
        }
        return number;
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

    public List<Integer> getQuantities() {
        return quantities;
    }

    public List<Long> getProductsId() {
        return productsId;
    }
}
