package br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.in.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UsuarioMedicamentoRequest(
        @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
        UUID usuarioId,

        @Schema(example = "10")
        UUID medicamentoId,

        @Schema(example = "true")
        boolean notificacaoAtiva
) {
}
