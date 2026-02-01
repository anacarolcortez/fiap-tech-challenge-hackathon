package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.request.AtualizarEstoqueMedicamentoRequest;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.AtualizarEstoqueMedicamentoResponse;

public interface AtualizarEstoqueMedicamentoPortIn {

    AtualizarEstoqueMedicamentoResponse adicionarQuantidade(AtualizarEstoqueMedicamentoRequest request);
}
