package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    public static void main(String[] args) {
        Warehouse warehouse = Warehouse.getInstance("MyWarehouse");
        Category fruitCategory = new Category("Fruit");
        ProductRecord apple = warehouse.addProduct(null, "Apple", fruitCategory, BigDecimal.valueOf(1.99));
        ProductRecord banana = warehouse.addProduct(null, "Banana", fruitCategory, BigDecimal.valueOf(0.99));
        ProductRecord orange = warehouse.addProduct(null, "Orange", fruitCategory, BigDecimal.valueOf(1.49));
        List<ProductRecord> fruitProducts = warehouse.getProductsBy(fruitCategory);
    }

    private final String name;
    private final Map<UUID, ProductRecord> products = new HashMap<>();
    private final Set<UUID> changedProducts = new HashSet<>();

    private static Warehouse instance;

    private Warehouse(String name) {
        this.name = name;
    }
    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse("DefaultWarehouse");
        }
        return instance;
    }

    public static Warehouse getInstance(String name) {
        if (instance == null) {
            instance = new Warehouse(name);
        }
        return instance;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }

    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        ProductRecord productRecord = new ProductRecord(uuid, name, category, price);
        products.put(uuid, productRecord);
        return productRecord;
    }

    public void updateProductPrice(UUID productId, BigDecimal newPrice) {
        if (!products.containsKey(productId)) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }
        ProductRecord product = products.get(productId);
        product = new ProductRecord(product.uuid(), product.name(), product.category(), newPrice);
        products.put(productId, product);
        changedProducts.add(productId);
    }

    public List<ProductRecord> getChangedProducts() {
        return changedProducts.stream()
                .map(products::get)
                .collect(Collectors.toList());
    }

    public Map <Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.values().stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }

    public List<ProductRecord> getProductsBy(Category category) {
        return products.values().stream()
                .filter(product -> product.category().equals(category))
                .collect(Collectors.toList());
    }

    public Optional<ProductRecord> getProductById(UUID productId) {
        return Optional.ofNullable(products.get(productId));
    }
}
