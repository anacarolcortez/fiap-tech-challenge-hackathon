package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response;

import br.org.sus.ms_medicamentos.domain.DistribuidorSUS;
import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public record EstoqueMedicamentoResponse(
        DistribuidorSUS distribuidor
) {

    public static Page<EstoqueMedicamentoResponse> fromDomainPage(
            Page<MedicamentoDistribuidor> page
    ) {
        return page.map(md ->
                new EstoqueMedicamentoResponse(md.getDistribuidor())
        );
    }
}
