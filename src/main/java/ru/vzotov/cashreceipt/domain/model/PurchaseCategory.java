package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang.Validate;
import ru.vzotov.ddd.shared.AggregateRoot;
import ru.vzotov.ddd.shared.Entity;
import ru.vzotov.person.domain.model.PersonId;

import java.util.Objects;

@AggregateRoot
public class PurchaseCategory implements Entity<PurchaseCategory> {

    private PurchaseCategoryId categoryId;

    private PersonId owner;

    private String name;

    private PurchaseCategoryId parentId;

    public PurchaseCategory(PurchaseCategoryId categoryId, PersonId owner, String name) {
        this(categoryId, owner, name, null);
    }

    public PurchaseCategory(PurchaseCategoryId categoryId, PersonId owner, String name, PurchaseCategoryId parentId) {
        Validate.notEmpty(name);
        Validate.notNull(owner);
        this.categoryId = categoryId;
        this.owner = owner;
        this.name = name;
        this.parentId = parentId;
    }

    public PurchaseCategoryId categoryId() {
        return categoryId;
    }

    public PersonId owner() {
        return owner;
    }

    public PurchaseCategoryId parentId() {
        return parentId;
    }

    public String name() {
        return name;
    }

    public void rename(String name) {
        Validate.notEmpty(name);
        this.name = name;
    }

    @Override
    public boolean sameIdentityAs(PurchaseCategory other) {
        return this.categoryId.sameValueAs(other.categoryId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PurchaseCategory that = (PurchaseCategory) o;
        return sameIdentityAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId);
    }

    @Override
    public String toString() {
        return name;
    }

    protected PurchaseCategory() {
        // for Hibernate
    }

    private Long id; // surrogate key

}
