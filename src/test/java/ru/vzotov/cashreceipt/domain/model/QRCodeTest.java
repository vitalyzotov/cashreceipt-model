package ru.vzotov.cashreceipt.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class QRCodeTest {
    @Test
    public void testConstructor() {
        assertThat(catchThrowable(() -> {
            new QRCode(null, null);
        })).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);

        assertThat(catchThrowable(() -> {
            new QRCode(null);
        })).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);

        new QRCode(
                new QRCodeData("t=20150720T1638&s=9999999.00&fn=000110000105&i=12345678&fp=123456&n=2"),
                ReceiptState.LOADED
        );
    }

}
