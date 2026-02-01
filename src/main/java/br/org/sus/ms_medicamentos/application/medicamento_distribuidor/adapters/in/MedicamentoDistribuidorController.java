package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.request.AtualizarEstoqueMedicamentoRequest;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.AtualizarEstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in.AtualizarEstoqueMedicamentoPortIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estoque")
public class MedicamentoDistribuidorController {

    private final AtualizarEstoqueMedicamentoPortIn useCase;

    public MedicamentoDistribuidorController(AtualizarEstoqueMedicamentoPortIn useCase) {
        this.useCase = useCase;
    }

    @PutMapping
    public ResponseEntity<AtualizarEstoqueMedicamentoResponse> atualizarEstoque(@RequestBody AtualizarEstoqueMedicamentoRequest request){
        var response = useCase.adicionarQuantidade(request);
        return ResponseEntity.ok().body(response);
    }
}
