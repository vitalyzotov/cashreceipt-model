package ru.vzotov.cashreceipt.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class AddressTest {
    @Test
    public void testConstructor() {
        assertThat(catchThrowable(() -> {
            new Address(null);
        })).as("Should not accept null arguments")
                .isInstanceOf(IllegalArgumentException.class);


        Address address = new Address("address");
        assertThat(address.value()).isEqualTo("address");
    }
}
