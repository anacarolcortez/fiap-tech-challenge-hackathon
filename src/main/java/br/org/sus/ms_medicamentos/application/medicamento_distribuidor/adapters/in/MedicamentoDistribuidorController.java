package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.request.AtualizarEstoqueMedicamentoRequest;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.AtualizarEstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.EstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in.AtualizarEstoqueMedicamentoPortIn;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in.ListarRemediosDisponiveisPorNomePortIn;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
public class MedicamentoDistribuidorController {

    private final AtualizarEstoqueMedicamentoPortIn estoqueUseCase;
    private final ListarRemediosDisponiveisPorNomePortIn listagemUseCase;

    public MedicamentoDistribuidorController(AtualizarEstoqueMedicamentoPortIn estoqueUseCase, ListarRemediosDisponiveisPorNomePortIn listagemUseCase) {
        this.estoqueUseCase = estoqueUseCase;
        this.listagemUseCase = listagemUseCase;
    }

    @PutMapping
    public ResponseEntity<AtualizarEstoqueMedicamentoResponse> atualizarEstoque(@RequestBody AtualizarEstoqueMedicamentoRequest request){
        var response = estoqueUseCase.adicionarQuantidade(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{nomeRemedio}")
    public ResponseEntity<Page<EstoqueMedicamentoResponse>> listarPorNome(
            @PathVariable("nomeRemedio") String nomeRemedio,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var result = listagemUseCase.listar(nomeRemedio, page, size);
        return ResponseEntity.ok(result);
    }


}
