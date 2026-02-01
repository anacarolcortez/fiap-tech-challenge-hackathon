package br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto;

import java.util.UUID;

public record UsuarioMedicamentoInput(
        UUID usuarioId,
        UUID medicamentoId,
        boolean notificacaoAtiva
) {
}
