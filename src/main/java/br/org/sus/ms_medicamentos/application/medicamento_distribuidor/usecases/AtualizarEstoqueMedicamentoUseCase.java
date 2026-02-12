package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.usecases;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.request.AtualizarEstoqueMedicamentoRequest;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.AtualizarEstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in.AtualizarEstoqueMedicamentoPortIn;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.out.AtualizarEstoqueMedicamentoPortOut;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.out.ConsultarEstoqueMedicamentoPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AtualizarEstoqueMedicamentoUseCase implements AtualizarEstoqueMedicamentoPortIn {

    private static final Logger logger = LoggerFactory.getLogger(AtualizarEstoqueMedicamentoUseCase.class);

    private final AtualizarEstoqueMedicamentoPortOut atualizarEstoqueMedicamentoPortOut;
    private final ConsultarEstoqueMedicamentoPortOut consultarEstoqueMedicamentoPortOut;

    public AtualizarEstoqueMedicamentoUseCase(AtualizarEstoqueMedicamentoPortOut atualizarEstoqueMedicamentoPortOut, ConsultarEstoqueMedicamentoPortOut consultarEstoqueMedicamentoPortOut) {
        this.atualizarEstoqueMedicamentoPortOut = atualizarEstoqueMedicamentoPortOut;
        this.consultarEstoqueMedicamentoPortOut = consultarEstoqueMedicamentoPortOut;
    }

    @Override
    public AtualizarEstoqueMedicamentoResponse adicionarQuantidade(AtualizarEstoqueMedicamentoRequest request) {
        logger.info("Consultando medicamento-distribuidor com o UUID " + request.uuid());
        var medicamentoDistribuidor = consultarEstoqueMedicamentoPortOut.consultar(request.uuid());

        if (medicamentoDistribuidor == null){
            throw new IllegalArgumentException("Erro ao consultar medicamento-distribuidor com UUID: " + request.uuid());
        }

        if (request.quantidade() <= 0){
            throw new IllegalArgumentException("A quantidade a acrescentar ao estoque deve ser maior que zero");
        }

        logger.info("Atualizando estoque de medicamento-distribuidor com o UUID " + request.uuid());
        var medicamentoAtualizado = atualizarEstoqueMedicamentoPortOut.adicionarQuantidade(medicamentoDistribuidor.getUuid(), request.quantidade());

        return AtualizarEstoqueMedicamentoResponse.fromDomain(medicamentoAtualizado);
    }
}
