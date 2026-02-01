package br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases;

import br.org.sus.ms_medicamentos.application.usuario_medicamento.ports.out.UsuarioMedicamentoPortOut;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto.UsuarioMedicamentoInput;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto.UsuarioMedicamentoOutput;
import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.domain.TipoUsuario;
import br.org.sus.ms_medicamentos.domain.Usuario;
import br.org.sus.ms_medicamentos.domain.UsuarioMedicamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarUsuarioMedicamentoUseCaseTest {

    @Mock
    private UsuarioMedicamentoPortOut usuarioMedicamentoPortOut;

    @InjectMocks
    private CadastrarUsuarioMedicamentoUseCase cadastrarUsuarioMedicamentoUseCase;

    private UUID usuarioId;
    private UUID medicamentoId;
    private Usuario usuario;
    private Medicamento medicamento;
    private UsuarioMedicamentoInput input;
    private UsuarioMedicamento usuarioMedicamentoSalvo;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        medicamentoId = UUID.randomUUID();
        
        usuario = new Usuario(
                usuarioId,
                "11122233344",
                "11912345678",
                "email@email.com",
                "senh@Segur@",
                TipoUsuario.PACIENTE
        );

        medicamento = new Medicamento(
                medicamentoId,
                "nomeDoMedicamento",
                "123456",
                LocalDate.of(2029, 12, 30)
        );

        input = new UsuarioMedicamentoInput(usuarioId, medicamentoId, true);
        
        usuarioMedicamentoSalvo = new UsuarioMedicamento(
            UUID.randomUUID(),
            usuario,
            medicamento,
            true,
            LocalDate.now()
        );
    }

    @Test
    void deveCadastrarUsuarioMedicamentoComSucesso() {
        // Arrange
        when(usuarioMedicamentoPortOut.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);
        when(usuarioMedicamentoPortOut.buscarMedicamentoPorId(medicamentoId)).thenReturn(medicamento);
        when(usuarioMedicamentoPortOut.salvar(any(UsuarioMedicamento.class))).thenReturn(usuarioMedicamentoSalvo);

        // Act
        UsuarioMedicamentoOutput output = cadastrarUsuarioMedicamentoUseCase.cadastrar(input);

        // Assert
        assertNotNull(output);
        assertEquals(usuarioMedicamentoSalvo.getUuid(), output.usuarioMedicamentoId());
        assertEquals(usuarioId, output.usuarioId());
        assertEquals(medicamentoId, output.medicamentoId());
        assertTrue(output.notificacaoAtiva());
        assertNotNull(output.dataCadastro());
        
        verify(usuarioMedicamentoPortOut).buscarUsuarioPorId(usuarioId);
        verify(usuarioMedicamentoPortOut).buscarMedicamentoPorId(medicamentoId);
        verify(usuarioMedicamentoPortOut).salvar(any(UsuarioMedicamento.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(usuarioMedicamentoPortOut.buscarUsuarioPorId(usuarioId)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            cadastrarUsuarioMedicamentoUseCase.cadastrar(input);
        });

        verify(usuarioMedicamentoPortOut).buscarUsuarioPorId(usuarioId);
        verify(usuarioMedicamentoPortOut, never()).buscarMedicamentoPorId(any());
        verify(usuarioMedicamentoPortOut, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoMedicamentoNaoEncontrado() {
        // Arrange
        when(usuarioMedicamentoPortOut.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);
        when(usuarioMedicamentoPortOut.buscarMedicamentoPorId(medicamentoId)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            cadastrarUsuarioMedicamentoUseCase.cadastrar(input);
        });

        verify(usuarioMedicamentoPortOut).buscarUsuarioPorId(usuarioId);
        verify(usuarioMedicamentoPortOut).buscarMedicamentoPorId(medicamentoId);
        verify(usuarioMedicamentoPortOut, never()).salvar(any());
    }
}
