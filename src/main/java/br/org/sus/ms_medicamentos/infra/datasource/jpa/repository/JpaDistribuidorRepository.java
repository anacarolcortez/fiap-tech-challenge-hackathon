package br.org.sus.ms_medicamentos.infra.datasource.jpa.repository;

import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaDistribuidorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDistribuidorRepository extends JpaRepository<JpaDistribuidorEntity, UUID> {
}
