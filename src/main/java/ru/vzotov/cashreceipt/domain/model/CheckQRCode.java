package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import ru.vzotov.ddd.shared.AggregateRoot;
import ru.vzotov.ddd.shared.Entity;

import java.time.OffsetDateTime;
import java.util.Objects;

@AggregateRoot
public class CheckQRCode implements Entity<CheckQRCode> {

    private CheckId checkId;

    private QRCodeData code;

    private CheckState state;

    private Long loadingTryCount;

    private OffsetDateTime loadedAt;

    public CheckQRCode(QRCodeData code) {
        this(code, CheckState.NEW);
    }

    public CheckQRCode(QRCodeData code, CheckState state) {
        this(code, state, 0L, null);
    }

    public CheckQRCode(QRCodeData code, CheckState state, Long loadingTryCount, OffsetDateTime loadedAt) {
        Validate.notNull(code);
        Validate.notNull(state);
        this.checkId = new CheckId(
                code.dateTime().value(),
                code.totalSum(),
                code.fiscalDriveNumber(),
                code.fiscalDocumentNumber(),
                code.fiscalSign(),
                code.operationType()
        );
        this.code = code;
        this.state = state;
        this.loadingTryCount = loadingTryCount;
        this.loadedAt = loadedAt;
    }

    public CheckId checkId() {
        return checkId;
    }

    public QRCodeData code() {
        return code;
    }

    public CheckState state() {
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
        this.state = CheckState.LOADED;
    }

    public boolean isLoaded() {
        return CheckState.LOADED.equals(this.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkId);
    }

    @Override
    public boolean sameIdentityAs(CheckQRCode other) {
        return other != null && new EqualsBuilder()
                .append(checkId, other.checkId)
                .isEquals();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckQRCode qrCode = (CheckQRCode) o;
        return Objects.equals(checkId, qrCode.checkId);
    }

    @Override
    public String toString() {
        return "CheckQRCode{" +
                "id(surrogate)=" + id +
                ", id=" + checkId +
                ", code=" + code +
                ", state=" + state +
                ", loadingTryCount=" + loadingTryCount +
                ", loadedAt" + loadedAt +
                '}';
    }

    protected CheckQRCode() {
        // for Hibernate
    }

    // Surrogate key
    private Long id;
}
