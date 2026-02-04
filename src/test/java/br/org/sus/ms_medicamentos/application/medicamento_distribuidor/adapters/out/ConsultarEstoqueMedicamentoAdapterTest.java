package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.out;

import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaDistribuidorEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoDistribuidorEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.repository.JpaMedicamentoDistribuidorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarEstoqueMedicamentoAdapterTest {

    @Mock
    private JpaMedicamentoDistribuidorRepository repository;

    @InjectMocks
    private ConsultarEstoqueMedicamentoAdapter adapter;

    @Test
    @DisplayName("Deve consultar estoque por UUID e converter para objeto de domínio")
    void deveConsultarComSucesso() {
        UUID uuidBusca = UUID.randomUUID();

        var medicamentoEntity = new JpaMedicamentoEntity();
        medicamentoEntity.setUuid(UUID.randomUUID());

        var distribuidorEntity = new JpaDistribuidorEntity();
        distribuidorEntity.setUuid(UUID.randomUUID());

        var entityMock = new JpaMedicamentoDistribuidorEntity();
        entityMock.setUuid(uuidBusca);
        entityMock.setQuantidade(50);
        entityMock.setMedicamento(medicamentoEntity);
        entityMock.setDistribuidor(distribuidorEntity);

        when(repository.findByUuid(uuidBusca)).thenReturn(entityMock);

        MedicamentoDistribuidor resultado = adapter.consultar(uuidBusca);

        assertNotNull(resultado);
        assertEquals(uuidBusca, resultado.getUuid());
        verify(repository, times(1)).findByUuid(uuidBusca);
    }

    @Test
    @DisplayName("Deve retornar null quando o repositório não encontrar o registro")
    void deveRetornarNullQuandoNaoEncontrado() {
        UUID uuidInexistente = UUID.randomUUID();
        when(repository.findByUuid(uuidInexistente)).thenReturn(null);

        MedicamentoDistribuidor resultado = adapter.consultar(uuidInexistente);

        assertNull(resultado);
        verify(repository, times(1)).findByUuid(uuidInexistente);
    }
}