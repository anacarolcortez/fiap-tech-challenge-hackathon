package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.AtualizarEstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.EstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in.AtualizarEstoqueMedicamentoPortIn;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in.ListarRemediosDisponiveisPorNomePortIn;
import br.org.sus.ms_medicamentos.domain.DistribuidorSUS;
import br.org.sus.ms_medicamentos.domain.Medicamento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicamentoDistribuidorController.class)
@AutoConfigureMockMvc(addFilters = false)
class MedicamentoDistribuidorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AtualizarEstoqueMedicamentoPortIn estoqueUseCase;

    @MockitoBean
    private ListarRemediosDisponiveisPorNomePortIn listagemUseCase;

    @Test
    @DisplayName("Deve atualizar estoque com sucesso")
    void deveAtualizarEstoqueComSucesso() throws Exception {
        UUID uuidRequest = UUID.randomUUID();
        int quantidade = 50;
        int novoTotal = 150;
        Medicamento mdMock = mock(Medicamento.class);
        DistribuidorSUS dsMock = mock(DistribuidorSUS.class);

        var requestJson = """
            {
              "uuid": "%s",
              "quantidade": %d
            }
        """.formatted(uuidRequest, quantidade);

        var response = new AtualizarEstoqueMedicamentoResponse(
                uuidRequest,
                mdMock,
                dsMock,
                novoTotal,
                LocalDate.now());

        Mockito.when(estoqueUseCase.adicionarQuantidade(Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(put("/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(uuidRequest.toString()))
                .andExpect(jsonPath("$.quantidade").value(novoTotal));
    }

    @Test
    @DisplayName("Deve listar medicamentos por nome com paginação")
    void deveListarMedicamentosPorNome() throws Exception {
        String nomeRemedio = "Dipirona";
        DistribuidorSUS dsMock = mock(DistribuidorSUS.class);

        var estoqueResponse = new EstoqueMedicamentoResponse(dsMock);
        var page = new PageImpl<>(List.of(estoqueResponse));

        Mockito.when(listagemUseCase.listar(Mockito.eq(nomeRemedio), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(page);

        mockMvc.perform(get("/estoque/{nomeRemedio}", nomeRemedio)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").exists());
    }

    // Error test scenarios
    @Test
    @DisplayName("Deve retornar 400 quando quantidade é negativa")
    void deveRetornar400QuandoQuantidadeNegativa() throws Exception {
        UUID uuidRequest = UUID.randomUUID();
        int quantidadeNegativa = -10;

        var requestJson = """
            {
              "uuid": "%s",
              "quantidade": %d
            }
        """.formatted(uuidRequest, quantidadeNegativa);

        Mockito.when(estoqueUseCase.adicionarQuantidade(Mockito.any()))
                .thenThrow(new IllegalArgumentException("Quantidade deve ser positiva"));

        mockMvc.perform(put("/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando quantidade é zero")
    void deveRetornar400QuandoQuantidadeZero() throws Exception {
        UUID uuidRequest = UUID.randomUUID();
        int quantidadeZero = 0;

        var requestJson = """
            {
              "uuid": "%s",
              "quantidade": %d
            }
        """.formatted(uuidRequest, quantidadeZero);

        Mockito.when(estoqueUseCase.adicionarQuantidade(Mockito.any()))
                .thenThrow(new IllegalArgumentException("Quantidade deve ser positiva"));

        mockMvc.perform(put("/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 500 quando use case lança RuntimeException")
    void deveRetornar500QuandoUseCaseLancaExcecao() throws Exception {
        UUID uuidRequest = UUID.randomUUID();
        int quantidade = 50;

        var requestJson = """
            {
              "uuid": "%s",
              "quantidade": %d
            }
        """.formatted(uuidRequest, quantidade);

        Mockito.when(estoqueUseCase.adicionarQuantidade(Mockito.any()))
                .thenThrow(new RuntimeException("Erro interno"));

        mockMvc.perform(put("/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError());
    }


    @Test
    @DisplayName("Deve retornar 400 quando tamanho da página é negativo")
    void deveRetornar400QuandoTamanhoPaginaNegativo() throws Exception {
        String nomeRemedio = "Dipirona";

        Mockito.when(listagemUseCase.listar(Mockito.anyString(), Mockito.anyInt(), Mockito.intThat(size -> size < 0)))
                .thenThrow(new IllegalArgumentException("Size must be positive"));

        mockMvc.perform(get("/estoque/{nomeRemedio}", nomeRemedio)
                        .param("page", "0")
                        .param("size", "-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 200 quando busca retorna lista vazia")
    void deveRetornar200QuandoListaVazia() throws Exception {
        String nomeRemedio = "MedicamentoInexistente";

        var page = new PageImpl<EstoqueMedicamentoResponse>(List.of());

        Mockito.when(listagemUseCase.listar(Mockito.eq(nomeRemedio), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(page);

        mockMvc.perform(get("/estoque/{nomeRemedio}", nomeRemedio)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    @DisplayName("Deve retornar 500 quando listagem lança RuntimeException")
    void deveRetornar500QuandoListagemLancaExcecao() throws Exception {
        String nomeRemedio = "Dipirona";

        Mockito.when(listagemUseCase.listar(Mockito.eq(nomeRemedio), Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(new RuntimeException("Erro na busca"));

        mockMvc.perform(get("/estoque/{nomeRemedio}", nomeRemedio)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar 400 quando UUID é inválido")
    void deveRetornar400QuandoUuidInvalido() throws Exception {
        var requestJson = """
            {
              "uuid": "uuid-invalido",
              "quantidade": 50
            }
        """;

        mockMvc.perform(put("/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando JSON está malformado")
    void deveRetornar400QuandoJsonMalformado() throws Exception {
        var requestJson = """
            {
              "uuid": "550e8400-e29b-41d4-a716-446655440000",
              "quantidade": 50
            """;

        mockMvc.perform(put("/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando corpo da requisição está vazio")
    void deveRetornar400QuandoCorpoVazio() throws Exception {
        mockMvc.perform(put("/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 415 quando Content-Type não é JSON")
    void deveRetornar415QuandoContentTypeNaoJson() throws Exception {
        UUID uuidRequest = UUID.randomUUID();
        int quantidade = 50;
        var requestBody = "uuid=" + uuidRequest + "&quantidade=" + quantidade;

        mockMvc.perform(put("/estoque")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(requestBody))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("Deve retornar 409 quando use case lança IllegalStateException")
    void deveRetornar409QuandoUseCaseLancaIllegalStateException() throws Exception {
        UUID uuidRequest = UUID.randomUUID();
        int quantidade = 50;
        var requestJson = """
            {
              "uuid": "%s",
              "quantidade": %d
            }
        """.formatted(uuidRequest, quantidade);

        Mockito.when(estoqueUseCase.adicionarQuantidade(Mockito.any()))
                .thenThrow(new IllegalStateException("Estoque insuficiente"));

        mockMvc.perform(put("/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deve retornar 404 quando nomeRemedio está vazio na URL")
    void deveRetornar404QuandoNomeRemedioVazioNaUrl() throws Exception {
        mockMvc.perform(get("/estoque/")  // URL sem path variable
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 400 quando use case lança IllegalArgumentException")
    void deveRetornar400QuandoUseCaseLancaIllegalArgumentException() throws Exception {
        String nomeInvalido = "abc";

        Mockito.when(listagemUseCase.listar(Mockito.eq(nomeInvalido), Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(new IllegalArgumentException("Nome do remédio inválido"));

        mockMvc.perform(get("/estoque/{nomeRemedio}", nomeInvalido)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando page é negativo")
    void deveRetornar400QuandoPageNegativo() throws Exception {
        String nomeRemedio = "Dipirona";
        Mockito.when(listagemUseCase.listar(Mockito.eq(nomeRemedio), Mockito.intThat(page -> page < 0), Mockito.anyInt()))
                .thenThrow(new IllegalArgumentException("Page must be non-negative"));

        mockMvc.perform(get("/estoque/{nomeRemedio}", nomeRemedio)
                        .param("page", "-1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando size é zero")
    void deveRetornar400QuandoSizeZero() throws Exception {
        String nomeRemedio = "Dipirona";
        Mockito.when(listagemUseCase.listar(Mockito.eq(nomeRemedio), Mockito.anyInt(), Mockito.intThat(size -> size <= 0)))
                .thenThrow(new IllegalArgumentException("Size must be positive"));

        mockMvc.perform(get("/estoque/{nomeRemedio}", nomeRemedio)
                        .param("page", "0")
                        .param("size", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando size é muito grande")
    void deveRetornar400QuandoSizeMuitoGrande() throws Exception {
        String nomeRemedio = "Dipirona";
        Mockito.when(listagemUseCase.listar(Mockito.eq(nomeRemedio), Mockito.anyInt(), Mockito.intThat(size -> size > 100)))
                .thenThrow(new IllegalArgumentException("Size too large"));

        mockMvc.perform(get("/estoque/{nomeRemedio}", nomeRemedio)
                        .param("page", "0")
                        .param("size", "101")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}