package br.org.sus.ms_medicamentos.infra.datasource.jpa.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "medicamento_distribuidor",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"medicamento_id", "distribuidor_id"})
        })
public class JpaMedicamentoDistribuidorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicamento_id", nullable = false)
    private JpaMedicamentoEntity medicamento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "distribuidor_id", nullable = false)
    private JpaDistribuidorEntity distribuidor;

    @Column(nullable = false)
    private int quantidade;

    @Column(name = "ultima_atualizacao", nullable = false)
    private LocalDate ultimaAtualizacao;

    public JpaMedicamentoDistribuidorEntity() {
    }

    public JpaMedicamentoDistribuidorEntity(UUID uuid, JpaMedicamentoEntity medicamento,
                                            JpaDistribuidorEntity distribuidor, int quantidade,
                                            LocalDate ultimaAtualizacao) {
        this.uuid = uuid;
        this.medicamento = medicamento;
        this.distribuidor = distribuidor;
        this.quantidade = quantidade;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public JpaMedicamentoEntity getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(JpaMedicamentoEntity medicamento) {
        this.medicamento = medicamento;
    }

    public JpaDistribuidorEntity getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(JpaDistribuidorEntity distribuidor) {
        this.distribuidor = distribuidor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDate ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }
}
