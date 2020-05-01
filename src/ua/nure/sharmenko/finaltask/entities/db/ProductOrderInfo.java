package ua.nure.sharmenko.finaltask.entities.db;

public class ProductOrderInfo {
    private long productId;
    private int quantity;

    public ProductOrderInfo(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
