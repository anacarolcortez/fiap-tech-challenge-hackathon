package br.org.sus.ms_medicamentos.domain;

import java.time.LocalDate;
import java.util.UUID;

public class Medicamento {
    private UUID uuid;
    private String nome;
    private String lote;
    private LocalDate validade;

    public Medicamento(UUID uuid, String nome, String lote, LocalDate validade) {
        this.uuid = uuid;
        this.nome = nome;
        this.lote = lote;
        this.validade = validade;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }

    public String getLote() {
        return lote;
    }

    public LocalDate getValidade() {
        return validade;
    }
}
