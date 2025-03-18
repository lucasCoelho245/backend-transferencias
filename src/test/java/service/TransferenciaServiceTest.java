package service;

import br.com.tokyomarine.transferencias.model.Transferencia;
import br.com.tokyomarine.transferencias.repository.TransferenciaRepository;
import br.com.tokyomarine.transferencias.exception.TransferenciaInvalidaException;
import br.com.tokyomarine.transferencias.service.TaxaCalculator;
import br.com.tokyomarine.transferencias.service.TransferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TransferenciaServiceTest {

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @Mock
    private TaxaCalculator taxaCalculator;

    @InjectMocks
    private TransferenciaService transferenciaService;

    private Transferencia transferencia;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transferencia = new Transferencia();
        transferencia.setContaOrigem("1234567890");
        transferencia.setContaDestino("0987654321");
        transferencia.setValor(BigDecimal.valueOf(1000));
        transferencia.setDataTransferencia(LocalDate.now().plusDays(10));
        transferencia.setDataAgendamento(LocalDate.now());
    }

    @Test
    public void testAgendarTransferenciaComSucesso() {
        // Simular o cálculo da taxa
        when(taxaCalculator.calcularTaxa(anyLong(), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(12.00));

        // Simular a persistência da transferência
        when(transferenciaRepository.save(any(Transferencia.class))).thenReturn(transferencia);

        // Executar o método
        Transferencia result = transferenciaService.agendarTransferencia(transferencia);

        // Verificar os resultados
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(12.00), result.getTaxa());
        verify(transferenciaRepository).save(transferencia);
    }

    @Test
    public void testAgendarTransferenciaComErroTaxaInvalida() {
        // Simular que não há taxa aplicável
        when(taxaCalculator.calcularTaxa(anyLong(), any(BigDecimal.class))).thenReturn(null);

        // Executar o método e verificar se a exceção é lançada
        assertThrows(TransferenciaInvalidaException.class, () -> transferenciaService.agendarTransferencia(transferencia));
    }
}
