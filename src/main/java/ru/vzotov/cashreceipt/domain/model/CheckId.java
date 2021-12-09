package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang.Validate;
import ru.vzotov.ddd.shared.ValueObject;
import ru.vzotov.domain.model.Money;
import ru.vzotov.fiscal.FiscalSign;

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

/**
 * Identifier of cash receipt
 */
public class CheckId implements ValueObject<CheckId> {

    private static final DateTimeFormatter ID_DATETIME_FORMAT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(YEAR, 4)
            .appendValue(MONTH_OF_YEAR, 2)
            .appendValue(DAY_OF_MONTH, 2)
            .appendValue(HOUR_OF_DAY, 2)
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendValue(SECOND_OF_MINUTE, 2)
            .toFormatter();

    /**
     * Max length is 75 characters
     */
    private String value;

    public CheckId(String value) {
        Validate.notNull(value);

        this.value = value;
    }

    public CheckId(LocalDateTime dateTime, Money sum, String fiscalDriveNumber, String fiscalDocumentNumber, FiscalSign fiscalSign, CheckOperationType operationType) {
        Validate.notNull(dateTime);
        Validate.notNull(sum);
        Validate.notNull(fiscalDriveNumber);
        Validate.notNull(fiscalDocumentNumber);
        Validate.notNull(fiscalSign);
        Validate.notNull(operationType);

        this.value = ID_DATETIME_FORMAT.format(dateTime) + // 14 chars
                '_' + // 1 char
                sum.rawAmount() + // 19 chars
                '_' + // 1 char
                fiscalDriveNumber + //16 chars
                '_' + // 1 char
                fiscalDocumentNumber + //10 chars
                '_' + // 1 char
                fiscalSign.value() + // 10 chars
                '_' + // 1 char
                operationType.numericValue(); // 1 char
    }

    public static CheckId ofQrCode(QRCodeData code) {
        return new CheckId(code.dateTime().value(), code.totalSum(), code.fiscalDriveNumber(), code.fiscalDocumentNumber(), code.fiscalSign(), code.operationType());
    }

    public String value() {
        return value;
    }

    @Override
    public boolean sameValueAs(CheckId that) {
        return that != null && Objects.equals(value, that.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckId that = (CheckId) o;
        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

    protected CheckId() {
        //for Hibernate
    }
}