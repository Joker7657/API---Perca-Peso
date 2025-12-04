package com.healthtrack.controller;

import com.healthtrack.model.Tarefa;
import com.healthtrack.service.ServicoTarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class ControladorTarefa {
    
    @Autowired
    private ServicoTarefa servicoTarefa;
    
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        try {
            Tarefa tarefaCriada = servicoTarefa.criarTarefa(tarefa);
            return ResponseEntity.ok(tarefaCriada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public List<Tarefa> listarTodasTarefas() {
        return servicoTarefa.buscarTodosTarefas();
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Tarefa> listarTarefasDoUsuario(@PathVariable Long usuarioId) {
        return servicoTarefa.buscarTarefasPorUsuario(usuarioId);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefa(@PathVariable Long id) {
        Optional<Tarefa> tarefa = servicoTarefa.buscarTarefaPorId(id);
        return tarefa.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        try {
            Tarefa tarefaAtualizada = servicoTarefa.atualizarTarefa(id, tarefa);
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/concluir")
    public ResponseEntity<Tarefa> concluirTarefa(@PathVariable Long id) {
        try {
            Tarefa tarefa = servicoTarefa.concluirTarefa(id);
            return ResponseEntity.ok(tarefa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/concluir-hoje")
    public ResponseEntity<Tarefa> concluirTarefaHoje(@PathVariable Long id) {
        try {
            Tarefa tarefa = servicoTarefa.concluirTarefaHoje(id);
            return ResponseEntity.ok(tarefa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/desconcluir-hoje")
    public ResponseEntity<Tarefa> desconcluirTarefaHoje(@PathVariable Long id) {
        try {
            Tarefa tarefa = servicoTarefa.desconcluirTarefaHoje(id);
            return ResponseEntity.ok(tarefa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/usuario/{usuarioId}/hoje")
    public List<Tarefa> listarTarefasDoUsuarioHoje(@PathVariable Long usuarioId) {
        return servicoTarefa.buscarTarefasDoUsuarioParaHoje(usuarioId);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerTarefa(@PathVariable Long id) {
        try {
            servicoTarefa.removerTarefa(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}