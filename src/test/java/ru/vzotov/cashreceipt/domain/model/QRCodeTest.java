package ru.vzotov.cashreceipt.domain.model;

import org.junit.jupiter.api.Test;
import ru.vzotov.person.domain.model.PersonId;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QRCodeTest {
    @Test
    public void testConstructor() {
        assertThatThrownBy(() -> {
            new QRCode(null, null, null);
        }).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);

        assertThatThrownBy(() -> {
            new QRCode(null, null, null);
        }).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);

        new QRCode(
                new ReceiptId("id"),
                new QRCodeData("t=20150720T1638&s=9999999.00&fn=000110000105&i=12345678&fp=123456&n=2"),
                ReceiptState.LOADED,
                PersonId.nextId()
        );
    }

}
