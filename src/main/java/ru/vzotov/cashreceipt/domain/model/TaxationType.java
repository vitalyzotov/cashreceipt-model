package ru.vzotov.cashreceipt.domain.model;

//TODO: use enum

/**
 * Система налогообложения.
 * Таблица 9
 */
public abstract class TaxationType {

    /**
     * Общая
     */
    public static final long MASK_GENERAL = 0x1;

    /**
     * Упрощенная доход
     */
    public static final long MASK_SIMPLIFIED_PROFIT = 0x2;

    /**
     * Упрощенная доход минус расход
     */
    public static final long MASK_SIMPLIFIED_PROFIT_MINUS_EXPENSE = 0x4;

    /**
     * Единый налог на вмененный доход
     */
    public static final long MASK_SINGLE_TAX_ON_IMPUTED_INCOME = 0x8;

    /**
     * Единый сельскохозяйственный налог
     */
    public static final long MASK_AGRICULTURAL_TAX = 0x10;

    /**
     * Патентная система налогообложения
     */
    public static final long MASK_PATENT_TAX = 0x20;
}
