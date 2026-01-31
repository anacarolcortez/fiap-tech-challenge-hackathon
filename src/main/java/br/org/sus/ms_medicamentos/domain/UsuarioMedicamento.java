package br.org.sus.ms_medicamentos.domain;

import java.time.LocalDate;
import java.util.UUID;

public class UsuarioMedicamento {
    private UUID uuid;
    private Usuario usuario;
    private Medicamento medicamento;
    private boolean notificacaoAtiva;
    private LocalDate dataCadastro;

    public UsuarioMedicamento(UUID uuid, Usuario usuario, Medicamento medicamento, boolean notificacaoAtiva, LocalDate dataCadastro) {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
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
