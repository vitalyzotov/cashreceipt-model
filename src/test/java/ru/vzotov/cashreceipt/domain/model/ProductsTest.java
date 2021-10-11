package ru.vzotov.cashreceipt.domain.model;

import org.junit.Test;
import ru.vzotov.domain.model.Money;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ProductsTest {
    @Test
    public void testConstructor() {
        assertThat(catchThrowable(() -> {
            new Products(null, null, null);
        })).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);

        Products p = new Products(Collections.emptyList(), Collections.emptyList(), Money.kopecks(0));
        assertThat(p.totalSum().rawAmount()).isEqualTo(0);
    }

}
