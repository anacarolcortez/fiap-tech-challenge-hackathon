package br.org.sus.ms_medicamentos.infra.mapper;

import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaMedicamentoEntity;

public class MedicamentoMapper {
    public static Medicamento toDomain(JpaMedicamentoEntity entity){
        return new Medicamento(
                entity.getUuid(),
                entity.getNome(),
                entity.getLote(),
                entity.getValidade()
        );
    }

    public static JpaMedicamentoEntity toEntity(Medicamento domain){
        JpaMedicamentoEntity entity = new JpaMedicamentoEntity();
        entity.setUuid(domain.getUuid());
        entity.setNome(domain.getNome());
        entity.setLote(domain.getLote());
        entity.setValidade(domain.getValidade());
        return entity;
    }
}
