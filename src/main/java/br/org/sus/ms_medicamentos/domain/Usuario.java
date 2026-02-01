package br.org.sus.ms_medicamentos.domain;

import java.util.UUID;

public class Usuario {
    private UUID uuid;
    private String CPF;
    private String telefone;
    private String email;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(UUID uuid, String CPF, String telefone,
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
}
