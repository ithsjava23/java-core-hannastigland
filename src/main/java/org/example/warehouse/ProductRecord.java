package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRecord {
    private UUID uuid;
    private String name;
    private Category category;
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
        return uuid;
    }

    public String name() {
        return name;
    }

    public Category category() {
        return category;
    }

    public BigDecimal price() {
        return price;
    }

    public void setPrice(BigDecimal modifiedPrice) {
        if (!this.price.equals(modifiedPrice)) {
            this.price = modifiedPrice;
            this.changed = true;
        }
    }
    public boolean hasChanged() {
        return changed;
    }
}
