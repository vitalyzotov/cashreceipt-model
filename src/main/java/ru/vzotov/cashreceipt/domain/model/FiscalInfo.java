package ru.vzotov.cashreceipt.domain.model;

import org.apache.commons.lang3.Validate;
import ru.vzotov.ddd.shared.ValueObject;
import ru.vzotov.fiscal.FiscalSign;

import java.util.Objects;

/**
 * <p>
 * 61. QR-код на кассовых чеках (БСО) в печатной форме не должен содержать данные адреса в формате URL и должен
 * обеспечивать с использованием программного обеспечения подсистемы обеспечения проверки кассового чека и подачи
 * жалобы в налоговые органы на корректность применения контрольно-кассовой техники, входящей в состав
 * автоматизированной системы контроля применения контрольно-кассовой техники ФНС России, проверку следующих
 * реквизитов кассового чека (БСО):
 * дата и время осуществления расчета;
 * сумма расчета;
 * заводской номер фискального накопителя;
 * порядковый номер фискального документа;
 * фискальный признак документа;
 * признак расчета.
 * </p>
 * <p>
 * 62. Данные QR-кода должны представлять собой текстовую строку из латинских букв, цифр и символовразделителей
 * «=» и «&». Текст должен быть представлен в кодировке CP866. Структура данных, помещаемых в строку QR-кода,
 * состоит из шести полей:
 * t=<date/time – дата и время осуществления расчета в формате ГГГГММДДТЧЧММ>
 * s=<сумма расчета в рублях и копейках, разделенных точкой>
 * fn=<заводской номер фискального накопителя>
 * i=<порядковый номер фискального документа, нулями не дополняется>
 * fp=<фискальный признак документа, нулями не дополняется>
 * n=<признак расчета>.
 * Пример строки QR-кода: t=20150720T1638&s=9999999.00&fn=000110000105&i=12345678&fp=123456&n=2
 * </p>
 */
public class FiscalInfo implements ValueObject<FiscalInfo> {

    /**
     * Заводской номер ККТ (?)
     */
    private String kktNumber;

    /**
     * Регистрационный номер ККТ (РНМ)
     * Обозначение на чеке: РН ККТ
     * Пример: "0001109150007803"
     * <p>
     * Формат регистрационного номера ККТ должен:
     * состоять из 16 цифр (10 + 6), XXXXXXXXXXYYYYYY, где:
     * XXXXXXXXXX – порядковый номер зарегистрированной ККТ, состоящий из 10 цифр (от 0 до 9);
     * YYYYYY – контрольное число для проверки регистрационного номера ККТ число, состоящее из 6 цифр (от 0 до 9).
     * Если порядковый номер регистрируемой ККТ содержит менее чем из 10 цифр, то он дополняется лидирующими нулями до
     * длины строки в 10 цифр;
     * обеспечивать вычисление контрольного числа регистрационного номера ККТ по алгоритму расчета контрольной
     * суммы CRC16-CCITT (дополняется лидирующими нулями до длины строки в 6 цифр), используя следующие параметры
     * алгоритма CRC16-CCITT:
     * Width = 16 bits;
     * Truncated polynomial = 0x1021;
     * Initial value = 0xFFFF;
     * No XOR is performed on the output CRC.
     * Примечание: на вход алгоритма CRC16-CCITT вводятся:
     * а) регистрационный номер ККТ (дополняется лидирующими нулями до длины в 10 цифр);
     * б) ИНН пользователя ККТ (дополняется лидирующими нулями до длины в 12 цифр);
     * в) заводской номер ККТ (дополняется лидирующими нулями до длины в 20 цифр).
     */
    private String kktRegId;

    /**
     * Фискальный признак данных
     * Обозначение на чеке: ФП, ФПД
     * Пример: 2334756689
     */
    private FiscalSign fiscalSign;

    /**
     * Порядковый номер фискального документа, номер фискального чека
     * Обозначение на чеке: ФД №
     * Пример: 21068
     * Тип: Целое UInt32 (4)
     */
    private String fiscalDocumentNumber;

    /**
     * Заводской номер экземпляра фискального накопителя
     * Обозначение на чеке: ФН №
     * Пример: "8710000100312991";
     * Тип: Строка (16)
     */
    private String fiscalDriveNumber;

    public FiscalInfo(String kktRegId,
                      FiscalSign fiscalSign,
                      String fiscalDocumentNumber,
                      String fiscalDriveNumber) {
        this(null, kktRegId, fiscalSign, fiscalDocumentNumber, fiscalDriveNumber);
    }

    public FiscalInfo(String kktNumber,
                      String kktRegId,
                      FiscalSign fiscalSign,
                      String fiscalDocumentNumber,
                      String fiscalDriveNumber) {
        Validate.notNull(kktRegId);
        Validate.notNull(fiscalSign);
        Validate.notNull(fiscalDocumentNumber);
        Validate.notNull(fiscalDriveNumber);

        this.kktNumber = kktNumber;
        this.kktRegId = kktRegId;
        this.fiscalSign = fiscalSign;
        this.fiscalDocumentNumber = fiscalDocumentNumber;
        this.fiscalDriveNumber = fiscalDriveNumber;
    }

    public String kktNumber() {
        return kktNumber;
    }

    public String kktRegId() {
        return kktRegId;
    }

    public FiscalSign fiscalSign() {
        return fiscalSign;
    }

    public String fiscalDocumentNumber() {
        return fiscalDocumentNumber;
    }

    public String fiscalDriveNumber() {
        return fiscalDriveNumber;
    }

    @Override
    public String toString() {
        return "{" +
                "kktNumber='" + kktNumber + '\'' +
                ", kktRegId='" + kktRegId + '\'' +
                ", fiscalSign=" + fiscalSign +
                ", fiscalDocumentNumber='" + fiscalDocumentNumber + '\'' +
                ", fiscalDriveNumber='" + fiscalDriveNumber + '\'' +
                '}';
    }

    @Override
    public boolean sameValueAs(FiscalInfo that) {
        return that != null
                && Objects.equals(kktNumber, that.kktNumber)
                && Objects.equals(kktRegId, that.kktRegId)
                && Objects.equals(fiscalSign, that.fiscalSign)
                && Objects.equals(fiscalDocumentNumber, that.fiscalDocumentNumber)
                && Objects.equals(fiscalDriveNumber, that.fiscalDriveNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FiscalInfo that = (FiscalInfo) o;
        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kktNumber, kktRegId, fiscalSign, fiscalDocumentNumber, fiscalDriveNumber);
    }

    protected FiscalInfo() {
        // for hibernate
    }
}
