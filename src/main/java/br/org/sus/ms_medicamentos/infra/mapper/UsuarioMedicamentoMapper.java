package br.org.sus.ms_medicamentos.infra.mapper;

import br.org.sus.ms_medicamentos.domain.UsuarioMedicamento;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaUsuarioMedicamentoEntity;

public class UsuarioMedicamentoMapper {
    public static UsuarioMedicamento toDomain(JpaUsuarioMedicamentoEntity entity){
        return new UsuarioMedicamento(
                entity.getUuid(),
                UsuarioMapper.toDomain(entity.getUsuario()),
                MedicamentoMapper.toDomain(entity.getMedicamento()),
                entity.isNotificacaoAtiva(),
                entity.getDataCadastro()
        );
    }

    public static JpaUsuarioMedicamentoEntity toEntity(UsuarioMedicamento domain){
        JpaUsuarioMedicamentoEntity entity = new JpaUsuarioMedicamentoEntity();

        entity.setUuid(domain.getUuid());
        entity.setUsuario(UsuarioMapper.toEntity(domain.getUsuario()));
        entity.setMedicamento(MedicamentoMapper.toEntity(domain.getMedicamento()));
        entity.setNotificacaoAtiva(domain.isNotificacaoAtiva());
        entity.setDataCadastro(domain.getDataCadastro());
        return entity;
    }
}
