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
import java.util.Arrays;
import java.util.List;

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
        when(taxaCalculator.calcularTaxa(anyLong(), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(12.00));

        transferencia.setId(1L);
        when(transferenciaRepository.save(any(Transferencia.class))).thenReturn(transferencia);

        Transferencia result = transferenciaService.agendarTransferencia(transferencia);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(12.00), result.getTaxa());
        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        verify(transferenciaRepository).save(transferencia);
    }

    @Test
    public void testAgendarTransferenciaComErroTaxaInvalida() {
        when(taxaCalculator.calcularTaxa(anyLong(), any(BigDecimal.class))).thenReturn(null);

        assertThrows(TransferenciaInvalidaException.class, () -> transferenciaService.agendarTransferencia(transferencia));
    }

    @Test
    public void testAgendarTransferenciaCom0Dias() {
        LocalDate dataAgendamento = LocalDate.now();
        transferencia.setDataAgendamento(dataAgendamento);

        when(taxaCalculator.calcularTaxa(anyLong(), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(10.00));

        when(transferenciaRepository.save(any(Transferencia.class))).thenAnswer(invocation -> {
            Transferencia t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        Transferencia result = transferenciaService.agendarTransferencia(transferencia);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        assertEquals(BigDecimal.valueOf(10.00), result.getTaxa());
    }


    @Test
    public void testAgendarTransferenciaComDiasEntre1e10() {
        when(taxaCalculator.calcularTaxa(anyLong(), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(12.00));

        when(transferenciaRepository.save(any(Transferencia.class))).thenAnswer(invocation -> {
            Transferencia t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });


        Transferencia result = transferenciaService.agendarTransferencia(transferencia);


        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(12.00), result.getTaxa());
        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        verify(transferenciaRepository).save(transferencia);
    }

    @Test
    public void testAgendarTransferenciaComDiasEntre11e20() {
        LocalDate dataAgendamento = LocalDate.now().plusDays(15);
        transferencia.setDataAgendamento(dataAgendamento);

        when(taxaCalculator.calcularTaxa(anyLong(), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(10.00));

        when(transferenciaRepository.save(any(Transferencia.class))).thenAnswer(invocation -> {
            Transferencia t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        Transferencia result = transferenciaService.agendarTransferencia(transferencia);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        assertEquals(BigDecimal.valueOf(10.00), result.getTaxa());
    }




    @Test
    public void testListarTransferencias() {
        Transferencia transferencia1 = new Transferencia();
        Transferencia transferencia2 = new Transferencia();

        when(transferenciaRepository.findAll()).thenReturn(Arrays.asList(transferencia1, transferencia2));

        List<Transferencia> result = transferenciaService.listarTransferencias();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transferenciaRepository, times(1)).findAll();
    }
}
