package ru.vzotov.cashreceipt.domain.model;

import org.junit.Test;
import ru.vzotov.domain.model.Money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ItemTest {
    @Test
    public void testConstructor() {
        assertThat(catchThrowable(() -> {
            new Item(null, null, 0.0d, null, null);
        })).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);

        assertThat(catchThrowable(() -> {
            new Item("item name", Money.kopecks(1), 2d, Money.kopecks(2), -1);
        })).as("Should not accept negative index")
                .isInstanceOf(Exception.class);

        new Item("item name", Money.kopecks(1), 2d, Money.kopecks(2), 0);
    }

}
