package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang.Validate;
import ru.vzotov.ddd.shared.ValueObject;
import ru.vzotov.domain.model.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Позиция в кассовом чеке
 */
public class Item implements ValueObject<Item> {

    /**
     * Наименование предмета расчета
     * Тег 1030
     * Строка (128)
     */
    private String name;

    private Money price;

    /**
     * Количество предмета расчета
     * Тег 1023
     * Тип FVLN, 8 байт
     * DECIMAL(19,3)
     */
    private BigDecimal quantity;

    private Money sum;

    private Integer index;

    private PurchaseCategory category;

    public Item(String name, Money price, double quantity, Money sum, Integer index) {
        Validate.notNull(name);
        Validate.notNull(price);
        Validate.notNull(sum);
        Validate.notNull(index);
        Validate.isTrue(index >= 0, "Wrong index: ", index);
        Validate.isTrue(quantity > 0.00d, "Wrong quantity: ", quantity);

        this.name = name;
        this.price = price;
        this.quantity = BigDecimal.valueOf(quantity).setScale(3, RoundingMode.HALF_UP);
        this.sum = sum;
        this.index = index;
    }

    public Item(String name, Money price, double quantity, Money sum, Integer index, PurchaseCategory category) {
        this(name, price, quantity, sum, index);
        this.category = category;
    }

    public String name() {
        return name;
    }

    public Money price() {
        return price;
    }

    public double quantity() {
        return quantity.doubleValue();
    }

    public Money sum() {
        return sum;
    }

    public Integer index() {
        return index;
    }

    public PurchaseCategory category() {
        return category;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", sum=" + sum +
                ", index=" + index +
                ", category=" + category +
                ", id(surrogate)=" + id +
                '}';
    }

    @Override
    public boolean sameValueAs(Item other) {
        return other != null &&
                Objects.equals(name, other.name) &&
                Objects.equals(price, other.price) &&
                Objects.compare(quantity, other.quantity, BigDecimal::compareTo) == 0 &&
                Objects.equals(sum, other.sum) &&
                Objects.equals(index, other.index) &&
                Objects.equals(category, other.category)
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Item item = (Item) o;

        return sameValueAs(item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity, sum, index, category);
    }

    protected Item() {
        // for Hibernate
    }

    /**
     * Auto-generated surrogate key
     */
    private Long id;

}
