package ru.vzotov.cashreceipt.domain.model;

import ru.vzotov.ddd.shared.ValueObject;

public enum ReceiptState implements ValueObject<ReceiptState> {
    NEW('N'),
    LOADED('L');

    private char symbol;

    ReceiptState(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }

    @Override
    public boolean sameValueAs(ReceiptState other) {
        return this.equals(other);
    }

    public static ReceiptState of(char symbol) {
        for (ReceiptState opt : ReceiptState.values()) {
            if (opt.symbol == symbol) return opt;
        }
        throw new IllegalArgumentException("Unknown receipt state");
    }

}
