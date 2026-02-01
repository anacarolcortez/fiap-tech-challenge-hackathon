package br.org.sus.ms_medicamentos.infra.datasource.jpa.entity;

import br.org.sus.ms_medicamentos.domain.TipoUsuario;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class JpaUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String CPF;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    public JpaUsuarioEntity() {
    }

    public JpaUsuarioEntity(UUID uuid, String CPF, String telefone,
                            String email, String senha, TipoUsuario tipo) {
        this.uuid = uuid;
        this.CPF = CPF;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JpaUsuarioEntity that = (JpaUsuarioEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(CPF, that.CPF) && Objects.equals(telefone, that.telefone) && Objects.equals(email, that.email) && Objects.equals(senha, that.senha) && tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, CPF, telefone, email, senha, tipo);
    }
}
