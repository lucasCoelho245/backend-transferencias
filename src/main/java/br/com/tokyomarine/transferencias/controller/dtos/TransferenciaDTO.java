package br.com.tokyomarine.transferencias.controller.dtos;

import br.com.tokyomarine.transferencias.model.Transferencia;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransferenciaDTO {

    @NotBlank(message = "A conta de origem é obrigatória")
    @Pattern(regexp = "\\d{10}", message = "A conta de origem deve ter 10 dígitos")
    private String contaOrigem;

    @NotBlank(message = "A conta de destino é obrigatória")
    @Pattern(regexp = "\\d{10}", message = "A conta de destino deve ter 10 dígitos")
    private String contaDestino;

    @NotNull(message = "O valor da transferência é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "A data de transferência é obrigatória")
    private LocalDate dataTransferencia;

    // Método para converter o DTO em uma entidade Transferencia
    public Transferencia toEntity() {
        Transferencia transferencia = new Transferencia();
        transferencia.setContaOrigem(this.contaOrigem);
        transferencia.setContaDestino(this.contaDestino);
        transferencia.setValor(this.valor);
        transferencia.setDataTransferencia(this.dataTransferencia);
        return transferencia;
    }
}