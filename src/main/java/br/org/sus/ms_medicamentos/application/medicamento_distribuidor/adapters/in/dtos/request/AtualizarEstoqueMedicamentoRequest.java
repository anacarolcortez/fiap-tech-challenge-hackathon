package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.request;


import java.util.UUID;

public record AtualizarEstoqueMedicamentoRequest(
        UUID uuid,
        int quantidade
) {
}
