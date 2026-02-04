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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarRemediosDisponiveisPorNomeAdapterTest {

    @Mock
    private JpaMedicamentoDistribuidorRepository repository;

    @InjectMocks
    private ListarRemediosDisponiveisPorNomeAdapter adapter;

    @Test
    @DisplayName("Deve retornar uma página de domínio mapeada corretamente a partir da entidade")
    void deveListarERetornarPaginaDeDominio() {
        String nomeRemedio = "Dipirona";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        JpaMedicamentoDistribuidorEntity entity = criarEntityCompleta(nomeRemedio);
        Page<JpaMedicamentoDistribuidorEntity> pageEntity = new PageImpl<>(List.of(entity), pageable, 1);

        when(repository.findByNomeRemedio(eq(nomeRemedio), any(Pageable.class))).thenReturn(pageEntity);

        Page<MedicamentoDistribuidor> resultado = adapter.listar(nomeRemedio, page, size);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals(nomeRemedio, resultado.getContent().get(0).getMedicamento().getNome());

        verify(repository, times(1)).findByNomeRemedio(nomeRemedio, pageable);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando o repositório não encontrar resultados")
    void deveRetornarPaginaVazia() {
        String nomeRemedio = "Inexistente";
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findByNomeRemedio(eq(nomeRemedio), any(Pageable.class))).thenReturn(Page.empty());

        Page<MedicamentoDistribuidor> resultado = adapter.listar(nomeRemedio, 0, 10);

        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.getTotalElements());
        verify(repository, times(1)).findByNomeRemedio(nomeRemedio, pageable);
    }

    private JpaMedicamentoDistribuidorEntity criarEntityCompleta(String nomeMedicamento) {
        var medicamento = new JpaMedicamentoEntity();
        medicamento.setUuid(UUID.randomUUID());
        medicamento.setNome(nomeMedicamento);

        var distribuidor = new JpaDistribuidorEntity();
        distribuidor.setUuid(UUID.randomUUID());

        var entity = new JpaMedicamentoDistribuidorEntity();
        entity.setUuid(UUID.randomUUID());
        entity.setQuantidade(100);
        entity.setMedicamento(medicamento);
        entity.setDistribuidor(distribuidor);

        return entity;
    }
}