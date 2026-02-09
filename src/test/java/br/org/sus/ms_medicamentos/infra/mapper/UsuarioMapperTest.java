package br.org.sus.ms_medicamentos.infra.mapper;

import br.org.sus.ms_medicamentos.domain.TipoUsuario;
import br.org.sus.ms_medicamentos.domain.Usuario;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaUsuarioEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    @Test
    void deveMapearParaDomainQuandoEntityValida() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String cpf = "123.456.789-00";
        String telefone = "(11) 99999-9999";
        String email = "usuario@example.com";
        String senha = "senha123";
        TipoUsuario tipo = TipoUsuario.PACIENTE;
        JpaUsuarioEntity entity = new JpaUsuarioEntity(uuid, cpf, telefone, email, senha, tipo);

        // Act
        Usuario result = UsuarioMapper.toDomain(entity);

        // Assert
        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
        assertEquals(cpf, result.getCPF());
        assertEquals(telefone, result.getTelefone());
        assertEquals(email, result.getEmail());
        assertEquals(senha, result.getSenha());
        assertEquals(tipo, result.getTipo());
    }

    @Test
    void deveRetornarNullQuandoEntityNula() {
        // Act
        Usuario result = UsuarioMapper.toDomain(null);

        // Assert
        assertNull(result);
    }

    @Test
    void deveMapearParaEntityQuandoDomainValido() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String cpf = "987.654.321-00";
        String telefone = "(21) 88888-8888";
        String email = "admin@example.com";
        String senha = "adminpass";
        TipoUsuario tipo = TipoUsuario.ADMIN;
        Usuario domain = new Usuario(uuid, cpf, telefone, email, senha, tipo);

        // Act
        JpaUsuarioEntity result = UsuarioMapper.toEntity(domain);

        // Assert
        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
        assertEquals(cpf, result.getCPF());
        assertEquals(telefone, result.getTelefone());
        assertEquals(email, result.getEmail());
        assertEquals(senha, result.getSenha());
        assertEquals(tipo, result.getTipo());
    }

    @Test
    void deveRetornarNullQuandoDomainNulo() {
        // Act
        JpaUsuarioEntity result = UsuarioMapper.toEntity(null);

        // Assert
        assertNull(result);
    }
}