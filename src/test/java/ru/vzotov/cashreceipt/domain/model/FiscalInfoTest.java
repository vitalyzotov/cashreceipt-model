package ru.vzotov.cashreceipt.domain.model;

import org.junit.jupiter.api.Test;
import ru.vzotov.fiscal.FiscalSign;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FiscalInfoTest {
    @Test
    public void testConstructor() {
        assertThatThrownBy(() -> new FiscalInfo(null, null, null, null))
                .as("Should not accept null arguments").isInstanceOf(Exception.class);

        FiscalInfo info = new FiscalInfo("0001107425024311", new FiscalSign(2334756689L), "21068", "8710000100312991");
        assertThat(info).isNotEqualTo(
                new FiscalInfo("1", "0001107425024311", new FiscalSign(2334756689L), "21068", "8710000100312991")
        );
    }

}
