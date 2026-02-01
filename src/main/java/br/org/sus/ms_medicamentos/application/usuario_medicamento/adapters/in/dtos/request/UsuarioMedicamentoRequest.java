package br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.in.dtos.request;

import java.util.UUID;

public record UsuarioMedicamentoRequest(
        UUID usuarioId,
        UUID medicamentoId,
        boolean notificacaoAtiva
) {
}
