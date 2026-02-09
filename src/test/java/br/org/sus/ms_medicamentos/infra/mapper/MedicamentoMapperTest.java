package br.org.sus.ms_medicamentos.infra.mapper;

import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MedicamentoMapperTest {

    @Test
    void deveMapearParaDomainCorretamente() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String nome = "Dipirona";
        String lote = "Lote123";
        LocalDate validade = LocalDate.of(2024, 12, 31);
        JpaMedicamentoEntity entity = new JpaMedicamentoEntity(uuid, nome, lote, validade);

        // Act
        Medicamento result = MedicamentoMapper.toDomain(entity);

        // Assert
        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
        assertEquals(nome, result.getNome());
        assertEquals(lote, result.getLote());
        assertEquals(validade, result.getValidade());
    }

    @Test
    void deveMapearParaEntityCorretamente() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String nome = "Paracetamol";
        String lote = "Lote456";
        LocalDate validade = LocalDate.of(2025, 6, 15);
        Medicamento domain = new Medicamento(uuid, nome, lote, validade);

        // Act
        JpaMedicamentoEntity result = MedicamentoMapper.toEntity(domain);

        // Assert
        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
        assertEquals(nome, result.getNome());
        assertEquals(lote, result.getLote());
        assertEquals(validade, result.getValidade());
    }
}