package com.healthtrack.repository;

import com.healthtrack.model.Tarefa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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
            .collect(Collectors.toList());
    }
}