package com.healthtrack.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistroDiario {
    private Long id;
    private Long usuarioId;
    private LocalDate data;
    private Double peso;
    private Double cintura;
    private Double quadril;
    private Double peito;
    private String humor;
    private String nivelEnergia;
    private String anotacoes;
    private LocalDateTime dataCriacao;
    
    public RegistroDiario() {
        this.data = LocalDate.now();
        this.dataCriacao = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    
    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
    
    public Double getCintura() { return cintura; }
    public void setCintura(Double cintura) { this.cintura = cintura; }
    
    public Double getQuadril() { return quadril; }
    public void setQuadril(Double quadril) { this.quadril = quadril; }
    
    public Double getPeito() { return peito; }
    public void setPeito(Double peito) { this.peito = peito; }
    
    public String getHumor() { return humor; }
    public void setHumor(String humor) { this.humor = humor; }
    
    public String getNivelEnergia() { return nivelEnergia; }
    public void setNivelEnergia(String nivelEnergia) { this.nivelEnergia = nivelEnergia; }
    
    public String getAnotacoes() { return anotacoes; }
    public void setAnotacoes(String anotacoes) { this.anotacoes = anotacoes; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}