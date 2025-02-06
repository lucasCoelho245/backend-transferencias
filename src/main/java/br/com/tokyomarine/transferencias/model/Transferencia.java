package br.com.tokyomarine.transferencias.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String contaOrigem;

    @NotBlank
    private String contaDestino;

    @NotNull
    private BigDecimal valor;

    @NotNull
    private BigDecimal taxa;

    @NotNull
    private LocalDate dataTransferencia;

    @NotNull
    private LocalDate dataAgendamento = LocalDate.now();
}