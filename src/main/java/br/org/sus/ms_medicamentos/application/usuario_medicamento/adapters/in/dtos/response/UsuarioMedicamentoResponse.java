package br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.in.dtos.response;

import java.time.LocalDate;
import java.util.UUID;

public record UsuarioMedicamentoResponse(
        UUID id,
        boolean notificacaoAtiva,
        LocalDate dataCadastro
) {
}
