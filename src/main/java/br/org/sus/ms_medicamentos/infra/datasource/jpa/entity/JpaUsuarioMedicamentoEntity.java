package br.org.sus.ms_medicamentos.infra.datasource.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "usuario_medicamento",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"usuario_id", "medicamento_id"})
        })
public class JpaUsuarioMedicamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private JpaUsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicamento_id", nullable = false)
    private JpaMedicamentoEntity medicamento;

    @Column(name = "notificacao_ativa", nullable = false)
    private boolean notificacaoAtiva;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    public JpaUsuarioMedicamentoEntity() {
    }

    public JpaUsuarioMedicamentoEntity(UUID uuid, JpaUsuarioEntity usuario,
                                       JpaMedicamentoEntity medicamento,
                                       boolean notificacaoAtiva, LocalDate dataCadastro) {
        this.uuid = uuid;
        this.usuario = usuario;
        this.medicamento = medicamento;
        this.notificacaoAtiva = notificacaoAtiva;
        this.dataCadastro = dataCadastro;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public JpaUsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(JpaUsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public JpaMedicamentoEntity getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(JpaMedicamentoEntity medicamento) {
        this.medicamento = medicamento;
    }

    public boolean isNotificacaoAtiva() {
        return notificacaoAtiva;
    }

    public void setNotificacaoAtiva(boolean notificacaoAtiva) {
        this.notificacaoAtiva = notificacaoAtiva;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
