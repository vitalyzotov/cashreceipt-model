package ru.vzotov.cashreceipt.domain.model;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class QRCodeDateTimeTest {
    @Test
    public void testConstructor() {
        assertThat(catchThrowable(() -> {
            new QRCodeDateTime((String) null);
        })).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);

        assertThat(catchThrowable(() -> {
            new QRCodeDateTime((LocalDateTime) null);
        })).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);

        QRCodeDateTime fromString = new QRCodeDateTime("20180123T0821");
        QRCodeDateTime fromDate = new QRCodeDateTime(LocalDateTime.of(2018, Month.JANUARY, 23, 8, 21, 0, 0));
        assertThat(fromString).isEqualTo(fromDate);
    }

    @Test
    public void testToStringWithSeconds() {
        QRCodeDateTime fromDate = new QRCodeDateTime(LocalDateTime.of(2018, Month.JANUARY, 23, 8, 21, 50, 0));
        assertThat(fromDate.toString()).isEqualTo("20180123T082150");
    }

    @Test
    public void testToStringWithoutSeconds() {
        QRCodeDateTime fromDate = new QRCodeDateTime(LocalDateTime.of(2018, Month.JANUARY, 23, 8, 21, 00, 0));
        assertThat(fromDate.toString()).isEqualTo("20180123T0821");
    }

}
