package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang.Validate;
import ru.vzotov.ddd.shared.DomainEvent;

import java.util.Objects;

public class QRCodeCreatedEvent implements DomainEvent<QRCodeCreatedEvent> {

    private final CheckId receiptId;

    public QRCodeCreatedEvent(CheckId receiptId) {
        Validate.notNull(receiptId);
        this.receiptId = receiptId;
    }

    public CheckId receiptId() {
        return receiptId;
    }

    @Override
    public boolean sameEventAs(QRCodeCreatedEvent other) {
        return other != null && other.receiptId.equals(this.receiptId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final QRCodeCreatedEvent event = (QRCodeCreatedEvent) o;

        return sameEventAs(event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptId);
    }

}
