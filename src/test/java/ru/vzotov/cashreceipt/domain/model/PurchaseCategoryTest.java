package ru.vzotov.cashreceipt.domain.model;

import org.junit.jupiter.api.Test;
import ru.vzotov.person.domain.model.PersonId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PurchaseCategoryTest {

    private static final PersonId PERSON_ID = new PersonId("c483a33e-5e84-4d4c-84fe-4edcb5cc0fd2");

    @Test
    public void testConstructor() {
        assertThatThrownBy(() -> new PurchaseCategory(null, null, null))
                .as("Should not accept null arguments").isInstanceOf(Exception.class);

        PurchaseCategory category = new PurchaseCategory(PurchaseCategoryId.nextId(), PERSON_ID, "Продукты");
        assertThat(category.name()).isEqualTo("Продукты");
    }

}
