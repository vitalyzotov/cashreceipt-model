package ru.vzotov.cashreceipt.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShiftInfoTest {
    @Test
    public void testConstructor() {
        //allow null arguments
        ShiftInfo empty = new ShiftInfo(null, null);

        ShiftInfo nonEmpty = new ShiftInfo(318L, "Иванов");

        assertThat(empty).isNotEqualTo(nonEmpty);
    }

}
