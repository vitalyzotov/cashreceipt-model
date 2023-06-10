package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang3.Validate;
import ru.vzotov.ddd.shared.ValueObject;

import java.util.Objects;
import java.util.UUID;

public class ReceiptSourceId implements ValueObject<ReceiptSourceId> {

    private String value;

    public ReceiptSourceId(String value) {
        Validate.notEmpty(value);
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static ReceiptSourceId nextId() {
        return new ReceiptSourceId(UUID.randomUUID().toString());
    }

    @Override
    public boolean sameValueAs(ReceiptSourceId that) {
        return that != null && Objects.equals(value, that.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptSourceId that = (ReceiptSourceId) o;
        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

    protected ReceiptSourceId() {
        // for ORM
    }

}
