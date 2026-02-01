package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.out;

import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;

import org.springframework.data.domain.Page;

public interface ListarRemediosDisponiveisPorNomePortOut {

    Page<MedicamentoDistribuidor> listar(String nomeMedicamento, int page, int size);
}
