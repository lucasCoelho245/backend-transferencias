package br.com.tokyomarine.transferencias.controller;

import br.com.tokyomarine.transferencias.controller.dtos.TransferenciaDTO;
import br.com.tokyomarine.transferencias.model.Transferencia;
import br.com.tokyomarine.transferencias.service.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {
    private final TransferenciaService service;

    @PostMapping
    public ResponseEntity<Transferencia> agendar(@Valid @RequestBody TransferenciaDTO dto) {
        Transferencia transferencia = new Transferencia();
        transferencia.setContaOrigem(dto.getContaOrigem());
        transferencia.setContaDestino(dto.getContaDestino());
        transferencia.setValor(dto.getValor());
        transferencia.setDataTransferencia(dto.getDataTransferencia());
        transferencia.setDataAgendamento(LocalDate.now());

        return ResponseEntity.ok(service.agendarTransferencia(transferencia));
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listarTransferencias());
    }
}