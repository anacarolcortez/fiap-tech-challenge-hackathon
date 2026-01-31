package br.org.sus.ms_medicamentos.application.usuario_medicamento.ports.in;

import br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto.UsuarioMedicamentoInput;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto.UsuarioMedicamentoOutput;

public interface CadastrarUsuarioMedicamentoPortIn {
    UsuarioMedicamentoOutput cadastrar(UsuarioMedicamentoInput input);
}
