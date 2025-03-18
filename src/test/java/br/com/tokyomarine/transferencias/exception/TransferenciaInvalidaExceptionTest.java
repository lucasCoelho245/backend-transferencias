package br.com.tokyomarine.transferencias.exception;

import br.com.tokyomarine.transferencias.model.Transferencia;
import br.com.tokyomarine.transferencias.service.TransferenciaService;
import br.com.tokyomarine.transferencias.service.TaxaCalculator;
import br.com.tokyomarine.transferencias.repository.TransferenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransferenciaInvalidaExceptionTest {

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
        transferencia.setDataTransferencia(LocalDate.now().plusDays(60));
        transferencia.setDataAgendamento(LocalDate.now());
    }

    @Test
    public void testTransferenciaInvalida() {
        when(taxaCalculator.calcularTaxa(anyLong(), any(BigDecimal.class))).thenReturn(null);

        assertThrows(TransferenciaInvalidaException.class, () -> transferenciaService.agendarTransferencia(transferencia));
    }
}
