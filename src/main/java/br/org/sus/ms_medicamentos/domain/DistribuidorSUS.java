package br.org.sus.ms_medicamentos.domain;

import java.util.UUID;

public class DistribuidorSUS {
    private UUID uuid;
    private String nome;
    private String CNPJ;
    private TipoDistribuidor tipo;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public DistribuidorSUS(UUID uuid, String nome, String CNPJ, TipoDistribuidor tipo, String logradouro, String bairro, String cidade, String estado, String cep) {
        this.uuid = uuid;
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.tipo = tipo;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public TipoDistribuidor getTipo() {
        return tipo;
    }

    public void setTipo(TipoDistribuidor tipo) {
        this.tipo = tipo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
