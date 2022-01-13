package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import ru.vzotov.ddd.shared.AggregateRoot;
import ru.vzotov.ddd.shared.Entity;

import java.time.OffsetDateTime;
import java.util.Objects;

@AggregateRoot
public class QRCode implements Entity<QRCode> {

    private ReceiptId receiptId;

    private QRCodeData data;

    private ReceiptState state;

    private Long loadingTryCount;

    private OffsetDateTime loadedAt;

    public QRCode(QRCodeData data) {
        this(data, ReceiptState.NEW);
    }

    public QRCode(QRCodeData data, ReceiptState state) {
        this(data, state, 0L, null);
    }

    public QRCode(QRCodeData data, ReceiptState state, Long loadingTryCount, OffsetDateTime loadedAt) {
        Validate.notNull(data);
        Validate.notNull(state);
        this.receiptId = new ReceiptId(
                data.dateTime().value(),
                data.totalSum(),
                data.fiscalDriveNumber(),
                data.fiscalDocumentNumber(),
                data.fiscalSign(),
                data.operationType()
        );
        this.data = data;
        this.state = state;
        this.loadingTryCount = loadingTryCount;
        this.loadedAt = loadedAt;
    }

    public ReceiptId receiptId() {
        return receiptId;
    }

    public QRCodeData code() {
        return data;
    }

    public ReceiptState state() {
        return state;
    }

    public Long loadingTryCount() {
        return loadingTryCount;
    }

    public OffsetDateTime loadedAt() {
        return loadedAt;
    }

    public void tryLoading() {
        if (loadingTryCount == null) {
            loadingTryCount = 0L;
        }
        loadingTryCount = loadingTryCount + 1L;
        loadedAt = OffsetDateTime.now();
    }

    public void markLoaded() {
        this.state = ReceiptState.LOADED;
    }

    public boolean isLoaded() {
        return ReceiptState.LOADED.equals(this.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptId);
    }

    @Override
    public boolean sameIdentityAs(QRCode other) {
        return other != null && new EqualsBuilder()
                .append(receiptId, other.receiptId)
                .isEquals();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QRCode qrCode = (QRCode) o;
        return Objects.equals(receiptId, qrCode.receiptId);
    }

    @Override
    public String toString() {
        return "QRCode{" +
                "id(surrogate)=" + id +
                ", id=" + receiptId +
                ", code=" + data +
                ", state=" + state +
                ", loadingTryCount=" + loadingTryCount +
                ", loadedAt" + loadedAt +
                '}';
    }

    protected QRCode() {
        // for Hibernate
    }

    // Surrogate key
    private Long id;
}
