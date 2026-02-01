package br.org.sus.ms_medicamentos.application.usuario_medicamento.ports.out;

import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.domain.Usuario;
import br.org.sus.ms_medicamentos.domain.UsuarioMedicamento;

import java.util.UUID;

public interface UsuarioMedicamentoPortOut {
    Usuario buscarUsuarioPorId(UUID id);
    Medicamento buscarMedicamentoPorId(UUID id);
    UsuarioMedicamento salvar(UsuarioMedicamento usuarioMedicamento);
}
