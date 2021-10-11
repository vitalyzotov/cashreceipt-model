package ru.vzotov.cashreceipt.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MarketingTest {
    @Test
    public void testConstructor() {
        // allow null arguments
        Marketing m = new Marketing(null, null, null, null);
        assertThat(m).isEqualTo(Marketing.emptyMarketing());
    }

}
