package ru.vzotov.cashreceipt.domain.model;

import ru.vzotov.ddd.shared.ValueObject;
import ru.vzotov.domain.model.Money;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Products implements ValueObject<Products> {
    private List<Item> items = Collections.emptyList();
    private List<Item> stornoItems = Collections.emptyList();
    private Money totalSum; // ": 65624 - ИТОГ (в копейках)

    public Products(final List<Item> items, final List<Item> stornoItems, Money totalSum) {
        Objects.requireNonNull(items);
        Objects.requireNonNull(stornoItems);
        Objects.requireNonNull(totalSum);

        this.items = items;
        this.stornoItems = stornoItems;
        this.totalSum = totalSum;
    }

    public List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public List<Item> stornoItems() {
        return Collections.unmodifiableList(stornoItems);
    }

    public Money totalSum() {
        return totalSum;
    }

    @Override
    public String toString() {
        return "Products{" +
                "items=" + items +
                ", stornoItems=" + stornoItems +
                ", totalSum=" + totalSum +
                '}';
    }

    @Override
    public boolean sameValueAs(Products other) {
        return other != null &&
                Objects.equals(items, other.items) &&
                Objects.equals(stornoItems, other.stornoItems) &&
                Objects.equals(totalSum, other.totalSum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Products products = (Products) o;

        return sameValueAs(products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, stornoItems, totalSum);
    }

    protected Products() {
        // for Hibernate
    }
}
