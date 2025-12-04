package com.healthtrack.service;

import com.healthtrack.model.Usuario;
import com.healthtrack.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoUsuario {
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    
    @Autowired
    private CalculadoraSaude calculadoraSaude;
    
    public Usuario criarUsuario(Usuario usuario) {
        if (repositorioUsuario.buscarPorEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        
        calcularMetricasSaude(usuario);
        return repositorioUsuario.salvar(usuario);
    }
    
    public List<Usuario> buscarTodosUsuarios() {
        return repositorioUsuario.buscarTodos();
    }
    
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return repositorioUsuario.buscarPorId(id);
    }
    
    public Usuario atualizarUsuario(Long id, Usuario novosDados) {
        Usuario usuarioExistente = repositorioUsuario.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        if (novosDados.getPesoAtual() != null && 
            !novosDados.getPesoAtual().equals(usuarioExistente.getPesoAtual())) {
            calculadoraSaude.validarPerdaPeso(
                usuarioExistente.getPesoAtual(), 
                novosDados.getPesoAtual()
            );
        }
        
        usuarioExistente.setNome(novosDados.getNome());
        usuarioExistente.setAltura(novosDados.getAltura());
        usuarioExistente.setPesoAtual(novosDados.getPesoAtual());
        usuarioExistente.setPesoMeta(novosDados.getPesoMeta());
        
        calcularMetricasSaude(usuarioExistente);
        return repositorioUsuario.salvar(usuarioExistente);
    }
    
    public void removerUsuario(Long id) {
        if (!repositorioUsuario.existePorId(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        repositorioUsuario.removerPorId(id);
    }
    
    public Optional<Usuario> buscarPorEmailESenha(String email, String senha) {
        Optional<Usuario> usuario = repositorioUsuario.buscarPorEmail(email);
        if (usuario.isPresent() && usuario.get().getSenha().equals(senha)) {
            return usuario;
        }
        return Optional.empty();
    }
    
    private void calcularMetricasSaude(Usuario usuario) {
        usuario.setImc(calculadoraSaude.calcularIMC(usuario.getPesoAtual(), usuario.getAltura()));
        usuario.setTmb(calculadoraSaude.calcularTMB(usuario));
    }
}