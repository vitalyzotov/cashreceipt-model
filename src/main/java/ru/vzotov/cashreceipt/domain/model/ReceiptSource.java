package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang3.Validate;
import ru.vzotov.ddd.shared.Entity;

import java.time.OffsetDateTime;
import java.util.Objects;

public class ReceiptSource implements Entity<ReceiptSource> {

    private ReceiptSourceId receiptSourceId;

    private String value;

    private OffsetDateTime created;

    public ReceiptSource(ReceiptSourceId receiptSourceId, String value) {
        Validate.notNull(receiptSourceId);
        Validate.notNull(value);
        this.receiptSourceId = receiptSourceId;
        this.value = value;
    }

    public static ReceiptSource load(ReceiptSourceId receiptSourceId, String value, OffsetDateTime created) {
        final ReceiptSource result = new ReceiptSource(receiptSourceId, value);
        result.created = created;
        return result;
    }

    public ReceiptSourceId receiptSourceId() {
        return receiptSourceId;
    }

    public String value() {
        return value;
    }

    public OffsetDateTime created() {
        return created;
    }

    @Override
    public boolean sameIdentityAs(ReceiptSource other) {
        return other != null && Objects.equals(receiptSourceId, other.receiptSourceId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptSource that = (ReceiptSource) o;
        return sameIdentityAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptSourceId);
    }

    protected ReceiptSource() {
        // for ORM
    }

    protected void onCreate() {
        created = OffsetDateTime.now();
    }

    /**
     * Auto-generated surrogate key
     */
    private Long id;
}
