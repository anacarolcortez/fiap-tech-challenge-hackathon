package br.org.sus.ms_medicamentos.infra.datasource.jpa.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "medicamento")
public class JpaMedicamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String lote;

    @Column(nullable = false)
    private LocalDate validade;

    public JpaMedicamentoEntity() {
    }

    public JpaMedicamentoEntity(UUID uuid, String nome, String lote, LocalDate validade) {
        this.uuid = uuid;
        this.nome = nome;
        this.lote = lote;
        this.validade = validade;
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

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JpaMedicamentoEntity that = (JpaMedicamentoEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(nome, that.nome) && Objects.equals(lote, that.lote) && Objects.equals(validade, that.validade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, nome, lote, validade);
    }
}
