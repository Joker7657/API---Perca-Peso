package com.healthtrack.model;

import java.util.ArrayList;
import java.util.List;

public class SistemaRecompensas {
    private Integer pontos = 0;
    private Integer diasConsecutivos = 0;
    private List<String> conquistas = new ArrayList<>();
    
    // Getters e Setters
    public Integer getPontos() { return pontos; }
    public void setPontos(Integer pontos) { this.pontos = pontos; }
    
    public Integer getDiasConsecutivos() { return diasConsecutivos; }
    public void setDiasConsecutivos(Integer diasConsecutivos) { this.diasConsecutivos = diasConsecutivos; }
    
    public List<String> getConquistas() { return conquistas; }
    public void setConquistas(List<String> conquistas) { this.conquistas = conquistas; }
    
    public void adicionarPontos(Integer pontos) {
        this.pontos += pontos;
    }
    
    public void adicionarConquista(String conquista) {
        if (!conquistas.contains(conquista)) {
            conquistas.add(conquista);
        }
    }
}