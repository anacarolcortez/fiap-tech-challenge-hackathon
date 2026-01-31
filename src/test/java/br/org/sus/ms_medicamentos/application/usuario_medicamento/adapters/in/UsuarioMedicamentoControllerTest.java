package br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.in;

import br.org.sus.ms_medicamentos.application.usuario_medicamento.ports.in.CadastrarUsuarioMedicamentoPortIn;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto.UsuarioMedicamentoOutput;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(UsuarioMedicamentoController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioMedicamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CadastrarUsuarioMedicamentoPortIn useCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCadastrarUsuarioMedicamentoComSucesso() throws Exception {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID medicamentoId = UUID.randomUUID();
        UUID usuarioMedicamentoId = UUID.randomUUID();

        var requestJson = """
            {
              "usuarioId": "%s",
              "medicamentoId": "%s",
              "notificacaoAtiva": true
            }
        """.formatted(usuarioId, medicamentoId);

        var output = new UsuarioMedicamentoOutput(
                usuarioMedicamentoId,
                usuarioId,
                medicamentoId,
                true,
                LocalDate.now()
        );

        Mockito.when(useCase.cadastrar(Mockito.any()))
                .thenReturn(output);

        // Act & Assert
        mockMvc.perform(post("/usuario_medicamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id")
                        .value(usuarioMedicamentoId.toString()))
                .andExpect(jsonPath("$.notificacaoAtiva").value(true))
                .andExpect(jsonPath("$.dataCadastro").exists());
    }
}