package br.org.sus.ms_medicamentos.infra.datasource.jpa.entity;

import br.org.sus.ms_medicamentos.domain.TipoDistribuidor;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "distribuidor")
public class JpaDistribuidorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String CNPJ;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String horarioFuncionamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDistribuidor tipo;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String estado;

    @Column(nullable = false)
    private String cep;

    public JpaDistribuidorEntity() {
    }

    public JpaDistribuidorEntity(UUID uuid, String nome, String CNPJ,
                                 String telefone, String horarioFuncionamento,
                                 TipoDistribuidor tipo, String logradouro, String bairro,
                                 String cidade, String estado, String cep) {
        this.uuid = uuid;
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.telefone = telefone;
        this.horarioFuncionamento = horarioFuncionamento;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JpaDistribuidorEntity that = (JpaDistribuidorEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(nome, that.nome) && Objects.equals(CNPJ, that.CNPJ) && Objects.equals(telefone, that.telefone) && Objects.equals(horarioFuncionamento, that.horarioFuncionamento) && tipo == that.tipo && Objects.equals(logradouro, that.logradouro) && Objects.equals(bairro, that.bairro) && Objects.equals(cidade, that.cidade) && Objects.equals(estado, that.estado) && Objects.equals(cep, that.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, nome, CNPJ, telefone, horarioFuncionamento, tipo, logradouro, bairro, cidade, estado, cep);
    }
}
