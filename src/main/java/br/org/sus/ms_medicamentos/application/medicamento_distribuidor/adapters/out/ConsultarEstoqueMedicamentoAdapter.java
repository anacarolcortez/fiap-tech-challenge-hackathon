package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.out;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.out.ConsultarEstoqueMedicamentoPortOut;
import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.repository.JpaMedicamentoDistribuidorRepository;
import br.org.sus.ms_medicamentos.infra.mapper.MedicamentoDistribuidorMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsultarEstoqueMedicamentoAdapter implements ConsultarEstoqueMedicamentoPortOut {

    private final JpaMedicamentoDistribuidorRepository repository;

    public ConsultarEstoqueMedicamentoAdapter(JpaMedicamentoDistribuidorRepository repository) {
        this.repository = repository;
    }

    @Override
    public MedicamentoDistribuidor consultar(UUID uuidMedicamentoDistribuidor) {
        var entity = repository.findByUuid(uuidMedicamentoDistribuidor);
        if (entity == null) {
            return null;
        }
        return MedicamentoDistribuidorMapper.toDomain(entity);
    }
}
