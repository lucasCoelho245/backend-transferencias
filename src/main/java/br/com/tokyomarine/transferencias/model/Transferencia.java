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
import javax.persistence.Column;
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
    @Column(nullable = false)
    private String contaOrigem;

    @NotBlank
    @Column(nullable = false)
    private String contaDestino;

    @NotNull
    @Column(nullable = false)
    private BigDecimal valor;

    @NotNull
    @Column(nullable = false)
    private BigDecimal taxa;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataTransferencia;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataAgendamento = LocalDate.now();
}
