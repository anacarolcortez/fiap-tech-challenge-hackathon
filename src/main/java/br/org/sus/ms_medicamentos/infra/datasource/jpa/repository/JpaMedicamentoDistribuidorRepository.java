package br.org.sus.ms_medicamentos.infra.datasource.jpa.repository;

import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoDistribuidorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMedicamentoDistribuidorRepository extends JpaRepository<JpaMedicamentoDistribuidorEntity, UUID> {
}
