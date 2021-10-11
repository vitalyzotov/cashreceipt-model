package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang.Validate;
import ru.vzotov.ddd.shared.ValueObject;
import ru.vzotov.domain.model.Money;

import java.util.Objects;

public class PaymentInfo implements ValueObject<PaymentInfo> {
    private Money cash;
    private Money eCash; //239527 - сумма в копейках по карте

    public PaymentInfo(Money cash, Money eCash) {
        Validate.isTrue(cash != null || eCash != null);
        this.cash = cash;
        this.eCash = eCash;
    }

    public static PaymentInfo inCash(Money cash) {
        return new PaymentInfo(cash, null);
    }

    public static PaymentInfo byCard(Money eCash) {
        return new PaymentInfo(null, eCash);
    }

    public Money cash() {
        return cash;
    }

    public Money eCash() {
        return eCash;
    }

    @Override
    public String toString() {
        return "{" +
                "cash=" + cash +
                ", eCash=" + eCash +
                '}';
    }

    protected PaymentInfo() {
        // for Hibernate
    }

    @Override
    public boolean sameValueAs(PaymentInfo that) {
        return that != null
                && Objects.equals(cash, that.cash)
                && Objects.equals(eCash, that.eCash);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PaymentInfo that = (PaymentInfo) o;

        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cash, eCash);
    }
}
