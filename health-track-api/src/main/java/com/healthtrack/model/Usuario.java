package com.healthtrack.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String genero;
    private Double altura;
    private Double pesoInicial;
    private Double pesoAtual;
    private Double pesoMeta;
    private Double imc;
    private Double tmb;
    private LocalDateTime dataCriacao;
    
    private List<Tarefa> tarefas = new ArrayList<>();
    private List<RegistroDiario> registrosDiarios = new ArrayList<>();
    private SistemaRecompensas sistemaRecompensas = new SistemaRecompensas();
    
    public Usuario() {
        this.dataCriacao = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }
    
    public Double getPesoInicial() { return pesoInicial; }
    public void setPesoInicial(Double pesoInicial) { this.pesoInicial = pesoInicial; }
    
    public Double getPesoAtual() { return pesoAtual; }
    public void setPesoAtual(Double pesoAtual) { this.pesoAtual = pesoAtual; }
    
    public Double getPesoMeta() { return pesoMeta; }
    public void setPesoMeta(Double pesoMeta) { this.pesoMeta = pesoMeta; }
    
    public Double getImc() { return imc; }
    public void setImc(Double imc) { this.imc = imc; }
    
    public Double getTmb() { return tmb; }
    public void setTmb(Double tmb) { this.tmb = tmb; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public List<Tarefa> getTarefas() { return tarefas; }
    public void setTarefas(List<Tarefa> tarefas) { this.tarefas = tarefas; }
    
    public List<RegistroDiario> getRegistrosDiarios() { return registrosDiarios; }
    public void setRegistrosDiarios(List<RegistroDiario> registrosDiarios) { this.registrosDiarios = registrosDiarios; }
    
    public SistemaRecompensas getSistemaRecompensas() { return sistemaRecompensas; }
    public void setSistemaRecompensas(SistemaRecompensas sistemaRecompensas) { this.sistemaRecompensas = sistemaRecompensas; }
}