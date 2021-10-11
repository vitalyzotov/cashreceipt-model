package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang.Validate;
import ru.vzotov.ddd.shared.ValueObject;
import ru.vzotov.fiscal.Inn;

import java.util.Objects;

public class RetailPlace implements ValueObject<RetailPlace> {

    /**
     * Кассир
     * Тег 1021
     * Строка (64)
     * По факту приходят более длинные значения, поэтому храним 128 символов
     */
    private String user; //

    /**
     * ИНН торгового предприятия, выдавшего чек.
     * ИНН пользователя.
     * Тег 1018
     * Строка (12)
     * Если ИНН имеет длину меньше 12 цифр, то он дополняется справа пробелами
     * Пример: "2310031475"
     */
    private Inn userInn;

    /**
     * адрес расчетов
     * Тег 1009
     * Строка (256)
     */
    private Address address;

    /**
     * Системы налогообложения
     * Тег 1062
     * @see TaxationType
     */
    private Long taxationType; //TODO: переделать на ValueObject

    public RetailPlace(String user, Inn userInn, Address address, Long taxationType) {
        Validate.notNull(userInn);
        this.user = user;
        this.userInn = userInn;
        this.address = address;
        this.taxationType = taxationType;
    }

    public String user() {
        return user;
    }

    public Inn userInn() {
        return userInn;
    }

    public Address address() {
        return address;
    }

    public Long taxationType() {
        return taxationType;
    }

    @Override
    public String toString() {
        return "RetailPlace{" +
                "user='" + user + '\'' +
                ", userInn=" + userInn +
                ", address=" + address +
                ", taxationType=" + taxationType +
                '}';
    }

    @Override
    public boolean sameValueAs(RetailPlace that) {
        return that != null
                && Objects.equals(user, that.user)
                && Objects.equals(userInn, that.userInn)
                && Objects.equals(address, that.address)
                && Objects.equals(taxationType, that.taxationType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final RetailPlace that = (RetailPlace) o;

        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, userInn, address, taxationType);
    }

    protected RetailPlace() {
        // for Hibernate
    }
}
