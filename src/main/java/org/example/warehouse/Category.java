package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private final String name;
    private static final Map<String, Category> categoryMap = new HashMap<>();
    private static String upperCase(String text) {
        if (text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
    private Category(String name) {
        this.name = name;
    }
    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }

        Category category = categoryMap.get(name);
        if (category != null) {
            return category;
        }

        Category newCategory = new Category(upperCase(name));
        categoryMap.put(name, newCategory);
        return newCategory;
    }
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }
}
