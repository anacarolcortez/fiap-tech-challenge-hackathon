package br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases;

import br.org.sus.ms_medicamentos.application.usuario_medicamento.ports.in.CadastrarUsuarioMedicamentoPortIn;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.ports.out.UsuarioMedicamentoPortOut;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto.UsuarioMedicamentoInput;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto.UsuarioMedicamentoOutput;
import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.domain.Usuario;
import br.org.sus.ms_medicamentos.domain.UsuarioMedicamento;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CadastrarUsuarioMedicamentoUseCase implements CadastrarUsuarioMedicamentoPortIn {

    private final UsuarioMedicamentoPortOut usuarioMedicamentoPortOut;

    public CadastrarUsuarioMedicamentoUseCase(UsuarioMedicamentoPortOut usuarioMedicamentoPortOut) {
        this.usuarioMedicamentoPortOut = usuarioMedicamentoPortOut;
    }

    @Override
    public UsuarioMedicamentoOutput cadastrar(UsuarioMedicamentoInput input) {
        Usuario usuario = usuarioMedicamentoPortOut.buscarUsuarioPorId(input.usuarioId());
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado com ID: " + input.usuarioId());
        }

        Medicamento medicamento = usuarioMedicamentoPortOut.buscarMedicamentoPorId(input.medicamentoId());
        if (medicamento == null) {
            throw new RuntimeException("Medicamento não encontrado com ID: " + input.medicamentoId());
        }

        UsuarioMedicamento usuarioMedicamento = new UsuarioMedicamento(
                UUID.randomUUID(),
                usuario,
                medicamento,
                input.notificacaoAtiva(),
                LocalDate.now()
        );

        UsuarioMedicamento salvo = usuarioMedicamentoPortOut.salvar(usuarioMedicamento);
        return new UsuarioMedicamentoOutput(
                salvo.getUuid(),
                salvo.getUsuario().getUuid(),
                salvo.getMedicamento().getUuid(),
                salvo.isNotificacaoAtiva(),
                salvo.getDataCadastro()
        );

    }
}
