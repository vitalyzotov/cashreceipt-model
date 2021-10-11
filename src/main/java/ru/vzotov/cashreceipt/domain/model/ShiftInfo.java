package ru.vzotov.cashreceipt.domain.model;

import ru.vzotov.ddd.shared.ValueObject;

import java.util.Objects;

public class ShiftInfo implements ValueObject<ShiftInfo> {
    /**
     * Номер смены
     * <p>
     * Тег 1038
     * Тип UInt32
     * Пример: 323
     */
    private Long shiftNumber;

    /**
     * Тег 1021
     * <p>
     * Кассир
     * Строка(64)
     * <p>
     * "Селиверстов"
     */
    private String operator;

    public ShiftInfo(Long shiftNumber, String operator) {
        this.shiftNumber = shiftNumber;
        this.operator = operator;
    }

    public Long shiftNumber() {
        return shiftNumber;
    }

    public String operator() {
        return operator;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "No=" + shiftNumber +
                ", operator='" + operator + '\'' +
                '}';
    }

    @Override
    public boolean sameValueAs(ShiftInfo shiftInfo) {
        return shiftInfo != null && Objects.equals(shiftNumber, shiftInfo.shiftNumber) &&
                Objects.equals(operator, shiftInfo.operator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftInfo shiftInfo = (ShiftInfo) o;
        return sameValueAs(shiftInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shiftNumber, operator);
    }

    protected ShiftInfo() {
        // for Hibernate
    }
}
