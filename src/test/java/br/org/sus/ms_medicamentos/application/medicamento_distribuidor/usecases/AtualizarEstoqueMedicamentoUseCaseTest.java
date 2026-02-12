package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.usecases;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.request.AtualizarEstoqueMedicamentoRequest;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.AtualizarEstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.out.AtualizarEstoqueMedicamentoPortOut;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.out.ConsultarEstoqueMedicamentoPortOut;
import br.org.sus.ms_medicamentos.domain.DistribuidorSUS;
import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;
import br.org.sus.ms_medicamentos.domain.TipoDistribuidor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizarEstoqueMedicamentoUseCaseTest {

    @Mock
    private AtualizarEstoqueMedicamentoPortOut atualizarEstoqueMedicamentoPortOut;

    @Mock
    private ConsultarEstoqueMedicamentoPortOut consultarEstoqueMedicamentoPortOut;

    @InjectMocks
    private AtualizarEstoqueMedicamentoUseCase atualizarEstoqueMedicamentoUseCase;

    @Test
    @DisplayName("Deve adicionar quantidade ao estoque com sucesso")
    void deveAdicionarQuantidadeComSucesso() {

        var uuid = UUID.randomUUID();
        var request = new AtualizarEstoqueMedicamentoRequest(uuid, 50);

        var medicamentoMock = new MedicamentoDistribuidor(
                uuid,
                new Medicamento(
                        UUID.randomUUID(),
                        "Fluoxetina",
                        "IFJ668DYFSDF",
                        LocalDate.now()
                ),
                new DistribuidorSUS(
                        UUID.randomUUID(),
                        "DrogaRaia FarmaciaPopular",
                        "79.836.356/0001-10",
                        "11 5253-78-79",
                        "De seg à dom, das 7h às 22h",
                        TipoDistribuidor.FARMACIA_CREDENCIADA,
                        "Av das Flores, 123",
                        "Saúde",
                        "São Paulo",
                        "SP",
                        "12032-123"
                ),
                50
        );

        when(consultarEstoqueMedicamentoPortOut.consultar(uuid)).thenReturn(medicamentoMock);

        AtualizarEstoqueMedicamentoResponse response = atualizarEstoqueMedicamentoUseCase.adicionarQuantidade(request);

        assertNotNull(response);
        verify(consultarEstoqueMedicamentoPortOut, times(1)).consultar(uuid);
        verify(atualizarEstoqueMedicamentoPortOut, times(1)).adicionarQuantidade(uuid, 50);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o medicamento não for encontrado")
    void deveLancarExcecaoQuandoMedicamentoNaoExistir() {

        var uuid = UUID.randomUUID();
        var request = new AtualizarEstoqueMedicamentoRequest(uuid, 10);

        when(consultarEstoqueMedicamentoPortOut.consultar(uuid)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            atualizarEstoqueMedicamentoUseCase.adicionarQuantidade(request);
        });

        assertEquals("Erro ao consultar medicamento-distribuidor com UUID: " + uuid, exception.getMessage());
        verify(atualizarEstoqueMedicamentoPortOut, never()).adicionarQuantidade(any(), anyInt());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a quantidade for zero ou negativa")
    void deveLancarExcecaoQuandoQuantidadeForInvalida() {
        var uuid = UUID.randomUUID();
        var request = new AtualizarEstoqueMedicamentoRequest(uuid, 0); // Quantidade zero

        var medicamentoDistribuidorMock = new MedicamentoDistribuidor(
                uuid,
                new Medicamento(UUID.randomUUID(), "Fluoxetina", "ABC123", LocalDate.now()),
                new DistribuidorSUS(UUID.randomUUID(), "Teste", "00.000.000/0001-00", "11 0000-0000", "Livre", TipoDistribuidor.FARMACIA_CREDENCIADA, "Rua X", "Bairro Y", "Cidade Z", "UF", "00000-000"),
                100);

        when(consultarEstoqueMedicamentoPortOut.consultar(uuid)).thenReturn(medicamentoDistribuidorMock);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            atualizarEstoqueMedicamentoUseCase.adicionarQuantidade(request);
        });

        assertEquals("A quantidade a acrescentar ao estoque deve ser maior que zero", exception.getMessage());
        verify(atualizarEstoqueMedicamentoPortOut, never()).adicionarQuantidade(any(), anyInt());
    }
}