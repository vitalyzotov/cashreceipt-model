package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang3.Validate;
import ru.vzotov.ddd.shared.ValueObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

public class QRCodeDateTime implements ValueObject<QRCodeDateTime> {

    private static DateTimeFormatter QRCODE_DATE_FORMAT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(YEAR, 4)
            .appendValue(MONTH_OF_YEAR, 2)
            .appendValue(DAY_OF_MONTH, 2)
            .appendLiteral('T')
            .appendValue(HOUR_OF_DAY, 2)
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendValue(SECOND_OF_MINUTE, 2)
            .optionalEnd()
            .toFormatter();

    private static DateTimeFormatter QRCODE_DATE_FORMAT_SHORT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(YEAR, 4)
            .appendValue(MONTH_OF_YEAR, 2)
            .appendValue(DAY_OF_MONTH, 2)
            .appendLiteral('T')
            .appendValue(HOUR_OF_DAY, 2)
            .appendValue(MINUTE_OF_HOUR, 2)
            .toFormatter();

    private static DateTimeFormatter QRCODE_ISO_FORMAT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(YEAR, 4)
            .appendLiteral('-')
            .appendValue(MONTH_OF_YEAR, 2)
            .appendLiteral('-')
            .appendValue(DAY_OF_MONTH, 2)
            .appendLiteral('T')
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .toFormatter();

    private LocalDateTime value;

    /**
     * @param value date/time – дата и время осуществления расчета в формате ГГГГММДДТЧЧММ. Некоторые добавляют секунды.
     */
    public QRCodeDateTime(String value) {
        Validate.notNull(value);
        this.value = LocalDateTime.parse(value, QRCODE_DATE_FORMAT);
    }

    public QRCodeDateTime(LocalDateTime value) {
        Validate.notNull(value);
        this.value = value;
    }

    public LocalDateTime value() {
        return value;
    }

    public String toISOString() {
        return QRCODE_ISO_FORMAT.format(this.value);
    }

    @Override
    public boolean sameValueAs(QRCodeDateTime other) {
        return other != null && Objects.equals(value, other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final QRCodeDateTime that = (QRCodeDateTime) o;

        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return this.value.getSecond() == 0 ? QRCODE_DATE_FORMAT_SHORT.format(this.value) :
                QRCODE_DATE_FORMAT.format(this.value);
    }

    protected QRCodeDateTime() {
        // for Hibernate
    }
}
