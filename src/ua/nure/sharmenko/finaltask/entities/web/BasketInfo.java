package ua.nure.sharmenko.finaltask.entities.web;

import ua.nure.sharmenko.finaltask.constants.Names;
import ua.nure.sharmenko.finaltask.entities.db.Order;

public final class BasketInfo {

    public static String getBasketInfo(Order order) {
        if (order.getBill() == 0) {
            return Names.EMPTY_BASKET;
        } else {
            int count = order.getNumberProducts();
            String info = Names.IN_YOUR_BASKET + count + " ";
            if (count > 1) {
                info = info + Names.PRODUCTS;
            } else {
                info = info + Names.PRODUCT;
            }
            info = info + " " + order.getBill() + " " + Names.CURRENCY;
            return info;
        }
    }
}
