package ru.vzotov.cashreceipt.domain.model;

import org.junit.jupiter.api.Test;
import ru.vzotov.domain.model.Money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PaymentInfoTest {
    @Test
    public void testConstructor() {
        assertThatThrownBy(() -> new PaymentInfo(null, null))
                .as("Should not accept both null arguments").isInstanceOf(Exception.class);

        new PaymentInfo(Money.kopecks(0), null);
        new PaymentInfo(null, Money.kopecks(0));

        PaymentInfo p = new PaymentInfo(Money.kopecks(0), Money.kopecks(0));
        assertThat(p.cash().rawAmount()).isEqualTo(0);
        assertThat(p.eCash().rawAmount()).isEqualTo(0);
    }

    @Test
    public void inCash() {
        PaymentInfo info = PaymentInfo.inCash(Money.kopecks(1));
        assertThat(info.eCash()).isNull();
        assertThat(info.cash().rawAmount()).isEqualTo(1);
    }

    @Test
    public void byCard() {
        PaymentInfo info = PaymentInfo.byCard(Money.kopecks(1));
        assertThat(info.cash()).isNull();
        assertThat(info.eCash().rawAmount()).isEqualTo(1);
    }
}
