package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import ru.vzotov.ddd.shared.AggregateRoot;
import ru.vzotov.ddd.shared.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Кассовый чек
 * <p>
 * Федеральный закон "О применении контрольно-кассовой техники при осуществлении наличных денежных расчетов и (или)
 * расчетов с использованием электронных средств платежа" от 22.05.2003 N 54-ФЗ
 */
@AggregateRoot
public class Receipt implements Entity<Receipt> {

    private ReceiptId receiptId;
    private LocalDateTime dateTime; //1528916460

    /**
     * Вероятно это признак расчета (приход, возврат)
     * Пример значения: 1
     *
     * @see ReceiptOperationType
     */
    private ReceiptOperationType operationType;

    /**
     * Serial number of cash receipt
     * <p>
     * Example value: 170
     * </p>
     */
    private Long requestNumber;

    private FiscalInfo fiscalInfo;
    private Marketing marketing;
    private ShiftInfo shiftInfo;
    private RetailPlace retailPlace;
    private Products products;
    private PaymentInfo paymentInfo;

    public Receipt(LocalDateTime dateTime, ReceiptOperationType operationType, Long requestNumber, FiscalInfo fiscalInfo, Marketing marketing,
                   ShiftInfo shiftInfo, RetailPlace retailPlace, Products products, PaymentInfo paymentInfo) {
        Validate.notNull(dateTime);
        Validate.notNull(operationType);
        Validate.notNull(requestNumber);
        Validate.notNull(fiscalInfo);
        //marketing can be null
        Validate.notNull(shiftInfo);
        Validate.notNull(retailPlace);
        Validate.notNull(products);
        Validate.notNull(paymentInfo);

        this.receiptId = new ReceiptId(dateTime, products.totalSum(),
                fiscalInfo.fiscalDriveNumber(), fiscalInfo.fiscalDocumentNumber(), fiscalInfo.fiscalSign(),
                operationType);
        this.dateTime = dateTime;
        this.operationType = operationType;
        this.requestNumber = requestNumber;
        this.fiscalInfo = fiscalInfo;
        this.marketing = marketing;
        this.shiftInfo = shiftInfo;
        this.retailPlace = retailPlace;
        this.products = products;
        this.paymentInfo = paymentInfo;
    }

    public ReceiptId receiptId() {
        return receiptId;
    }

    public LocalDateTime dateTime() {
        return dateTime;
    }

    public ReceiptOperationType operationType() {
        return operationType;
    }

    public Long requestNumber() {
        return requestNumber;
    }

    public FiscalInfo fiscalInfo() {
        return fiscalInfo;
    }

    public Marketing marketing() {
        return marketing;
    }

    public ShiftInfo shiftInfo() {
        return shiftInfo;
    }

    public RetailPlace retailPlace() {
        return retailPlace;
    }

    public Products products() {
        return products;
    }

    public PaymentInfo paymentInfo() {
        return paymentInfo;
    }

    public void assignCategoryToItem(Integer itemIndex, PurchaseCategory category) {
        Validate.notNull(itemIndex);
        Validate.isTrue(itemIndex >= 0);
        Validate.notNull(category);
        Validate.isTrue(products().items().stream().anyMatch(i -> i.index().equals(itemIndex)));

        List<Item> items = products().items().stream().map(i -> i.index().equals(itemIndex) ? new Item(
                i.name(), i.price(), i.quantity(), i.sum(), i.index(), category
        ) : i).collect(Collectors.toList());

        this.products = new Products(items, this.products.stornoItems(), this.products.totalSum());
    }

    @Override
    public String toString() {
        return "Receipt{ id(surrogate)=" + id + ", id(real)=" + receiptId + '}';
    }

    @Override
    public boolean sameIdentityAs(Receipt other) {
        return other != null && new EqualsBuilder()
                .append(receiptId, other.receiptId)
                .isEquals();
    }

    protected Receipt() {
        // for Hibernate
    }

    // Surrogate key
    private Long id;
}
