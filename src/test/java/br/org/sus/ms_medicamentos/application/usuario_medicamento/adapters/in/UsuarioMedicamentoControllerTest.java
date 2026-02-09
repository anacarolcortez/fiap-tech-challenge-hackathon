package br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.in;

import br.org.sus.ms_medicamentos.application.usuario_medicamento.ports.in.CadastrarUsuarioMedicamentoPortIn;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto.UsuarioMedicamentoInput;
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

        // ========== TESTES DE CASOS DE FALHA ==========

        @Test
        void deveRetornarBadRequest_QuandoUsuarioIdForNulo() throws Exception {
            // Arrange
            UUID medicamentoId = UUID.randomUUID();

            var requestJson = """
            {
              "usuarioId": null,
              "medicamentoId": "%s",
              "notificacaoAtiva": true
            }
        """.formatted(medicamentoId);

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void deveRetornarBadRequest_QuandoMedicamentoIdForNulo() throws Exception {
            // Arrange
            UUID usuarioId = UUID.randomUUID();

            var requestJson = """
            {
              "usuarioId": "%s",
              "medicamentoId": null,
              "notificacaoAtiva": true
            }
        """.formatted(usuarioId);

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void deveRetornarBadRequest_QuandoUsuarioIdForInvalido() throws Exception {
            // Arrange
            UUID medicamentoId = UUID.randomUUID();

            var requestJson = """
            {
              "usuarioId": "uuid-invalido",
              "medicamentoId": "%s",
              "notificacaoAtiva": true
            }
        """.formatted(medicamentoId);

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void deveRetornarBadRequest_QuandoMedicamentoIdForInvalido() throws Exception {
            // Arrange
            UUID usuarioId = UUID.randomUUID();

            var requestJson = """
            {
              "usuarioId": "%s",
              "medicamentoId": "uuid-invalido",
              "notificacaoAtiva": true
            }
        """.formatted(usuarioId);

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void deveRetornarBadRequest_QuandoNotificacaoAtivaForNula() throws Exception {
            // Arrange
            UUID usuarioId = UUID.randomUUID();
            UUID medicamentoId = UUID.randomUUID();

            var requestJson = """
            {
              "usuarioId": "%s",
              "medicamentoId": "%s",
              "notificacaoAtiva": null
            }
        """.formatted(usuarioId, medicamentoId);

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void deveRetornarBadRequest_QuandoCorpoRequisicaoForVazio() throws Exception {
            // Arrange
            var requestJson = "{}";

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void deveRetornarBadRequest_QuandoJsonForMalFormado() throws Exception {
            // Arrange
            var requestJson = """
            {
              "usuarioId": "%s",
              "medicamentoId": 
            }
        """;

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void deveRetornarUnsupportedMediaType_QuandoContentTypeNaoForJson() throws Exception {
            // Arrange
            UUID usuarioId = UUID.randomUUID();
            UUID medicamentoId = UUID.randomUUID();

            var requestBody = "usuarioId=" + usuarioId + "&medicamentoId=" + medicamentoId;

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(requestBody))
                    .andExpect(status().isUnsupportedMediaType());
        }

        @Test
        void deveRetornarErro_QuandoUseCaseLancarException() throws Exception {
            // Arrange
            UUID usuarioId = UUID.randomUUID();
            UUID medicamentoId = UUID.randomUUID();

            var requestJson = """
            {
              "usuarioId": "%s",
              "medicamentoId": "%s",
              "notificacaoAtiva": true
            }
        """.formatted(usuarioId, medicamentoId);

            Mockito.when(useCase.cadastrar(new UsuarioMedicamentoInput(usuarioId, medicamentoId, true)))
                    .thenThrow(new RuntimeException("Erro ao cadastrar"));

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void deveRetornarErro_QuandoUsuarioNaoExistir() throws Exception {
            // Arrange
            UUID usuarioId = UUID.randomUUID();
            UUID medicamentoId = UUID.randomUUID();

            var requestJson = """
            {
              "usuarioId": "%s",
              "medicamentoId": "%s",
              "notificacaoAtiva": true
            }
        """.formatted(usuarioId, medicamentoId);

            Mockito.when(useCase.cadastrar(new UsuarioMedicamentoInput(usuarioId, medicamentoId, true)))
                    .thenThrow(new IllegalArgumentException("Usuário não encontrado"));

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void deveRetornarErro_QuandoMedicamentoNaoExistir() throws Exception {
            // Arrange
            UUID usuarioId = UUID.randomUUID();
            UUID medicamentoId = UUID.randomUUID();

            var requestJson = """
            {
              "usuarioId": "%s",
              "medicamentoId": "%s",
              "notificacaoAtiva": true
            }
        """.formatted(usuarioId, medicamentoId);

            Mockito.when(useCase.cadastrar(new UsuarioMedicamentoInput(usuarioId, medicamentoId, true)))
                    .thenThrow(new IllegalArgumentException("Medicamento não encontrado"));

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void deveRetornarErro_QuandoAssociacaoJaExistir() throws Exception {
            // Arrange
            UUID usuarioId = UUID.randomUUID();
            UUID medicamentoId = UUID.randomUUID();

            var requestJson = """
            {
              "usuarioId": "%s",
              "medicamentoId": "%s",
              "notificacaoAtiva": true
            }
        """.formatted(usuarioId, medicamentoId);

            Mockito.when(useCase.cadastrar(new UsuarioMedicamentoInput(usuarioId, medicamentoId, true)))
                    .thenThrow(new IllegalStateException("Associação já existe"));

            // Act & Assert
            mockMvc.perform(post("/usuario_medicamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isConflict());
        }
    }

