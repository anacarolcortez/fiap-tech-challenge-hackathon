package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.EstoqueMedicamentoResponse;

import org.springframework.data.domain.Page;

public interface ListarRemediosDisponiveisPorNomePortIn {

    Page<EstoqueMedicamentoResponse> listar(String nomeMedicamento, int page, int size);
}
