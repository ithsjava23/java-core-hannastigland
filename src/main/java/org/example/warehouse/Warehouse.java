package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Warehouse instance = new Warehouse();
    //^statisk instans av warehouse-klassen, dvs 1 enda instans i programmet.

    private static final Map<UUID, ProductRecord> products = new HashMap<>();
    //^statisk hashmap som använder uuid som nycklar och productRecord som värden.
    private static final List<ProductRecord> productList = new ArrayList<>();
    //^arraylist som lagrar en lista över alla produkter.
    private String name;
    //^privat fält som innehåller namnet på lagret.

    private Warehouse() {
    }//^privat constructor. Singleton-klass, finns bara 1 instans av klassen
    //som fås med getInstance()-metoden.
    //Singleton pga ska inte finnas dubbletter av produkter eller uuid.
    public static Warehouse getInstance(String name) {
        Warehouse warehouse = getInstance();
        warehouse.setName(name);
        return warehouse;
    }//^returnerar också den enda instansen av warehouse och sätter lagrets namn.

    public static Warehouse getInstance() {
        productList.clear();
        products.clear();
        return instance;
    }//^metod som returnerar den enda instansen av warehouse och rensar
    //productList och products när den anropas.

    public boolean isEmpty() {
        return products.isEmpty();
    }//^returnerar true om lagerprodukterna är tomma, annars false.

    public void setName(String name) {
        this.name = name;
    }//^setter för namn.

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(productList);
    }//^returnerar en oföränderlig lista av produkter i lagret.

    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {
        verifyNewProduct(name, category);

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
    }//^metod som lägger till ny produkt i lagret, kollar först om
    //produkten redan finns & om priset är null eller ej,
    //lägger sedan till produkten i products och productList.
    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        if (!products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }
        products.get(uuid).setPrice(newPrice);
    }//^metod som uppdaterar priset för produkt med visst uuid.
    public Optional<ProductRecord> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));
    }//^metod som returnerar en optional med produkt med angivet uuid.

    public List<ProductRecord> getChangedProducts() {
        return products.values().stream()
                .filter(ProductRecord::hasChanged)
                .collect(Collectors.toList());
    }
    //^returnerar lista med alla produkter som ändrats.
    public Map <Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.values().stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }
    //^returnerar em map där produkternas kategori är
    //nycklar och listor av produkter i kategorierna är värden.
    public List<ProductRecord> getProductsBy(Category category) {
        return products.values().stream()
                .filter(product -> product.category().equals(category))
                .collect(Collectors.toList());
    }
    //^returnerar en lista av produkter i en viss kategori.
    private void verifyNewProduct(String name, Category category) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
    }
    //^kollar så produktnamn och kategori inte är null eller tom sträng
    //och kastar undantag om ogiltigt.

}