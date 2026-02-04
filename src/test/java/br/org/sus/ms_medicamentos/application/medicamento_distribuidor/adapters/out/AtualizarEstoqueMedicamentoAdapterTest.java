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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarEstoqueMedicamentoAdapterTest {

    @Mock
    private JpaMedicamentoDistribuidorRepository repository;

    @InjectMocks
    private AtualizarEstoqueMedicamentoAdapter adapter;

    @Test
    @DisplayName("Deve chamar o repositório para adicionar estoque e converter para domínio")
    void deveAdicionarEstoqueComSucesso() {
        UUID uuid = UUID.randomUUID();
        int quantidade = 100;

        var medicamentoEntity = new JpaMedicamentoEntity();
        medicamentoEntity.setUuid(UUID.randomUUID());
        medicamentoEntity.setNome("Fluoxetina");

        var distribuidorEntity = new JpaDistribuidorEntity();
        distribuidorEntity.setUuid(UUID.randomUUID());

        JpaMedicamentoDistribuidorEntity entityMock = new JpaMedicamentoDistribuidorEntity();
        entityMock.setUuid(uuid);
        entityMock.setQuantidade(150);
        entityMock.setMedicamento(medicamentoEntity);
        entityMock.setDistribuidor(distribuidorEntity);

        when(repository.addStock(uuid, quantidade)).thenReturn(entityMock);

        MedicamentoDistribuidor resultado = adapter.adicionarQuantidade(uuid, quantidade);

        assertNotNull(resultado);
        assertEquals(uuid, resultado.getUuid());
        verify(repository, times(1)).addStock(uuid, quantidade);
    }
}