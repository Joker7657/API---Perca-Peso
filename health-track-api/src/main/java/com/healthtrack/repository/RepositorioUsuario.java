package com.healthtrack.repository;

import com.healthtrack.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RepositorioUsuario extends RepositorioBaseJSON<Usuario> {
    
    public RepositorioUsuario() {
        super("usuarios.json");
    }
    
    @Override
    protected Class<Usuario> getClasseEntidade() {
        return Usuario.class;
    }
    
    @Override
    protected Long getIdEntidade(Usuario usuario) {
        return usuario.getId();
    }
    
    @Override
    protected void setIdEntidade(Usuario usuario, Long id) {
        usuario.setId(id);
    }
    
    public Optional<Usuario> buscarPorEmail(String email) {
        return buscarTodos().stream()
                .filter(usuario -> email.equals(usuario.getEmail()))
                .findFirst();
    }
}