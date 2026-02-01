package br.org.sus.ms_medicamentos.infra.mapper;

import br.org.sus.ms_medicamentos.domain.DistribuidorSUS;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaDistribuidorEntity;

public class DistribuidorMapper {

    public static DistribuidorSUS toDomain(JpaDistribuidorEntity entity){
        return new DistribuidorSUS(
                entity.getUuid(),
                entity.getNome(),
                entity.getCNPJ(),
                entity.getTelefone(),
                entity.getHorarioFuncionamento(),
                entity.getTipo(),
                entity.getLogradouro(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstado(),
                entity.getCep()
        );
    }

    public static JpaDistribuidorEntity toEntity(DistribuidorSUS domain) {
        return new JpaDistribuidorEntity(
                domain.getUuid(),
                domain.getNome(),
                domain.getCNPJ(),
                domain.getTelefone(),
                domain.getHorarioFuncionamento(),
                domain.getTipo(),
                domain.getLogradouro(),
                domain.getBairro(),
                domain.getCidade(),
                domain.getEstado(),
                domain.getCep()
        );
    }
}
