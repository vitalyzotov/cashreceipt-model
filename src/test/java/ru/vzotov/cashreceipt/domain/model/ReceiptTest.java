package ru.vzotov.cashreceipt.domain.model;

import org.junit.Test;
import ru.vzotov.domain.model.Money;
import ru.vzotov.fiscal.FiscalSign;
import ru.vzotov.fiscal.Inn;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ReceiptTest {
    @Test
    public void testConstructor() {
        assertThat(catchThrowable(() -> {
            new Receipt(null, null, null, null, null, null, null, null, null);
        })).as("Should not accept null arguments")
                .isInstanceOf(Exception.class);
    }

    @Test
    public void assignItemToCategory() {
        Receipt receipt = new Receipt(
                LocalDateTime.of(2018, Month.JANUARY, 23, 0, 0, 0, 0),
                ReceiptOperationType.INCOME,
                1L,
                new FiscalInfo("0001107425024311", new FiscalSign(2334756689L), "21068", "8710000100312991"),
                Marketing.emptyMarketing(),
                new ShiftInfo(318L, "Иванов"),
                new RetailPlace(null, new Inn("2310031475"), new Address("410062, Саратовская обл, Саратов г, Моторная ул, дом № 6"), TaxationType.MASK_GENERAL),
                new Products(
                        Arrays.asList(
                                new Item("Товар №1", Money.kopecks(10), 1.5d, Money.kopecks(15), 0),
                                new Item("Товар №2", Money.kopecks(25), 1.0d, Money.kopecks(25), 1)
                        ),
                        Collections.emptyList(),
                        Money.kopecks(40)
                ),
                new PaymentInfo(Money.kopecks(0), Money.kopecks(40))
        );

        assertThat(receipt.products().items().get(0).category()).isNull();
        receipt.assignCategoryToItem(0, new PurchaseCategory(PurchaseCategoryId.nextId(), "Категория 1"));
        assertThat(receipt.products().items().get(0).category().name()).isEqualTo("Категория 1");
    }
}
