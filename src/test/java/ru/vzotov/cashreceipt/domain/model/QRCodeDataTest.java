package ru.vzotov.cashreceipt.domain.model;

import org.junit.Test;
import ru.vzotov.domain.model.Money;
import ru.vzotov.fiscal.FiscalSign;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class QRCodeDataTest {
    @Test
    public void testConstructor() {
        assertThat(catchThrowable(() -> {
            new QRCodeData(null);
        })).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);

        QRCodeData qr = new QRCodeData("t=20150720T1638&s=9999999.00&fn=000110000105&i=12345678&fp=123456&n=2");
        assertThat(qr.fiscalDocumentNumber()).isEqualTo("12345678");
        assertThat(qr.fiscalDriveNumber()).isEqualTo("000110000105");
        assertThat(qr.operationType()).isEqualTo(2);
        assertThat(qr.fiscalSign()).isEqualTo(new FiscalSign(123456));
        assertThat(qr.totalSum()).isEqualTo(Money.kopecks(999999900L));
        assertThat(qr.dateTime().value()).isEqualTo(LocalDateTime.of(2015, Month.JULY, 20, 16, 38, 0, 0));

        assertThat(catchThrowable(() -> {
            new QRCodeData("t=20150720T1638&s=9999999.00&fn=000110000105&i=12345678&fp=123456&n=2&abc=unknown");
        })).as("Should not accept unknown parameters")
                .isInstanceOf(Exception.class);

        assertThat(catchThrowable(() -> {
            new QRCodeData("t=20150720T1638&s=9999A99.00&fn=000110000105&i=12345678&fp=123456&n=2");
        })).as("Should not accept invalid numbers")
                .isInstanceOf(Exception.class);
    }

    @Test
    public void testToString() {
        QRCodeData qr = new QRCodeData("t=20150720T163800&s=9999999&fn=000110000105&i=12345678&fp=123456&n=2");
        assertThat(qr.toString()).isEqualTo("t=20150720T1638&s=9999999.00&fn=000110000105&i=12345678&fp=123456&n=2");
    }

}
