package controller.entities.db;

import java.util.Objects;

public class ProductOrderInfo {
    private long productId;
    private int quantity;

    public ProductOrderInfo(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductOrderInfo() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrderInfo that = (ProductOrderInfo) o;
        return productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
