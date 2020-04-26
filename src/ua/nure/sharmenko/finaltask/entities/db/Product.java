package ua.nure.sharmenko.finaltask.entities.db;

public class Product extends Entity {
    private static final long serialVersionUID = 5572981319216477583L;

    private String name;
    private Integer price;
    private Integer amount;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
