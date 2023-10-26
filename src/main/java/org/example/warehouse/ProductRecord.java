package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRecord {
    private final UUID uuid;
    private final String name;
    private final Category category;
    private BigDecimal price;
    private boolean changed;

    public ProductRecord(UUID uuid, String name, Category category, BigDecimal price) {
        this.uuid = uuid;
        this.name = name;
        this.category = category;
        this.price = price;
        this.changed = false;
    }
    public UUID uuid() {
        return this.uuid;
    }

    public Category category() {
        return this.category;
    }

    public BigDecimal price() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        if (!this.price.equals(price)) {
            this.price = price;
            this.changed = true;
        }
    }
    public boolean hasChanged() {
        return changed;
    }
}
