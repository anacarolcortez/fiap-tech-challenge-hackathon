package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.usecases;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.EstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.out.ListarRemediosDisponiveisPorNomePortOut;
import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarRemediosDisponiveisPorNomeUseCaseTest {

    @Mock
    private ListarRemediosDisponiveisPorNomePortOut listarRemediosDisponiveisPorNomePortOut;

    @InjectMocks
    private ListarRemediosDisponiveisPorNomeUseCase useCase;

    @Test
    @DisplayName("Deve listar remédios disponíveis por nome com paginação")
    void deveListarRemediosComSucesso() {
        String nomeMedicamento = "Dipirona";
        int pagina = 0;
        int tamanho = 10;

        MedicamentoDistribuidor mdMock = mock(MedicamentoDistribuidor.class);

        Page<MedicamentoDistribuidor> pageMock = new PageImpl<>(List.of(mdMock), PageRequest.of(pagina, tamanho), 1);

        when(listarRemediosDisponiveisPorNomePortOut.listar(nomeMedicamento, pagina, tamanho))
                .thenReturn(pageMock);

        Page<EstoqueMedicamentoResponse> resultado = useCase.listar(nomeMedicamento, pagina, tamanho);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(listarRemediosDisponiveisPorNomePortOut, times(1)).listar(nomeMedicamento, pagina, tamanho);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando nenhum medicamento for encontrado")
    void deveRetornarVazioQuandoNaoEncontrarMedicamento() {
        String nomeInexistente = "MedicamentoX";
        Page<MedicamentoDistribuidor> pageVazia = Page.empty();

        when(listarRemediosDisponiveisPorNomePortOut.listar(eq(nomeInexistente), anyInt(), anyInt()))
                .thenReturn(pageVazia);

        Page<EstoqueMedicamentoResponse> resultado = useCase.listar(nomeInexistente, 0, 10);

        assertTrue(resultado.isEmpty());
        verify(listarRemediosDisponiveisPorNomePortOut, times(1)).listar(nomeInexistente, 0, 10);
    }
}
