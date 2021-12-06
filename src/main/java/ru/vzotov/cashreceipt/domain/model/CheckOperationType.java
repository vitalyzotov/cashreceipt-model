package ru.vzotov.cashreceipt.domain.model;

//TODO: use enum

import ru.vzotov.ddd.shared.ValueObject;

/**
 * Значение реквизита "Признак расчета" для кассового чека (БСО).
 * Таблица 25
 */
public enum CheckOperationType implements ValueObject<CheckOperationType> {
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

    CheckOperationType(long numericValue) {
        this.numericValue = numericValue;
    }

    @Override
    public boolean sameValueAs(CheckOperationType other) {
        return this.equals(other);
    }

    public static CheckOperationType of(long numericValue) {
        for (CheckOperationType opt : CheckOperationType.values()) {
            if (opt.numericValue == numericValue) return opt;
        }
        throw new IllegalArgumentException("Unknown operation type");
    }
}
