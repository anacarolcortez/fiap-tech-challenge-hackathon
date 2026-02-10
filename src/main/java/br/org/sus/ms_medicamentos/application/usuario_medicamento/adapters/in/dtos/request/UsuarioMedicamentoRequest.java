package br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.in.dtos.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UsuarioMedicamentoRequest(
        @NotNull UUID usuarioId,
        @NotNull UUID medicamentoId,
        boolean notificacaoAtiva
) {
}
