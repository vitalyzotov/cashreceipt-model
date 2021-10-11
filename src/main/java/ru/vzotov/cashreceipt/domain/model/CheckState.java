package ru.vzotov.cashreceipt.domain.model;

import ru.vzotov.ddd.shared.ValueObject;

public enum CheckState implements ValueObject<CheckState> {
    NEW('N'),
    LOADED('L');

    private char symbol;

    CheckState(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }

    @Override
    public boolean sameValueAs(CheckState other) {
        return this.equals(other);
    }

    public static CheckState of(char symbol) {
        for (CheckState opt : CheckState.values()) {
            if (opt.symbol == symbol) return opt;
        }
        throw new IllegalArgumentException("Unknown check state");
    }

}
