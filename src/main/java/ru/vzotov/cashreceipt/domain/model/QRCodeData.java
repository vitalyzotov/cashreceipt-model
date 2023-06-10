package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang3.Validate;
import ru.vzotov.ddd.shared.ValueObject;
import ru.vzotov.domain.model.Money;
import ru.vzotov.fiscal.FiscalSign;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Objects;

/**
 * Данные QR-кода должны представлять собой текстовую строку из латинских букв, цифр и символов-
 * разделителей "=" и "&".
 * Текст должен быть представлен в кодировке CP866.
 * Структура данных, помещаемых в строку QR-кода, состоит из шести полей:
 * <p>
 * - t=<date/time – дата и время осуществления расчета в формате ГГГГММДДТЧЧММ>
 * - s=<сумма расчета в рублях и копейках, разделенных точкой>
 * - fn=<заводской номер фискального накопителя>
 * - i=<порядковый номер фискального документа, нулями не дополняется>
 * - fp=<фискальный признак документа, нулями не дополняется>
 * - n=<признак расчета>.
 * <p>
 * Пример строки QR-кода: t=20150720T1638&s=9999999.00&fn=000110000105&i=12345678&fp=123456&n=2.
 */
public class QRCodeData implements ValueObject<QRCodeData> {

    private QRCodeDateTime dateTime;

    private Money totalSum;

    private String fiscalDriveNumber;

    private String fiscalDocumentNumber;

    private FiscalSign fiscalSign;

    private ReceiptOperationType operationType;

    public QRCodeData(QRCodeDateTime dateTime, Money totalSum, String fiscalDriveNumber, String fiscalDocumentNumber, FiscalSign fiscalSign, ReceiptOperationType operationType) {
        Validate.notNull(dateTime);
        Validate.notNull(totalSum);
        Validate.notNull(fiscalDriveNumber);
        Validate.notNull(fiscalDocumentNumber);
        Validate.notNull(fiscalSign);
        Validate.notNull(operationType);

        this.dateTime = dateTime;
        this.totalSum = totalSum;
        this.fiscalDriveNumber = fiscalDriveNumber;
        this.fiscalDocumentNumber = fiscalDocumentNumber;
        this.fiscalSign = fiscalSign;
        this.operationType = operationType;
    }

    public QRCodeData(String data) {
        Validate.notNull(data);
        Validate.isTrue(data.matches("^([\\w]+(=[\\w.]*)?(&[\\w]+(=[\\w.]*)?)*)?$"));

        final String[] parts = data.split("&");
        for (String part : parts) {
            final String[] pair = part.split("=");
            final String partName = pair[0];
            final String partValue = pair[1];
            switch (partName) {
                case "t" -> dateTime = new QRCodeDateTime(partValue);
                case "s" -> {
                    final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setDecimalSeparator('.');
                    final DecimalFormat format = new DecimalFormat("###.##", symbols);
                    format.setParseBigDecimal(true);
                    final ParsePosition index = new ParsePosition(0);
                    BigDecimal money = ((BigDecimal) format.parseObject(partValue, index))
                            .setScale(2, RoundingMode.HALF_UP);
                    if (index.getIndex() != partValue.length())
                        throw new IllegalArgumentException("Invalid 's' parameter format: " + partValue);
                    totalSum = Money.rubles(money.doubleValue());
                }
                case "fn" -> fiscalDriveNumber = partValue;
                case "i" -> fiscalDocumentNumber = partValue;
                case "fp" -> fiscalSign = new FiscalSign(partValue);
                case "n" -> operationType = ReceiptOperationType.of(Long.parseLong(partValue));
                default ->
                        throw new IllegalArgumentException("Unknown part name " + partName + " with value " + partValue);
            }
        }
    }

    public QRCodeDateTime dateTime() {
        return dateTime;
    }

    public Money totalSum() {
        return totalSum;
    }

    public String fiscalDriveNumber() {
        return fiscalDriveNumber;
    }

    public String fiscalDocumentNumber() {
        return fiscalDocumentNumber;
    }

    public FiscalSign fiscalSign() {
        return fiscalSign;
    }

    public ReceiptOperationType operationType() {
        return operationType;
    }

    @Override
    public boolean sameValueAs(QRCodeData that) {
        return that != null && Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(totalSum, that.totalSum) &&
                Objects.equals(fiscalDriveNumber, that.fiscalDriveNumber) &&
                Objects.equals(fiscalDocumentNumber, that.fiscalDocumentNumber) &&
                Objects.equals(fiscalSign, that.fiscalSign) &&
                Objects.equals(operationType, that.operationType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final QRCodeData that = (QRCodeData) o;
        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, totalSum, fiscalDriveNumber, fiscalDocumentNumber, fiscalSign, operationType);
    }

    @Override
    public String toString() {
        return
                "t=" + dateTime +
                        "&s=" + String.format(Locale.US, "%.2f", totalSum.amount().doubleValue()) +
                        "&fn=" + fiscalDriveNumber +
                        "&i=" + fiscalDocumentNumber +
                        "&fp=" + fiscalSign.value() +
                        "&n=" + operationType.numericValue();
    }

    protected QRCodeData() {
        // for Hibernate
    }
}
