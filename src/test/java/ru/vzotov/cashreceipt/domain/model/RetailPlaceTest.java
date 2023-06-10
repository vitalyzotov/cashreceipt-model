package ru.vzotov.cashreceipt.domain.model;

import org.junit.jupiter.api.Test;
import ru.vzotov.fiscal.Inn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RetailPlaceTest {
    @Test
    public void testConstructor() {
        assertThatThrownBy(() -> new RetailPlace(null, null, null, null))
                .as("Should not accept null arguments for userInn").isInstanceOf(Exception.class);

        RetailPlace place = new RetailPlace(null, new Inn("2310031475"), null, null);
        assertThat(place.address()).isNull();
    }

}
