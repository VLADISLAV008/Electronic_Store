package ua.nure.sharmenko.finaltask.entities.db;

public class Category extends Entity {
    private static final long serialVersionUID = 3746501320736940127L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
