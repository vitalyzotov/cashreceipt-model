package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang.Validate;
import ru.vzotov.ddd.shared.ValueObject;

import java.util.Objects;

public class Address implements ValueObject<Address> {
    private String value;

    public Address(String value) {
        Validate.notNull(value);
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean sameValueAs(Address address) {
        return address != null && Objects.equals(value, address.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Address address = (Address) o;

        return sameValueAs(address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Address{" +
                value +
                '}';
    }

    protected Address() {
        // for Hibernate
    }
}
