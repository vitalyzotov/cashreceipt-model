package ru.vzotov.cashreceipt.domain.model;

import ru.vzotov.ddd.shared.ValueObject;
import ru.vzotov.domain.model.Money;

import java.util.Objects;

public class Marketing implements ValueObject<Marketing> {

    private static final Marketing EMPTY_MARKETING = new Marketing(null, null, null, null);

    /**
     * Наценка (торговая наценка, mark-up) на реализуемые товары — торговая надбавка к цене реализуемого товара,
     * доход розничного продавца, разница между розничной и оптовой ценой товаров, необходимая для покрытия издержек
     * и получения средней прибыли торговыми предприятиями.
     */
    private Money markup;

    /**
     * Сумма наценки
     */
    private Money markupSum;

    private Money discount;

    private Money discountSum;

    public Marketing(Money markup, Money markupSum, Money discount, Money discountSum) {
        this.markup = markup;
        this.markupSum = markupSum;
        this.discount = discount;
        this.discountSum = discountSum;
    }

    public static Marketing emptyMarketing() {
        return EMPTY_MARKETING;
    }

    public Money markup() {
        return markup;
    }

    public Money markupSum() {
        return markupSum;
    }

    public Money discount() {
        return discount;
    }

    public Money discountSum() {
        return discountSum;
    }

    @Override
    public boolean sameValueAs(Marketing other) {
        return other != null
                && Objects.equals(markup, other.markup)
                && Objects.equals(markupSum, other.markupSum)
                && Objects.equals(discount, other.discount)
                && Objects.equals(discountSum, other.discountSum)
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Marketing marketing = (Marketing) o;

        return sameValueAs(marketing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(markup, markupSum, discount, discountSum);
    }

    protected Marketing() {
        // for hibernate
    }
}
