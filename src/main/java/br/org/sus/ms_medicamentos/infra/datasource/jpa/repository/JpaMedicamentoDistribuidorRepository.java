package br.org.sus.ms_medicamentos.infra.datasource.jpa.repository;

import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoDistribuidorEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface JpaMedicamentoDistribuidorRepository extends JpaRepository<JpaMedicamentoDistribuidorEntity, UUID> {

    JpaMedicamentoDistribuidorEntity findByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("""
        UPDATE JpaMedicamentoDistribuidorEntity md
           SET md.quantidade = md.quantidade + :addAmount
         WHERE md.uuid = :uuidMedicamentoDistribuidor
    """)
    JpaMedicamentoDistribuidorEntity addStock(@Param("uuidMedicamentoDistribuidor") UUID uuidMedicamentoDistribuidor,
                 @Param("addAmount") int addAmount);

    @Query("""
        SELECT md
          FROM JpaMedicamentoDistribuidorEntity md
          JOIN md.medicamento m
         WHERE LOWER(m.nome) LIKE LOWER(CONCAT('%', :nomeRemedio, '%'))
    """)
    Page<JpaMedicamentoDistribuidorEntity> findByNomeRemedio(
            @Param("nomeRemedio") String nomeRemedio,
            Pageable pageable
    );
}
