package com.healthtrack.repository;

import com.healthtrack.model.RegistroDiario;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Repository
public class RepositorioRegistroDiario extends RepositorioBaseJSON<RegistroDiario> {
    
    public RepositorioRegistroDiario() {
        super("registros_diarios.json");
    }
    
    @Override
    protected Class<RegistroDiario> getClasseEntidade() {
        return RegistroDiario.class;
    }
    
    @Override
    protected Long getIdEntidade(RegistroDiario registro) {
        return registro.getId();
    }
    
    @Override
    protected void setIdEntidade(RegistroDiario registro, Long id) {
        registro.setId(id);
    }
    
    public List<RegistroDiario> buscarPorUsuarioId(Long usuarioId) {
        return buscarTodos().stream()
            .filter(registro -> usuarioId.equals(registro.getUsuarioId()))
            .collect(Collectors.toList());
    }
    
    public Optional<RegistroDiario> buscarPorUsuarioIdEData(Long usuarioId, LocalDate data) {
        return buscarTodos().stream()
                .filter(registro -> usuarioId.equals(registro.getUsuarioId()) && data.equals(registro.getData()))
                .findFirst();
    }
}