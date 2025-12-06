package com.healthtrack.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaRecompensas {
    private Integer pontos = 0;
    private Integer experiencia = 0;
    private Integer nivel = 1;
    private Integer diasConsecutivos = 0;
    private Integer recordeStreak = 0;
    private Integer totalRegistros = 0;
    private Integer tarefasCompletas = 0;
    private Double totalPesoPermitido = 0.0;
    private LocalDate ultimoRegistro;
    private List<Conquista> conquistas = new ArrayList<>();
    private Map<String, Integer> historicoStreaks = new HashMap<>();
    
    // Classe interna para Conquistas
    public static class Conquista {
        private String id;
        private String nome;
        private String descricao;
        private String icone;
        private Integer progresso;
        private Integer meta;
        private LocalDate dataDesbloqueio;
        private Boolean desbloqueada = false;
        
        public Conquista() {}
        
        public Conquista(String id, String nome, String descricao, String icone, Integer meta) {
            this.id = id;
            this.nome = nome;
            this.descricao = descricao;
            this.icone = icone;
            this.meta = meta;
            this.progresso = 0;
        }
        
        // Getters e Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        
        public String getIcone() { return icone; }
        public void setIcone(String icone) { this.icone = icone; }
        
        public Integer getProgresso() { return progresso; }
        public void setProgresso(Integer progresso) { this.progresso = progresso; }
        
        public Integer getMeta() { return meta; }
        public void setMeta(Integer meta) { this.meta = meta; }
        
        public LocalDate getDataDesbloqueio() { return dataDesbloqueio; }
        public void setDataDesbloqueio(LocalDate dataDesbloqueio) { this.dataDesbloqueio = dataDesbloqueio; }
        
        public Boolean getDesbloqueada() { return desbloqueada; }
        public void setDesbloqueada(Boolean desbloqueada) { this.desbloqueada = desbloqueada; }
    }
    
    // Getters e Setters
    public Integer getPontos() { return pontos; }
    public void setPontos(Integer pontos) { 
        this.pontos = pontos;
        // XP e pontos são a mesma coisa - sincronizar sempre
        this.experiencia = this.pontos;
        atualizarNivel();
    }
    
    public Integer getExperiencia() { return experiencia; }
    public void setExperiencia(Integer experiencia) { 
        this.experiencia = experiencia;
        atualizarNivel();
    }
    
    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }
    
    public Integer getDiasConsecutivos() { return diasConsecutivos; }
    public void setDiasConsecutivos(Integer diasConsecutivos) { 
        this.diasConsecutivos = diasConsecutivos;
        if (diasConsecutivos > recordeStreak) {
            this.recordeStreak = diasConsecutivos;
        }
    }
    
    public Integer getRecordeStreak() { return recordeStreak; }
    public void setRecordeStreak(Integer recordeStreak) { this.recordeStreak = recordeStreak; }
    
    public Integer getTotalRegistros() { return totalRegistros; }
    public void setTotalRegistros(Integer totalRegistros) { this.totalRegistros = totalRegistros; }
    
    public Integer getTarefasCompletas() { return tarefasCompletas; }
    public void setTarefasCompletas(Integer tarefasCompletas) { this.tarefasCompletas = tarefasCompletas; }
    
    public Double getTotalPesoPermitido() { return totalPesoPermitido; }
    public void setTotalPesoPermitido(Double totalPesoPermitido) { this.totalPesoPermitido = totalPesoPermitido; }
    
    public LocalDate getUltimoRegistro() { return ultimoRegistro; }
    public void setUltimoRegistro(LocalDate ultimoRegistro) { this.ultimoRegistro = ultimoRegistro; }
    
    public List<Conquista> getConquistas() { return conquistas; }
    public void setConquistas(List<Conquista> conquistas) { this.conquistas = conquistas; }
    
    public Map<String, Integer> getHistoricoStreaks() { return historicoStreaks; }
    public void setHistoricoStreaks(Map<String, Integer> historicoStreaks) { this.historicoStreaks = historicoStreaks; }
    
    // Métodos
    public void adicionarPontos(Integer pontos) {
        this.pontos += pontos;
        // XP e pontos são a mesma coisa
        this.experiencia = this.pontos;
        atualizarNivel();
    }
    
    public void adicionarExperiencia(Integer xp) {
        // XP e pontos são a mesma coisa
        adicionarPontos(xp);
    }
    
    private void atualizarNivel() {
        // Fórmula: nivel = raiz(experiencia / 100) + 1
        this.nivel = (int) Math.floor(Math.sqrt(experiencia / 100.0)) + 1;
    }
    
    public String getNivelTitulo() {
        if (nivel >= 20) return "Lenda 👑";
        if (nivel >= 15) return "Mestre 🏆";
        if (nivel >= 10) return "Dedicado 💪";
        if (nivel >= 5) return "Comprometido ⭐";
        return "Iniciante 🌱";
    }
    
    public Double getMultiplicadorStreak() {
        if (diasConsecutivos >= 30) return 3.0;
        if (diasConsecutivos >= 14) return 2.0;
        if (diasConsecutivos >= 7) return 1.5;
        return 1.0;
    }
    
    public void removerPontos(Integer pontos) {
        this.pontos = Math.max(0, this.pontos - pontos);
        // XP e pontos são a mesma coisa
        this.experiencia = this.pontos;
        atualizarNivel();
    }
    
    public void adicionarConquista(Conquista conquista) {
        boolean existe = conquistas.stream()
            .anyMatch(c -> c.getId().equals(conquista.getId()));
        if (!existe) {
            conquistas.add(conquista);
        }
    }
    
    public void atualizarProgressoConquista(String conquistaId, Integer progresso) {
        conquistas.stream()
            .filter(c -> c.getId().equals(conquistaId))
            .findFirst()
            .ifPresent(c -> {
                c.setProgresso(Math.min(progresso, c.getMeta()));
                if (c.getProgresso() >= c.getMeta() && !c.getDesbloqueada()) {
                    c.setDesbloqueada(true);
                    c.setDataDesbloqueio(LocalDate.now());
                }
            });
    }
    
    public void inicializarConquistas() {
        if (conquistas.isEmpty()) {
            conquistas.add(new Conquista("primeira_vitoria", "Primeira Vitória", "Faça seu primeiro registro de peso", "🏅", 1));
            conquistas.add(new Conquista("dedicado", "Dedicado", "Registre por 7 dias consecutivos", "⭐", 7));
            conquistas.add(new Conquista("imparavel", "Imparável", "Registre por 30 dias consecutivos", "🔥", 30));
            conquistas.add(new Conquista("transformacao", "Transformação", "Perca 5kg", "💪", 5));
            conquistas.add(new Conquista("meta_alcancada", "Meta Alcançada", "Atinja seu peso meta", "🎯", 1));
            conquistas.add(new Conquista("lenda", "Lenda", "Registre por 100 dias consecutivos", "👑", 100));
            conquistas.add(new Conquista("consistente", "Consistente", "Faça 50 registros totais", "🌟", 50));
            conquistas.add(new Conquista("relampago", "Relâmpago", "Perca 10kg no total", "⚡", 10));
            conquistas.add(new Conquista("forte", "Forte", "Complete 20 tarefas", "🦾", 20));
            conquistas.add(new Conquista("campeao", "Campeão", "Atinja 1000 pontos", "🎊", 1000));
        }
    }
}