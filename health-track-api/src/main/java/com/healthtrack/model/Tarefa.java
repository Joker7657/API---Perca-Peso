package com.healthtrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tarefa {
    private Long id;
    private Long usuarioId;
    private String titulo;
    private String descricao;
    private String categoria;
    private String dificuldade;
    private Integer caloriasEstimadas;
    private Integer tempoMinutos;
    private LocalDateTime dataHora;
    private Boolean concluida = false;
    private LocalDateTime dataConclusao;
    private LocalDateTime dataCriacao;
    
    // Novos campos para recorrência
    private List<String> diasDaSemana = new ArrayList<>(); // ["SEGUNDA", "QUARTA", "SEXTA"]
    private String frequencia = "UNICA"; // "UNICA", "DIARIA", "SEMANAL", "MENSAL"
    private Map<String, Boolean> conclusoesPorData = new HashMap<>(); // "2025-12-04" -> true
    
    public Tarefa() {
        this.dataCriacao = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getDificuldade() { return dificuldade; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }
    
    public Integer getCaloriasEstimadas() { return caloriasEstimadas; }
    public void setCaloriasEstimadas(Integer caloriasEstimadas) { this.caloriasEstimadas = caloriasEstimadas; }
    
    public Integer getTempoMinutos() { return tempoMinutos; }
    public void setTempoMinutos(Integer tempoMinutos) { this.tempoMinutos = tempoMinutos; }
    
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    
    public Boolean getConcluida() { return concluida; }
    public void setConcluida(Boolean concluida) { 
        this.concluida = concluida;
        if (concluida && this.dataConclusao == null) {
            this.dataConclusao = LocalDateTime.now();
        }
    }
    
    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public List<String> getDiasDaSemana() { return diasDaSemana; }
    public void setDiasDaSemana(List<String> diasDaSemana) { this.diasDaSemana = diasDaSemana; }
    
    public String getFrequencia() { return frequencia; }
    public void setFrequencia(String frequencia) { this.frequencia = frequencia; }
    
    public Map<String, Boolean> getConclusoesPorData() { return conclusoesPorData; }
    public void setConclusoesPorData(Map<String, Boolean> conclusoesPorData) { this.conclusoesPorData = conclusoesPorData; }
    
    // Métodos auxiliares
    @JsonIgnore
    public boolean isConcluidaHoje() {
        String hoje = LocalDate.now().toString();
        return conclusoesPorData.getOrDefault(hoje, false);
    }
    
    @JsonIgnore
    public void marcarConcluidaHoje() {
        String hoje = LocalDate.now().toString();
        conclusoesPorData.put(hoje, true);
    }
    
    @JsonIgnore
    public void desmarcarConcluidaHoje() {
        String hoje = LocalDate.now().toString();
        conclusoesPorData.remove(hoje);
    }
}