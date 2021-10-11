package ru.vzotov.cashreceipt.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PurchaseCategoryTest {
    @Test
    public void testConstructor() {
        assertThat(catchThrowable(() -> {
            new PurchaseCategory(null, null);
        })).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);

        PurchaseCategory category = new PurchaseCategory(PurchaseCategoryId.nextId(), "Продукты");
        assertThat(category.name()).isEqualTo("Продукты");
    }

}
