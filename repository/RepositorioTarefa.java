package com.healthtrack.repository;

import com.healthtrack.model.Tarefa;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioTarefa extends RepositorioBaseJSON<Tarefa> {
    
    public RepositorioTarefa() {
        super("tarefas.json");
    }
    
    @Override
    protected Class<Tarefa> getClasseEntidade() {
        return Tarefa.class;
    }
    
    @Override
    protected Long getIdEntidade(Tarefa tarefa) {
        return tarefa.getId();
    }
    
    @Override
    protected void setIdEntidade(Tarefa tarefa, Long id) {
        tarefa.setId(id);
    }
    
    public List<Tarefa> buscarPorUsuarioId(Long usuarioId) {
        return buscarTodos().stream()
                .filter(tarefa -> usuarioId.equals(tarefa.getUsuarioId()))
                .toList();
    }
}