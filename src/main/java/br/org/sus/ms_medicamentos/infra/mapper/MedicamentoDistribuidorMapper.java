package br.org.sus.ms_medicamentos.infra.mapper;

import br.org.sus.ms_medicamentos.domain.MedicamentoDistribuidor;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoDistribuidorEntity;

public class MedicamentoDistribuidorMapper {
    public static MedicamentoDistribuidor toDomain(JpaMedicamentoDistribuidorEntity entity){
        return new MedicamentoDistribuidor(
                entity.getUuid(),
                MedicamentoMapper.toDomain(entity.getMedicamento()),
                DistribuidorMapper.toDomain(entity.getDistribuidor()),
                entity.getQuantidade()
        );
    }

    public static JpaMedicamentoDistribuidorEntity toEntity(MedicamentoDistribuidor domain){
        return new JpaMedicamentoDistribuidorEntity(
                domain.getUuid(),
                MedicamentoMapper.toEntity(domain.getMedicamento()),
                DistribuidorMapper.toEntity(domain.getDistribuidor()),
                domain.getQuantidade(),
                domain.getUltimaAtualizacao()
        );
    }
}
