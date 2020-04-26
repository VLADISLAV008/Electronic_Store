package ua.nure.sharmenko.finaltask.entities.web;

public class SortingCriterion {
    private String name;
    private String webText;
    private boolean selected;

    public SortingCriterion(String name, String webText, boolean selected) {
        this.name = name;
        this.webText = webText;
        this.selected = selected;
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
