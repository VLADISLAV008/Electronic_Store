package ua.nure.sharmenko.finaltask.entities.web;

import java.util.ArrayList;

public class CriteriaSortingProducts {
    private ArrayList<SortingCriterion> list;

    public CriteriaSortingProducts() {
        list = new ArrayList<>();
        list.add(new SortingCriterion("null", "Choose here", true));
        list.add(new SortingCriterion("az", "by name(a...z)", false));
        list.add(new SortingCriterion("za", "by name(z...a)", false));
        list.add(new SortingCriterion("cheap", "from cheap", false));
        list.add(new SortingCriterion("expensive", "from expensive", false));
        list.add(new SortingCriterion("novelty", "by novelty", false));
    }

    public void changeSelectedCriterion(String name) {
        for (SortingCriterion criterion : list) {
            if (criterion.isSelected()) {
                criterion.setSelected(false);
            }
            if (criterion.getName().equals(name)) {
                criterion.setSelected(true);
            }
        }
    }

    public ArrayList<SortingCriterion> getList() {
        return list;
    }
}
