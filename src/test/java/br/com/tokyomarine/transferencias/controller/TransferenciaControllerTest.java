package br.com.tokyomarine.transferencias.controller;

import br.com.tokyomarine.transferencias.model.Transferencia;
import br.com.tokyomarine.transferencias.service.TransferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TransferenciaControllerTest {

    @Mock
    private TransferenciaService transferenciaService;

    @InjectMocks
    private TransferenciaController transferenciaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transferenciaController).build();
    }

    @Test
    public void testAgendarTransferencia() throws Exception {
        Transferencia transferencia = new Transferencia();
        transferencia.setContaOrigem("1234567890");
        transferencia.setContaDestino("0987654321");
        transferencia.setValor(BigDecimal.valueOf(1000));
        transferencia.setDataTransferencia(LocalDate.of(2025, 5, 1));

        when(transferenciaService.agendarTransferencia(transferencia)).thenReturn(transferencia);

        mockMvc.perform(post("/transferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contaOrigem\":\"1234567890\",\"contaDestino\":\"0987654321\",\"valor\":1000,\"dataTransferencia\":\"2025-05-01\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contaOrigem").value("1234567890"))
                .andExpect(jsonPath("$.contaDestino").value("0987654321"))
                .andExpect(jsonPath("$.valor").value(1000));
    }

    @Test
    public void testListarTransferencias() throws Exception {
        mockMvc.perform(get("/transferencias"))
                .andExpect(status().isOk());
    }
}
