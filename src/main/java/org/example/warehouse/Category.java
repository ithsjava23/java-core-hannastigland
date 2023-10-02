package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private final String name;

    private static final Map<String, Category> categoryInfo = new HashMap<>();

    private Category(String name) {
        this.name = name;
    }
//^allt ok
    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }

        Category infoCategory = categoryInfo.get(name);
        if (infoCategory != null) {
            return infoCategory;
        }

        Category newCategory = new Category(startWithCapital(name));
        categoryInfo.put(name, newCategory);
        return newCategory;
    }

    private static String startWithCapital(String text) {
        if (text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    public String getName() {
        return name;
    }
//^getter ok, byta plats??

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
