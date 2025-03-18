package br.com.tokyomarine.transferencias.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TaxaCalculator {

    private static final BigDecimal TAXA_FIXA_0_DIAS = new BigDecimal("3.00");
    private static final BigDecimal TAXA_2_5_PERCENTO = new BigDecimal("0.025");

    private static final Map<Integer, BigDecimal> TAXAS_POR_INTERVALO = Map.of(
            0, TAXA_2_5_PERCENTO,
            10, new BigDecimal("12.00"),
            20, new BigDecimal("0.082"),
            30, new BigDecimal("0.069"),
            40, new BigDecimal("0.047"),
            50, new BigDecimal("0.017")
    );

    public BigDecimal calcularTaxa(long dias, BigDecimal valor) {
        if (dias == 0) {
            return valor.multiply(TAXA_2_5_PERCENTO).add(TAXA_FIXA_0_DIAS);
        }

        if (dias >= 1 && dias <= 10) {
            return new BigDecimal("12.00");
        }

        Optional<Map.Entry<Integer, BigDecimal>> taxaOpt = TAXAS_POR_INTERVALO.entrySet().stream()
                .filter(entry -> dias <= entry.getKey())
                .min(Map.Entry.comparingByKey());

        return taxaOpt.map(entry -> {
            if (entry.getKey() == 10) {
                return entry.getValue();
            } else {
                return valor.multiply(entry.getValue());
            }
        }).orElse(null);
    }
}
