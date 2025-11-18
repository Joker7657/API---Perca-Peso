package com.healthtrack.service;

import com.healthtrack.model.Tarefa;
import com.healthtrack.model.Usuario;
import com.healthtrack.repository.RepositorioTarefa;
import com.healthtrack.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            
            int pontos = switch (tarefa.getDificuldade()) {
                case "FACIL" -> 10;
                case "MEDIO" -> 25;
                case "DIFICIL" -> 50;
                default -> 10;
            };
            
            usuario.getSistemaRecompensas().adicionarPontos(pontos);
            
            repositorioTarefa.salvar(tarefa);
            repositorioUsuario.salvar(usuario);
        }
        
        return tarefa;
    }
    
    public void removerTarefa(Long id) {
        if (!repositorioTarefa.existePorId(id)) {
            throw new IllegalArgumentException("Tarefa não encontrada");
        }
        repositorioTarefa.removerPorId(id);
    }
}