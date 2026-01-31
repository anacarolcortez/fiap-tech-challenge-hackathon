package br.org.sus.ms_medicamentos.infra.mapper;

import br.org.sus.ms_medicamentos.domain.Usuario;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaUsuarioEntity;

public class UsuarioMapper {

    private UsuarioMapper(){}

    public static Usuario toDomain(JpaUsuarioEntity entity){
        if(entity == null){
            return null;
        }

        return new Usuario(
                entity.getUuid(),
                entity.getCPF(),
                entity.getSenha(),
                entity.getTipo()
        );
    }

    public static JpaUsuarioEntity toEntity(Usuario domain){
        if(domain == null){
            return null;
        }

        return new JpaUsuarioEntity(
                domain.getUuid(),
                domain.getCPF(),
                domain.getSenha(),
                domain.getTipo()
        );
    }
}
