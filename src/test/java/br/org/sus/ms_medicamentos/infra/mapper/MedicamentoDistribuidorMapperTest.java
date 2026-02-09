package br.org.sus.ms_medicamentos.infra.mapper;

import br.org.sus.ms_medicamentos.domain.DistribuidorSUS;
import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoDistribuidorEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaDistribuidorEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoEntity;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MedicamentoDistribuidorMapperTest {

    @Test
    void deveMapearParaDomainCorretamente() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        int quantidade = 100;
        LocalDate ultimaAtualizacao = LocalDate.of(2023, 10, 1);

        JpaMedicamentoEntity jpaMedicamento = Mockito.mock(JpaMedicamentoEntity.class);
        JpaDistribuidorEntity jpaDistribuidor = Mockito.mock(JpaDistribuidorEntity.class);
        JpaMedicamentoDistribuidorEntity entity = new JpaMedicamentoDistribuidorEntity(uuid, jpaMedicamento, jpaDistribuidor, quantidade, ultimaAtualizacao);

        Medicamento medicamentoMock = Mockito.mock(Medicamento.class);
        DistribuidorSUS distribuidorMock = Mockito.mock(DistribuidorSUS.class);

        try (MockedStatic<MedicamentoMapper> medicamentoMapperMock = Mockito.mockStatic(MedicamentoMapper.class);
             MockedStatic<DistribuidorMapper> distribuidorMapperMock = Mockito.mockStatic(DistribuidorMapper.class)) {

            medicamentoMapperMock.when(() -> MedicamentoMapper.toDomain(jpaMedicamento)).thenReturn(medicamentoMock);
            distribuidorMapperMock.when(() -> DistribuidorMapper.toDomain(jpaDistribuidor)).thenReturn(distribuidorMock);

            // Act
            MedicamentoDistribuidor result = MedicamentoDistribuidorMapper.toDomain(entity);

            // Assert
            assertNotNull(result);
            assertEquals(uuid, result.getUuid());
            assertEquals(medicamentoMock, result.getMedicamento());
            assertEquals(distribuidorMock, result.getDistribuidor());
            assertEquals(quantidade, result.getQuantidade());
            // ultimaAtualizacao is set to LocalDate.now() in constructor, so assert it's not null
            assertNotNull(result.getUltimaAtualizacao());
        }
    }

    @Test
    void deveMapearParaEntityCorretamente() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        int quantidade = 200;
        LocalDate ultimaAtualizacao = LocalDate.of(2023, 10, 2);

        Medicamento medicamentoMock = Mockito.mock(Medicamento.class);
        DistribuidorSUS distribuidorMock = Mockito.mock(DistribuidorSUS.class);
        MedicamentoDistribuidor domain = new MedicamentoDistribuidor(uuid, medicamentoMock, distribuidorMock, quantidade);
        // Set ultimaAtualizacao manually for test
        domain = Mockito.spy(domain);
        Mockito.when(domain.getUltimaAtualizacao()).thenReturn(ultimaAtualizacao);

        JpaMedicamentoEntity jpaMedicamentoMock = Mockito.mock(JpaMedicamentoEntity.class);
        JpaDistribuidorEntity jpaDistribuidorMock = Mockito.mock(JpaDistribuidorEntity.class);

        try (MockedStatic<MedicamentoMapper> medicamentoMapperMock = Mockito.mockStatic(MedicamentoMapper.class);
             MockedStatic<DistribuidorMapper> distribuidorMapperMock = Mockito.mockStatic(DistribuidorMapper.class)) {

            medicamentoMapperMock.when(() -> MedicamentoMapper.toEntity(medicamentoMock)).thenReturn(jpaMedicamentoMock);
            distribuidorMapperMock.when(() -> DistribuidorMapper.toEntity(distribuidorMock)).thenReturn(jpaDistribuidorMock);

            // Act
            JpaMedicamentoDistribuidorEntity result = MedicamentoDistribuidorMapper.toEntity(domain);

            // Assert
            assertNotNull(result);
            assertEquals(uuid, result.getUuid());
            assertEquals(jpaMedicamentoMock, result.getMedicamento());
            assertEquals(jpaDistribuidorMock, result.getDistribuidor());
            assertEquals(quantidade, result.getQuantidade());
            assertEquals(ultimaAtualizacao, result.getUltimaAtualizacao());
        }
    }
}