package br.com.tokyomarine.transferencias.controller;

import br.com.tokyomarine.transferencias.controller.dtos.TransferenciaDTO;
import br.com.tokyomarine.transferencias.model.Transferencia;
import br.com.tokyomarine.transferencias.service.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    private static final Logger log = LoggerFactory.getLogger(TransferenciaController.class);

    private final TransferenciaService service;

    @PostMapping
    public ResponseEntity<Transferencia> agendar(@Valid @RequestBody TransferenciaDTO dto) {
        log.info("Recebendo solicitação para agendar transferência: {}", dto);
        Transferencia transferencia = dto.toEntity();
        transferencia.setDataAgendamento(LocalDate.now());

        Transferencia result = service.agendarTransferencia(transferencia);
        log.info("Transferência agendada com sucesso: ID {}", result.getId());

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        log.info("Listando todas as transferências");
        return ResponseEntity.ok(service.listarTransferencias());
    }
}
