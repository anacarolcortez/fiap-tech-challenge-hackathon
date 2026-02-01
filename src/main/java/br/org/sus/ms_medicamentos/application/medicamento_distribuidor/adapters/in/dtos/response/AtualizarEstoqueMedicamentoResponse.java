package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response;

import br.org.sus.ms_medicamentos.domain.DistribuidorSUS;
import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;

import java.time.LocalDate;
import java.util.UUID;

public record AtualizarEstoqueMedicamentoResponse(
        UUID uuid,
        Medicamento medicamento,
        DistribuidorSUS distribuidor,
        int quantidade,
        LocalDate ultimaAtualizacao
) {
    public static AtualizarEstoqueMedicamentoResponse fromDomain(MedicamentoDistribuidor domain) {
        return new AtualizarEstoqueMedicamentoResponse(
                domain.getUuid(),
                domain.getMedicamento(),
                domain.getDistribuidor(),
                domain.getQuantidade(),
                domain.getUltimaAtualizacao()
        );
    }
}
