package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.request;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record AtualizarEstoqueMedicamentoRequest(
        @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
        UUID uuid,

        @Schema(example = "10")
        int quantidade
) {
}
