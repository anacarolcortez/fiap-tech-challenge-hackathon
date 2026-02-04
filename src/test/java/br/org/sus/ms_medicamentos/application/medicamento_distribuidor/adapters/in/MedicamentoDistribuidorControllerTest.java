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
}