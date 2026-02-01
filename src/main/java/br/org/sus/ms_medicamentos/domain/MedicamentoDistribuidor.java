package br.org.sus.ms_medicamentos.domain;

import java.time.LocalDate;
import java.util.UUID;

public class MedicamentoDistribuidor {
    private UUID uuid;
    private Medicamento medicamento;
    private DistribuidorSUS distribuidor;
    private int quantidade;
    private LocalDate ultimaAtualizacao;

    public MedicamentoDistribuidor(UUID uuid,
                                   Medicamento medicamento,
                                   DistribuidorSUS distribuidor,
                                   int quantidade) {
        this.uuid = uuid;
        this.medicamento = medicamento;
        this.distribuidor = distribuidor;
        this.quantidade = quantidade;
        this.ultimaAtualizacao = LocalDate.now();
    }

    public UUID getUuid() {
        return uuid;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public DistribuidorSUS getDistribuidor() {
        return distribuidor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public LocalDate getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }
}
