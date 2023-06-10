package ru.vzotov.cashreceipt.domain.model;

import org.junit.jupiter.api.Test;
import ru.vzotov.domain.model.Money;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductsTest {
    @Test
    public void testConstructor() {
        assertThatThrownBy(() -> new Products(null, null, null))
                .as("Should not accept null arguments").isInstanceOf(Exception.class);

        Products p = new Products(Collections.emptyList(), Collections.emptyList(), Money.kopecks(0));
        assertThat(p.totalSum().rawAmount()).isEqualTo(0);
    }

}
