package br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UsuarioMedicamentoOutput(
        UUID usuarioMedicamentoId,
        UUID usuarioId,
        UUID medicamentoId,
        boolean notificacaoAtiva,
        LocalDate dataCadastro
) {
}
