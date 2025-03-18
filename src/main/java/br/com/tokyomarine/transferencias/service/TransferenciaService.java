package br.com.tokyomarine.transferencias.service;

import br.com.tokyomarine.transferencias.model.Transferencia;
import br.com.tokyomarine.transferencias.repository.TransferenciaRepository;
import br.com.tokyomarine.transferencias.exception.TransferenciaInvalidaException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private static final Logger log = LoggerFactory.getLogger(TransferenciaService.class);

    private final TransferenciaRepository repository;
    private final TaxaCalculator taxaCalculator;

    public Transferencia agendarTransferencia(Transferencia transferencia) {
        long dias = ChronoUnit.DAYS.between(transferencia.getDataAgendamento(), transferencia.getDataTransferencia());
        BigDecimal taxa = taxaCalculator.calcularTaxa(dias, transferencia.getValor());

        if (taxa == null) {
            log.warn("Tentativa de agendar uma transferência inválida.");
            throw new TransferenciaInvalidaException("Nenhuma taxa aplicável para esta transferência.");
        }

        transferencia.setTaxa(taxa);
        log.info("Transferência agendada com sucesso: ID {}", transferencia.getId());
        return repository.save(transferencia);
    }

    public List<Transferencia> listarTransferencias() {
        log.info("Listando todas as transferências");
        return repository.findAll();
    }

    @Transactional
    public void deleteTransferencia(Long id) {
        repository.deleteById(id);
    }
}
