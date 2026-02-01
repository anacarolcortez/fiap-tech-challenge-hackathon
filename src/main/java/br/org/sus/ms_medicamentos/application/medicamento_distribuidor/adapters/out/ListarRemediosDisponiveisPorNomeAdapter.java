package br.org.sus.ms_medicamentos.application.medicamento_distribuidor.adapters.out;

import br.org.sus.ms_medicamentos.application.medicamento_distribuidor.ports.out.ListarRemediosDisponiveisPorNomePortOut;
import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoDistribuidorEntity;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.repository.JpaMedicamentoDistribuidorRepository;
import br.org.sus.ms_medicamentos.infra.mapper.MedicamentoDistribuidorMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class ListarRemediosDisponiveisPorNomeAdapter implements ListarRemediosDisponiveisPorNomePortOut {

    private final JpaMedicamentoDistribuidorRepository repository;

    public ListarRemediosDisponiveisPorNomeAdapter(JpaMedicamentoDistribuidorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<MedicamentoDistribuidor> listar(String nomeMedicamento, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<JpaMedicamentoDistribuidorEntity> pageEntity =
                repository.findByNomeRemedio(nomeMedicamento, pageable);

        return pageEntity.map(MedicamentoDistribuidorMapper::toDomain);
    }
}
