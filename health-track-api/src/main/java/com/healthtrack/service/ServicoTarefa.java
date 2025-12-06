package com.healthtrack.service;

import com.healthtrack.model.Tarefa;
import com.healthtrack.model.Usuario;
import com.healthtrack.repository.RepositorioTarefa;
import com.healthtrack.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicoTarefa {
    
    @Autowired
    private RepositorioTarefa repositorioTarefa;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    
    public Tarefa criarTarefa(Tarefa tarefa) {
        if (!repositorioUsuario.existePorId(tarefa.getUsuarioId())) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        return repositorioTarefa.salvar(tarefa);
    }
    
    public List<Tarefa> buscarTarefasPorUsuario(Long usuarioId) {
        return repositorioTarefa.buscarPorUsuarioId(usuarioId);
    }
    
    public Optional<Tarefa> buscarTarefaPorId(Long id) {
        return repositorioTarefa.buscarPorId(id);
    }
    
    public Tarefa atualizarTarefa(Long id, Tarefa novosDados) {
        Tarefa tarefaExistente = repositorioTarefa.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
        
        tarefaExistente.setTitulo(novosDados.getTitulo());
        tarefaExistente.setDescricao(novosDados.getDescricao());
        tarefaExistente.setCategoria(novosDados.getCategoria());
        tarefaExistente.setDificuldade(novosDados.getDificuldade());
        tarefaExistente.setCaloriasEstimadas(novosDados.getCaloriasEstimadas());
        tarefaExistente.setTempoMinutos(novosDados.getTempoMinutos());
        tarefaExistente.setDataHora(novosDados.getDataHora());
        
        return repositorioTarefa.salvar(tarefaExistente);
    }
    
    public Tarefa concluirTarefa(Long tarefaId) {
        Tarefa tarefa = repositorioTarefa.buscarPorId(tarefaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
        
        if (!tarefa.getConcluida()) {
            tarefa.setConcluida(true);
            
            Usuario usuario = repositorioUsuario.buscarPorId(tarefa.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            
            int pontos;
            switch (tarefa.getDificuldade()) {
                case "FACIL":
                    pontos = 10;
                    break;
                case "MEDIO":
                    pontos = 25;
                    break;
                case "DIFICIL":
                    pontos = 50;
                    break;
                default:
                    pontos = 10;
            }
            
            if (usuario.getSistemaRecompensas() == null) {
                usuario.setSistemaRecompensas(new com.healthtrack.model.SistemaRecompensas());
                usuario.getSistemaRecompensas().inicializarConquistas();
            }
            
            usuario.getSistemaRecompensas().adicionarPontos(pontos);
            usuario.getSistemaRecompensas().setTarefasCompletas(
                usuario.getSistemaRecompensas().getTarefasCompletas() + 1
            );
            
            // Atualizar conquista de tarefas
            usuario.getSistemaRecompensas().atualizarProgressoConquista(
                "forte", 
                usuario.getSistemaRecompensas().getTarefasCompletas()
            );
            
            repositorioTarefa.salvar(tarefa);
            repositorioUsuario.salvar(usuario);
        }
        
        return tarefa;
    }
    
    public Tarefa concluirTarefaHoje(Long tarefaId) {
        Tarefa tarefa = repositorioTarefa.buscarPorId(tarefaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
        
        if (!tarefa.isConcluidaHoje()) {
            tarefa.marcarConcluidaHoje();
            
            // Adicionar pontos ao usuário
            Usuario usuario = repositorioUsuario.buscarPorId(tarefa.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            
            int pontos;
            switch (tarefa.getDificuldade()) {
                case "FACIL":
                    pontos = 10;
                    break;
                case "MEDIO":
                    pontos = 25;
                    break;
                case "DIFICIL":
                    pontos = 50;
                    break;
                default:
                    pontos = 10;
            }
            
            if (usuario.getSistemaRecompensas() == null) {
                usuario.setSistemaRecompensas(new com.healthtrack.model.SistemaRecompensas());
                usuario.getSistemaRecompensas().inicializarConquistas();
            }
            
            usuario.getSistemaRecompensas().adicionarPontos(pontos);
            usuario.getSistemaRecompensas().setTarefasCompletas(
                usuario.getSistemaRecompensas().getTarefasCompletas() + 1
            );
            
            // Atualizar conquista de tarefas
            usuario.getSistemaRecompensas().atualizarProgressoConquista(
                "forte", 
                usuario.getSistemaRecompensas().getTarefasCompletas()
            );
            
            repositorioUsuario.salvar(usuario);
            repositorioTarefa.salvar(tarefa);
        }
        
        return tarefa;
    }
    
    public Tarefa desconcluirTarefaHoje(Long tarefaId) {
        Tarefa tarefa = repositorioTarefa.buscarPorId(tarefaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
        
        if (tarefa.isConcluidaHoje()) {
            tarefa.desmarcarConcluidaHoje();
            
            // Remover pontos do usuário
            Usuario usuario = repositorioUsuario.buscarPorId(tarefa.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            
            int pontos;
            switch (tarefa.getDificuldade()) {
                case "FACIL":
                    pontos = 10;
                    break;
                case "MEDIO":
                    pontos = 25;
                    break;
                case "DIFICIL":
                    pontos = 50;
                    break;
                default:
                    pontos = 10;
            }
            
            if (usuario.getSistemaRecompensas() != null) {
                usuario.getSistemaRecompensas().removerPontos(pontos);
                repositorioUsuario.salvar(usuario);
            }
            
            repositorioTarefa.salvar(tarefa);
        }
        
        return tarefa;
    }
    
    public List<Tarefa> buscarTarefasDoUsuarioParaHoje(Long usuarioId) {
        List<Tarefa> todasTarefas = repositorioTarefa.buscarPorUsuarioId(usuarioId);
        List<Tarefa> tarefasDeHoje = new ArrayList<>();
        
        String diaAtual = java.time.LocalDate.now().getDayOfWeek().toString();
        
        for (Tarefa tarefa : todasTarefas) {
            if (tarefa.getFrequencia().equals("DIARIA")) {
                tarefasDeHoje.add(tarefa);
            } else if (tarefa.getFrequencia().equals("SEMANAL") && 
                       tarefa.getDiasDaSemana() != null && 
                       tarefa.getDiasDaSemana().contains(diaAtual)) {
                tarefasDeHoje.add(tarefa);
            } else if (tarefa.getFrequencia().equals("UNICA") && !tarefa.getConcluida()) {
                tarefasDeHoje.add(tarefa);
            }
        }
        
        return tarefasDeHoje;
    }
    
    public void removerTarefa(Long id) {
        if (!repositorioTarefa.existePorId(id)) {
            throw new IllegalArgumentException("Tarefa não encontrada");
        }
        repositorioTarefa.removerPorId(id);
    }

    public List<Tarefa> buscarTodosTarefas() {
        return repositorioTarefa.buscarTodos();
    }
}