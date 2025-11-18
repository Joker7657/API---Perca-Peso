package com.healthtrack.model;

import java.time.LocalDateTime;

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
}