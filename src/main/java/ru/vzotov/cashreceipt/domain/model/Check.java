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
public class Check implements Entity<Check> {

    private CheckId checkId;
    private LocalDateTime dateTime; //1528916460

    /**
     * Вероятно это признак расчета (приход, возврат)
     * Пример значения: 1
     *
     * @see CheckOperationType
     */
    private Long operationType; //TODO: use enum

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

    public Check(LocalDateTime dateTime, Long operationType, Long requestNumber, FiscalInfo fiscalInfo, Marketing marketing,
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

        this.checkId = new CheckId(dateTime, products.totalSum(),
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

    public CheckId checkId() {
        return checkId;
    }

    public LocalDateTime dateTime() {
        return dateTime;
    }

    public Long operationType() {
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
        return "Check{ id(surrogate)=" + id + ", id(real)=" + checkId + '}';
    }

    @Override
    public boolean sameIdentityAs(Check other) {
        return other != null && new EqualsBuilder()
                .append(checkId, other.checkId)
                .isEquals();
    }

    protected Check() {
        // for Hibernate
    }

    // Surrogate key
    private Long id;
}
