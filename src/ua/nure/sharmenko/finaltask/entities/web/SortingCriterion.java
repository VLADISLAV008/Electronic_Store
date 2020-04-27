package ua.nure.sharmenko.finaltask.entities.web;

import ua.nure.sharmenko.finaltask.entities.db.Product;

import java.util.Comparator;

public class SortingCriterion {
    private String name;
    private String webText;
    private boolean selected;

    private Comparator<Product> comparator;

    public SortingCriterion(String name, String webText, boolean selected, Comparator<Product> comparator) {
        this.name = name;
        this.webText = webText;
        this.selected = selected;
        this.comparator = comparator;
    }

    public Comparator<Product> getComparator() {
        return comparator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebText() {
        return webText;
    }

    public void setWebText(String webText) {
        this.webText = webText;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
