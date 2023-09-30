package org.example.warehouse;

public class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public static Category of(String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            throw new IllegalArgumentException("Category name can't be null or empty.");
        }
        return new Category(categoryName);
    }
    //public static Object of(String test) {
      //  return null;
   // }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
