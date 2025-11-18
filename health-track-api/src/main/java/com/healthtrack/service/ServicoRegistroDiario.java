package com.healthtrack.service;

import com.healthtrack.model.RegistroDiario;
import com.healthtrack.model.Usuario;
import com.healthtrack.repository.RepositorioRegistroDiario;
import com.healthtrack.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServicoRegistroDiario {
    
    @Autowired
    private RepositorioRegistroDiario repositorioRegistroDiario;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    
    @Autowired
    private CalculadoraSaude calculadoraSaude;
    
    public RegistroDiario criarRegistroDiario(RegistroDiario registro) {
        if (!repositorioUsuario.existePorId(registro.getUsuarioId())) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        Optional<RegistroDiario> registroExistente = repositorioRegistroDiario.buscarPorUsuarioIdEData(
            registro.getUsuarioId(), registro.getData()
        );
        
        if (registroExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe um registro para esta data");
        }
        
        if (registro.getPeso() != null) {
            Usuario usuario = repositorioUsuario.buscarPorId(registro.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            calculadoraSaude.validarPerdaPeso(usuario.getPesoAtual(), registro.getPeso());
        }
        
        return repositorioRegistroDiario.salvar(registro);
    }
    
    public List<RegistroDiario> buscarRegistrosPorUsuario(Long usuarioId) {
        return repositorioRegistroDiario.buscarPorUsuarioId(usuarioId);
    }
    
    public Optional<RegistroDiario> buscarRegistroPorId(Long id) {
        return repositorioRegistroDiario.buscarPorId(id);
    }
    
    public RegistroDiario atualizarRegistroDiario(Long id, RegistroDiario novosDados) {
        RegistroDiario registroExistente = repositorioRegistroDiario.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro diário não encontrado"));
        
        if (novosDados.getPeso() != null && 
            !novosDados.getPeso().equals(registroExistente.getPeso())) {
            Usuario usuario = repositorioUsuario.buscarPorId(registroExistente.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            calculadoraSaude.validarPerdaPeso(usuario.getPesoAtual(), novosDados.getPeso());
        }
        
        registroExistente.setPeso(novosDados.getPeso());
        registroExistente.setCintura(novosDados.getCintura());
        registroExistente.setQuadril(novosDados.getQuadril());
        registroExistente.setPeito(novosDados.getPeito());
        registroExistente.setHumor(novosDados.getHumor());
        registroExistente.setNivelEnergia(novosDados.getNivelEnergia());
        registroExistente.setAnotacoes(novosDados.getAnotacoes());
        
        return repositorioRegistroDiario.salvar(registroExistente);
    }
    
    public void removerRegistroDiario(Long id) {
        if (!repositorioRegistroDiario.existePorId(id)) {
            throw new IllegalArgumentException("Registro diário não encontrado");
        }
        repositorioRegistroDiario.removerPorId(id);
    }
    
    public Optional<RegistroDiario> buscarRegistroDeHoje(Long usuarioId) {
        return repositorioRegistroDiario.buscarPorUsuarioIdEData(usuarioId, LocalDate.now());
    }
}