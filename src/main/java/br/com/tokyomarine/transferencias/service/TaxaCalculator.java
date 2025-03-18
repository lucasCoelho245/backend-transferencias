package br.com.tokyomarine.transferencias.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class TaxaCalculator {

    private static final BigDecimal TAXA_FIXA_0_DIAS = new BigDecimal("3.00");
    private static final BigDecimal TAXA_2_5_PERCENTO = new BigDecimal("0.025");
    private static final BigDecimal TAXA_8_2_PERCENTO = new BigDecimal("0.082");
    private static final BigDecimal TAXA_6_9_PERCENTO = new BigDecimal("0.069");
    private static final BigDecimal TAXA_4_7_PERCENTO = new BigDecimal("0.047");
    private static final BigDecimal TAXA_1_7_PERCENTO = new BigDecimal("0.017");

    public BigDecimal calcularTaxa(long dias, BigDecimal valor) {
        if (dias == 0) {
            return valor.multiply(TAXA_2_5_PERCENTO).add(TAXA_FIXA_0_DIAS);
        }
        if (dias >= 1 && dias <= 10) {
            return new BigDecimal("12.00");
        }
        if (dias >= 11 && dias <= 20) {
            return valor.multiply(TAXA_8_2_PERCENTO);
        }
        if (dias >= 21 && dias <= 30) {
            return valor.multiply(TAXA_6_9_PERCENTO);
        }
        if (dias >= 31 && dias <= 40) {
            return valor.multiply(TAXA_4_7_PERCENTO);
        }
        if (dias >= 41 && dias <= 50) {
            return valor.multiply(TAXA_1_7_PERCENTO);
        }
        return null;
    }
}
