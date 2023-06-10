package ru.vzotov.cashreceipt.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AddressTest {
    @Test
    public void testConstructor() {
        assertThatThrownBy(() -> new Address(null)).as("Should not accept null arguments")
                .isInstanceOf(NullPointerException.class);


        Address address = new Address("address");
        assertThat(address.value()).isEqualTo("address");
    }
}
