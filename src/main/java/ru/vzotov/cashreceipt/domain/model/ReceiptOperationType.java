package ru.vzotov.cashreceipt.domain.model;

import ru.vzotov.ddd.shared.ValueObject;

/**
 * Значение реквизита "Признак расчета" для кассового чека (БСО).
 * Таблица 25
 */
public enum ReceiptOperationType implements ValueObject<ReceiptOperationType> {
    /**
     * Приход
     */
    INCOME(1),
    /**
     * Возврат прихода
     */
    INCOME_RETURN(2),
    /**
     * Расход
     */
    EXPENSE(3),

    /**
     * Возврат расхода
     */
    EXPENSE_RETURN(4);

    private final long numericValue;

    public long numericValue() {
        return numericValue;
    }

    ReceiptOperationType(long numericValue) {
        this.numericValue = numericValue;
    }

    @Override
    public boolean sameValueAs(ReceiptOperationType other) {
        return this.equals(other);
    }

    public static ReceiptOperationType of(long numericValue) {
        for (ReceiptOperationType opt : ReceiptOperationType.values()) {
            if (opt.numericValue == numericValue) return opt;
        }
        throw new IllegalArgumentException("Unknown operation type");
    }
}
