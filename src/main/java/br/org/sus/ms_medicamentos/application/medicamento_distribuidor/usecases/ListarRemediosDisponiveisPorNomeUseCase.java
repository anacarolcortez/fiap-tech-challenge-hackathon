package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.usecases;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.EstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in.ListarRemediosDisponiveisPorNomePortIn;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.out.ListarRemediosDisponiveisPorNomePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ListarRemediosDisponiveisPorNomeUseCase implements ListarRemediosDisponiveisPorNomePortIn {

    private static final Logger logger = LoggerFactory.getLogger(ListarRemediosDisponiveisPorNomeUseCase.class);

    private final ListarRemediosDisponiveisPorNomePortOut listarRemediosDisponiveisPorNomePortOut;

    public ListarRemediosDisponiveisPorNomeUseCase(ListarRemediosDisponiveisPorNomePortOut listarRemediosDisponiveisPorNomePortOut) {
        this.listarRemediosDisponiveisPorNomePortOut = listarRemediosDisponiveisPorNomePortOut;
    }

    @Override
    public Page<EstoqueMedicamentoResponse> listar(String nomeMedicamento, int page, int size) {
        logger.info("Consultando distribuidores com o medicamento: " + nomeMedicamento);

        var pageDistribuidores =
                listarRemediosDisponiveisPorNomePortOut.listar(nomeMedicamento, page, size);

        return EstoqueMedicamentoResponse.fromDomainPage(pageDistribuidores);
    }
}
