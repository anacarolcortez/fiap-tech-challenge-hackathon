package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.out;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.out.AtualizarEstoqueMedicamentoPortOut;
import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.repository.JpaMedicamentoDistribuidorRepository;
import br.org.sus.ms_medicamentos.infra.mapper.MedicamentoDistribuidorMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AtualizarEstoqueMedicamentoAdapter implements AtualizarEstoqueMedicamentoPortOut {

    private final JpaMedicamentoDistribuidorRepository repository;

    public AtualizarEstoqueMedicamentoAdapter(JpaMedicamentoDistribuidorRepository repository) {
        this.repository = repository;
    }

    @Override
    public MedicamentoDistribuidor adicionarQuantidade(UUID uuidMedicamentoDistribuidor, int qtdAdicionada) {
        var md = repository.addStock(uuidMedicamentoDistribuidor, qtdAdicionada);
        return MedicamentoDistribuidorMapper.toDomain(md);
    }
}
