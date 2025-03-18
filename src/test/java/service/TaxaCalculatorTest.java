package service;

import br.com.tokyomarine.transferencias.service.TaxaCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class TaxaCalculatorTest {

    private TaxaCalculator taxaCalculator;

    @BeforeEach
    public void setUp() {
        taxaCalculator = new TaxaCalculator();
    }

    @Test
    public void testCalcularTaxaDiasZero() {
        BigDecimal valor = new BigDecimal("1000.00");
        BigDecimal taxaEsperada = new BigDecimal("28.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal resultado = taxaCalculator.calcularTaxa(0, valor).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(taxaEsperada, resultado);
    }

    @Test
    public void testCalcularTaxaDiasEntre1e10() {
        BigDecimal valor = new BigDecimal("1000.00");
        BigDecimal taxaEsperada = new BigDecimal("12.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal resultado = taxaCalculator.calcularTaxa(5, valor).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(taxaEsperada, resultado);
    }

    @Test
    public void testCalcularTaxaDiasEntre11e20() {
        BigDecimal valor = new BigDecimal("1000.00");
        BigDecimal taxaEsperada = new BigDecimal("82.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal resultado = taxaCalculator.calcularTaxa(15, valor).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(taxaEsperada, resultado);
    }

    @Test
    public void testCalcularTaxaDiasEntre21e30() {
        BigDecimal valor = new BigDecimal("1000.00");
        BigDecimal taxaEsperada = new BigDecimal("69.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal resultado = taxaCalculator.calcularTaxa(25, valor).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(taxaEsperada, resultado);
    }

    @Test
    public void testCalcularTaxaDiasEntre31e40() {
        BigDecimal valor = new BigDecimal("1000.00");
        BigDecimal taxaEsperada = new BigDecimal("47.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal resultado = taxaCalculator.calcularTaxa(35, valor).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(taxaEsperada, resultado);
    }

    @Test
    public void testCalcularTaxaDiasEntre41e50() {
        BigDecimal valor = new BigDecimal("1000.00");
        BigDecimal taxaEsperada = new BigDecimal("17.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal resultado = taxaCalculator.calcularTaxa(45, valor).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(taxaEsperada, resultado);
    }

    @Test
    public void testCalcularTaxaDiasMaiorQue50() {
        BigDecimal valor = new BigDecimal("1000.00");
        BigDecimal resultado = taxaCalculator.calcularTaxa(60, valor);
        assertNull(resultado);
    }
}
