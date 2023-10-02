package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Warehouse instance = new Warehouse();

    private static Map<UUID, ProductRecord> products = new HashMap<>();
    private static ArrayList<ProductRecord> productList = new ArrayList<>();
    private String name;

    private Warehouse() {
    }

    public static Warehouse getInstance() {
        productList.clear();
        products.clear();
        return instance;
    }

    public static Warehouse getInstance(String name) {
        Warehouse warehouse = getInstance();
        warehouse.setName(name);
        return warehouse;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(productList);
    }

    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {
        verifyInput(name, category);

        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        if (price == null) {
            price = BigDecimal.ZERO;
        }
        ProductRecord productRecord = new ProductRecord(uuid, name, category, price);
        products.put(uuid, productRecord);
        productList.add(productRecord);
        return productRecord;
    }
    public Optional<ProductRecord> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));
    }
    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        if (!products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }
        products.get(uuid).setPrice(newPrice);
    }

    public List<ProductRecord> getChangedProducts() {
        return products.values().stream()
                .filter(ProductRecord::hasChanged)
                .collect(Collectors.toList());
    }

    public Map <Category, List<ProductRecord>> getProductsGroupedByCategories() {
        Map<Category, List<ProductRecord>> productGroups = new HashMap<>();
        for (ProductRecord product : products.values()) {
            productGroups.computeIfAbsent(product.category(), k -> new ArrayList<>()).add(product);
        }
        return Collections.unmodifiableMap(productGroups);
    }

    public List<ProductRecord> getProductsBy(Category category) {
        List<ProductRecord> categorizedProductList = new ArrayList<>();
        for (ProductRecord product : products.values()) {
            if (product.category().equals(category)) {
                categorizedProductList.add(product);
            }
        }
        return Collections.unmodifiableList(categorizedProductList);
    }
    private void verifyInput(String name, Category category) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
    }

}