package ua.nure.sharmenko.finaltask.entities.db;

public class Product extends Entity {
    private static final long serialVersionUID = 5572981319216477583L;

    private String name;
    private Integer price;
    private Boolean available;
    private Integer categoryId;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", available=" + available +
                ", categoryId=" + categoryId +
                '}';
    }
}
