package ua.nure.sharmenko.finaltask.entities.web;

import ua.nure.sharmenko.finaltask.entities.db.Product;

import java.util.ArrayList;
import java.util.Comparator;

public class CriteriaSortingProducts {
    private ArrayList<SortingCriterion> list;

    public CriteriaSortingProducts() {
        list = new ArrayList<>();
        list.add(new SortingCriterion("null", "Choose here", true, null));
        list.add(new SortingCriterion("az", "by name(a...z)", false, Comparator.comparing(Product::getName)));
        list.add(new SortingCriterion("za", "by name(z...a)", false, (o1, o2) -> -o1.getName().compareTo(o2.getName())));
        list.add(new SortingCriterion("cheap", "from cheap", false, Comparator.comparing(Product::getPrice)));
        list.add(new SortingCriterion("expensive", "from expensive", false, (o1, o2) -> -o1.getPrice().compareTo(o2.getPrice())));
        list.add(new SortingCriterion("novelty", "by novelty", false, (o1, o2) -> -o1.getId().compareTo(o2.getId())));
    }

    public Comparator<Product> getComparator(String name) {
        for (SortingCriterion criterion : list) {
            if (criterion.getName().equals(name)) {
                return criterion.getComparator();
            }
        }
        return null;
    }

    public void changeSelectedCriterion(String name) {
        boolean exist = false;
        SortingCriterion prevSelected = null;
        for (SortingCriterion criterion : list) {
            if (criterion.isSelected()) {
                prevSelected = criterion;
                criterion.setSelected(false);
            }
            if (criterion.getName().equals(name)) {
                exist = true;
                criterion.setSelected(true);
            }
        }
        if (!exist && prevSelected != null) {
            prevSelected.setSelected(true);
        }
    }

    public ArrayList<SortingCriterion> getList() {
        return list;
    }
}
