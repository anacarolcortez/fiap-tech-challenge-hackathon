package br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.out;

import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.domain.TipoUsuario;
import br.org.sus.ms_medicamentos.domain.Usuario;
import br.org.sus.ms_medicamentos.domain.UsuarioMedicamento;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaUsuarioEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaUsuarioMedicamentoEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.repository.JpaMedicamentoRepository;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.repository.JpaUsuarioMedicamentoRepository;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.repository.JpaUsuarioRepository;
import br.org.sus.ms_medicamentos.infra.mapper.MedicamentoMapper;
import br.org.sus.ms_medicamentos.infra.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioMedicamentoAdapterTest {

    @Mock
    private JpaUsuarioRepository usuarioRepository;

    @Mock
    private JpaMedicamentoRepository medicamentoRepository;

    @Mock
    private JpaUsuarioMedicamentoRepository usuarioMedicamentoRepository;

    @InjectMocks
    private UsuarioMedicamentoAdapter usuarioMedicamentoAdapter;

    private UUID usuarioId;
    private UUID medicamentoId;
    private UUID usuarioMedicamentoId;
    private JpaUsuarioEntity jpaUsuarioEntity;
    private JpaMedicamentoEntity jpaMedicamentoEntity;
    private JpaUsuarioMedicamentoEntity jpaUsuarioMedicamentoEntity;
    private String cpf = "11122233344";
    private String senha = "senha";
    private String paracetamol = "paracetamol";
    private String lote = "123456";

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        medicamentoId = UUID.randomUUID();
        usuarioMedicamentoId = UUID.randomUUID();

        jpaUsuarioEntity = new JpaUsuarioEntity(
                usuarioId,
                cpf,
                senha,
                TipoUsuario.PACIENTE
        );

        jpaMedicamentoEntity = new JpaMedicamentoEntity(
                medicamentoId,
                paracetamol,
                lote,
                LocalDate.of(2029, 12, 30)
        );


       jpaUsuarioMedicamentoEntity = new JpaUsuarioMedicamentoEntity(
               usuarioMedicamentoId,
               jpaUsuarioEntity,
               jpaMedicamentoEntity,
               true,
               LocalDate.now()
       );
    }

    @Test
    void buscarUsuarioPorId_QuandoUsuarioExiste_DeveRetornarUsuario() {
        // Arrange
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(jpaUsuarioEntity));

        // Act
        Usuario usuario = usuarioMedicamentoAdapter.buscarUsuarioPorId(usuarioId);

        // Assert
        assertNotNull(usuario);
        assertEquals(usuarioId, usuario.getUuid());
        assertEquals(cpf, usuario.getCPF());
        assertEquals(senha, usuario.getSenha());
        assertEquals("PACIENTE", usuario.getTipo().name());
    }

    @Test
    void buscarUsuarioPorId_QuandoUsuarioNaoExiste_DeveLancarExcecao() {
        // Arrange
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            usuarioMedicamentoAdapter.buscarUsuarioPorId(usuarioId);
        });
    }

    @Test
    void buscarMedicamentoPorId_QuandoMedicamentoExiste_DeveRetornarMedicamento() {
        // Arrange
        when(medicamentoRepository.findById(medicamentoId)).thenReturn(Optional.of(jpaMedicamentoEntity));

        // Act
        Medicamento medicamento = usuarioMedicamentoAdapter.buscarMedicamentoPorId(medicamentoId);

        // Assert
        assertNotNull(medicamento);
        assertEquals(medicamentoId, medicamento.getUuid());
        assertEquals(paracetamol, medicamento.getNome());
        assertEquals(lote, medicamento.getLote());
        assertNotNull(medicamento.getValidade());
    }

    @Test
    void buscarMedicamentoPorId_QuandoMedicamentoNaoExiste_DeveLancarExcecao() {
        // Arrange
        when(medicamentoRepository.findById(medicamentoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            usuarioMedicamentoAdapter.buscarMedicamentoPorId(medicamentoId);
        });
    }

    @Test
    void salvar_DeveRetornarUsuarioMedicamentoSalvo() {
        // Arrange
        UsuarioMedicamento usuarioMedicamento = new UsuarioMedicamento(
                null, // ID será gerado
                UsuarioMapper.toDomain(jpaUsuarioEntity),
                MedicamentoMapper.toDomain(jpaMedicamentoEntity),
                true,
                LocalDate.now()
        );

        when(usuarioMedicamentoRepository.save(any(JpaUsuarioMedicamentoEntity.class)))
                .thenReturn(jpaUsuarioMedicamentoEntity);

        // Act
        UsuarioMedicamento resultado = usuarioMedicamentoAdapter.salvar(usuarioMedicamento);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuarioMedicamentoId, resultado.getUuid());
        assertEquals(usuarioId, resultado.getUsuario().getUuid());
        assertEquals(medicamentoId, resultado.getMedicamento().getUuid());
        assertTrue(resultado.isNotificacaoAtiva());
        assertNotNull(resultado.getDataCadastro());
    }
}