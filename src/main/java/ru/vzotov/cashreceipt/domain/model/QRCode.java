package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import ru.vzotov.ddd.shared.AggregateRoot;
import ru.vzotov.ddd.shared.Entity;
import ru.vzotov.person.domain.model.Owned;
import ru.vzotov.person.domain.model.PersonId;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AggregateRoot
public class QRCode implements Entity<QRCode>, Owned {

    private ReceiptId receiptId;

    private QRCodeData data;

    private ReceiptState state;

    private Long loadingTryCount;

    private OffsetDateTime loadedAt;

    private Set<ReceiptSource> sources;

    private PersonId owner;

    public QRCode(ReceiptId receiptId, QRCodeData data, PersonId owner) {
        this(receiptId, data, ReceiptState.NEW, owner);
    }

    public QRCode(ReceiptId receiptId, QRCodeData data, ReceiptState state, PersonId owner) {
        this(receiptId, data, state, 0L, null, owner);
    }

    public QRCode(ReceiptId receiptId, QRCodeData data, ReceiptState state, Long loadingTryCount, OffsetDateTime loadedAt, PersonId owner) {
        Validate.notNull(receiptId);
        Validate.notNull(data);
        Validate.notNull(state);
        Validate.notNull(owner);
        this.receiptId = receiptId;
        this.data = data;
        this.state = state;
        this.loadingTryCount = loadingTryCount;
        this.loadedAt = loadedAt;
        this.owner = owner;
        this.sources = new HashSet<>(0);
    }

    public PersonId owner() {
        return owner;
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

    public Set<ReceiptSource> sources() {
        return Collections.unmodifiableSet(sources);
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

    /**
     *
     * @param source source to be added to this QR code
     * @return {@code true} if this QR code did not already contain the specified source
     */
    public boolean addSource(ReceiptSource source) {
        Validate.notNull(source);
        return this.sources.add(source);
    }

    /**
     *
     * @param source source to be removed from this QR code, if present
     * @return {@code true} if this QR code contained the specified source
     */
    public boolean removeSource(ReceiptSource source) {
        Validate.notNull(source);
        return this.sources.remove(source);
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
