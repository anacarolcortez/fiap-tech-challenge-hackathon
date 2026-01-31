package br.org.sus.ms_medicamentos.domain;

import java.util.UUID;

public class Usuario {
    private UUID uuid;
    private String CPF;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(UUID uuid, String CPF, String senha, TipoUsuario tipo) {
        this.uuid = uuid;
        this.CPF = CPF;
        this.senha = senha;
        this.tipo = tipo;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCPF() {
        return CPF;
    }

    public String getSenha() {
        return senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }
}
