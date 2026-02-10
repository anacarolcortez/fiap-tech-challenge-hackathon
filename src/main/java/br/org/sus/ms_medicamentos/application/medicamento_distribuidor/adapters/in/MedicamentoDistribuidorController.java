package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.request.AtualizarEstoqueMedicamentoRequest;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.AtualizarEstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.in.dtos.response.EstoqueMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in.AtualizarEstoqueMedicamentoPortIn;
import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.in.ListarRemediosDisponiveisPorNomePortIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
@Tag(name = "Estoque de Medicamentos", description = "Endpoints para gestão de estoque e consulta de disponibilidade")
public class MedicamentoDistribuidorController {

    private final AtualizarEstoqueMedicamentoPortIn estoqueUseCase;
    private final ListarRemediosDisponiveisPorNomePortIn listagemUseCase;

    public MedicamentoDistribuidorController(AtualizarEstoqueMedicamentoPortIn estoqueUseCase, ListarRemediosDisponiveisPorNomePortIn listagemUseCase) {
        this.estoqueUseCase = estoqueUseCase;
        this.listagemUseCase = listagemUseCase;
    }

    @Operation(summary = "Atualiza o estoque", description = "Adiciona quantidades ao estoque de um medicamento específico.")
    @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso")
    @PutMapping
    public ResponseEntity<AtualizarEstoqueMedicamentoResponse> atualizarEstoque(@RequestBody AtualizarEstoqueMedicamentoRequest request){
        var response = estoqueUseCase.adicionarQuantidade(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Lista remédios por nome", description = "Busca medicamentos disponíveis no estoque filtrando pelo nome, com suporte a paginação.")
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
