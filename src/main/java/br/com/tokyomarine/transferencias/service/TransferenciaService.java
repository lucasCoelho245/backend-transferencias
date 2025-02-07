package br.com.tokyomarine.transferencias.service;

import br.com.tokyomarine.transferencias.model.Transferencia;
import br.com.tokyomarine.transferencias.repository.TransferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class TransferenciaService {
    private final TransferenciaRepository repository;

    private static final Map<Long, BigDecimal> TAXAS = Map.of(
            0L, BigDecimal.valueOf(0.025),
            11L, BigDecimal.valueOf(0.082),
            21L, BigDecimal.valueOf(0.069),
            31L, BigDecimal.valueOf(0.047),
            41L, BigDecimal.valueOf(0.017)
    );

    public Transferencia agendarTransferencia(Transferencia transferencia) {
        long dias = ChronoUnit.DAYS.between(transferencia.getDataAgendamento(), transferencia.getDataTransferencia());
        BigDecimal taxa = calcularTaxa(dias, transferencia.getValor());

        if (taxa == null) {
            throw new IllegalArgumentException("Transferência inválida: nenhuma taxa aplicável.");
        }

        transferencia.setTaxa(taxa);
        return repository.save(transferencia);
    }

    private BigDecimal calcularTaxa(long dias, BigDecimal valor) {
        if (dias == 0) {
            return valor.multiply(TAXAS.get(0L)).add(BigDecimal.valueOf(3.00));
        }
        if (dias >= 1 && dias <= 10) {
            return BigDecimal.valueOf(12.00);
        }

        for (Map.Entry<Long, BigDecimal> entry : TAXAS.entrySet()) {
            long inicioIntervalo = entry.getKey();
            long fimIntervalo = inicioIntervalo + 9;

            if (dias >= inicioIntervalo && dias <= fimIntervalo) {
                return valor.multiply(entry.getValue());
            }
        }

        return null;
    }

    public List<Transferencia> listarTransferencias() {
        return repository.findAll();
    }
}