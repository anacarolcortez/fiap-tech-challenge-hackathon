package br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.in;

import br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.in.dtos.request.UsuarioMedicamentoRequest;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.in.dtos.response.UsuarioMedicamentoResponse;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.ports.in.CadastrarUsuarioMedicamentoPortIn;
import br.org.sus.ms_medicamentos.application.usuario_medicamento.usecases.dto.UsuarioMedicamentoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario_medicamento")
@Tag(name = "Vínculo Usuário-Medicamento", description = "Endpoints para gerenciar os medicamentos associados aos usuários")
public class UsuarioMedicamentoController {

    private final CadastrarUsuarioMedicamentoPortIn useCase;

    public UsuarioMedicamentoController(CadastrarUsuarioMedicamentoPortIn useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Cadastra vínculo", description = "Associa um medicamento a um usuário e define se as notificações estão ativas.")
    @ApiResponse(responseCode = "201", description = "Vínculo criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    @PostMapping
    public ResponseEntity<UsuarioMedicamentoResponse> cadastrar(@RequestBody UsuarioMedicamentoRequest request) {

        var output = useCase.cadastrar(new UsuarioMedicamentoInput(
                request.usuarioId(),
                request.medicamentoId(),
                request.notificacaoAtiva()
        ));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UsuarioMedicamentoResponse(
                        output.usuarioMedicamentoId(),
                        output.notificacaoAtiva(),
                        output.dataCadastro()
                ));

    }
}
