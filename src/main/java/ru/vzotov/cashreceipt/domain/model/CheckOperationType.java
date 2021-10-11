package ru.vzotov.cashreceipt.domain.model;

//TODO: use enum

/**
 * Значение реквизита "Признак расчета" для кассового чека (БСО).
 * Таблица 25
 */
public abstract class CheckOperationType {

    /**
     * Приход
     */
    public static final Long INCOME = 1L;

    /**
     * Возврат прихода
     */
    public static final Long INCOME_RETURN = 2L;

    /**
     * Расход
     */
    public static final Long EXPENSE = 3L;

    /**
     * Возврат расхода
     */
    public static final Long EXPENSE_RETURN = 4L;
}
