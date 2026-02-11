package br.org.sus.ms_medicamentos.infra.mapper;

import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.domain.Usuario;
import br.org.sus.ms_medicamentos.domain.UsuarioMedicamento;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaUsuarioEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaUsuarioMedicamentoEntity;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMedicamentoMapperTest {

    @Test
    void deveMapearParaDomainCorretamente() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        boolean notificacaoAtiva = true;
        LocalDate dataCadastro = LocalDate.of(2023, 10, 1);

        JpaUsuarioEntity jpaUsuario = Mockito.mock(JpaUsuarioEntity.class);
        JpaMedicamentoEntity jpaMedicamento = Mockito.mock(JpaMedicamentoEntity.class);
        JpaUsuarioMedicamentoEntity entity = new JpaUsuarioMedicamentoEntity(uuid, jpaUsuario, jpaMedicamento, notificacaoAtiva, dataCadastro);

        Usuario usuarioMock = Mockito.mock(Usuario.class);
        Medicamento medicamentoMock = Mockito.mock(Medicamento.class);

        try (MockedStatic<UsuarioMapper> usuarioMapperMock = Mockito.mockStatic(UsuarioMapper.class);
             MockedStatic<MedicamentoMapper> medicamentoMapperMock = Mockito.mockStatic(MedicamentoMapper.class)) {

            usuarioMapperMock.when(() -> UsuarioMapper.toDomain(jpaUsuario)).thenReturn(usuarioMock);
            medicamentoMapperMock.when(() -> MedicamentoMapper.toDomain(jpaMedicamento)).thenReturn(medicamentoMock);

            // Act
            UsuarioMedicamento result = UsuarioMedicamentoMapper.toDomain(entity);

            // Assert
            assertNotNull(result);
            assertEquals(uuid, result.getUuid());
            assertEquals(usuarioMock, result.getUsuario());
            assertEquals(medicamentoMock, result.getMedicamento());
            assertEquals(notificacaoAtiva, result.isNotificacaoAtiva());
            assertEquals(dataCadastro, result.getDataCadastro());
        }
    }

    @Test
    void deveMapearParaEntityCorretamente() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        boolean notificacaoAtiva = false;
        LocalDate dataCadastro = LocalDate.of(2023, 10, 2);

        Usuario usuarioMock = Mockito.mock(Usuario.class);
        Medicamento medicamentoMock = Mockito.mock(Medicamento.class);
        UsuarioMedicamento domain = new UsuarioMedicamento(uuid, usuarioMock, medicamentoMock, notificacaoAtiva, dataCadastro);

        JpaUsuarioEntity jpaUsuarioMock = Mockito.mock(JpaUsuarioEntity.class);
        JpaMedicamentoEntity jpaMedicamentoMock = Mockito.mock(JpaMedicamentoEntity.class);

        try (MockedStatic<UsuarioMapper> usuarioMapperMock = Mockito.mockStatic(UsuarioMapper.class);
             MockedStatic<MedicamentoMapper> medicamentoMapperMock = Mockito.mockStatic(MedicamentoMapper.class)) {

            usuarioMapperMock.when(() -> UsuarioMapper.toEntity(usuarioMock)).thenReturn(jpaUsuarioMock);
            medicamentoMapperMock.when(() -> MedicamentoMapper.toEntity(medicamentoMock)).thenReturn(jpaMedicamentoMock);

            // Act
            JpaUsuarioMedicamentoEntity result = UsuarioMedicamentoMapper.toEntity(domain);

            // Assert
            assertNotNull(result);
            assertEquals(uuid, result.getUuid());
            assertEquals(jpaUsuarioMock, result.getUsuario());
            assertEquals(jpaMedicamentoMock, result.getMedicamento());
            assertEquals(notificacaoAtiva, result.isNotificacaoAtiva());
            assertEquals(dataCadastro, result.getDataCadastro());
        }
    }
}