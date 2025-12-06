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
        
        // Atualizar peso do usuário
        if (registro.getPeso() != null) {
            Usuario usuario = repositorioUsuario.buscarPorId(registro.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            calculadoraSaude.validarPerdaPeso(usuario.getPesoAtual(), registro.getPeso());
            
            // Definir peso inicial se for o primeiro registro
            if (usuario.getPesoInicial() == null) {
                usuario.setPesoInicial(usuario.getPesoAtual());
            }
            
            Double pesoAnterior = usuario.getPesoAtual();
            
            // Atualizar peso atual do usuário
            usuario.setPesoAtual(registro.getPeso());
            // Recalcular IMC
            usuario.setImc(calculadoraSaude.calcularIMC(registro.getPeso(), usuario.getAltura()));
            
            // Sistema de Recompensas
            if (usuario.getSistemaRecompensas() == null) {
                usuario.setSistemaRecompensas(new com.healthtrack.model.SistemaRecompensas());
                usuario.getSistemaRecompensas().inicializarConquistas();
            }
            
            atualizarRecompensas(usuario, registro, pesoAnterior);
            repositorioUsuario.salvar(usuario);
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

    public List<RegistroDiario> buscarTodosRegistros() {
        return repositorioRegistroDiario.buscarTodos();
    }
    
    private void atualizarRecompensas(Usuario usuario, RegistroDiario registro, Double pesoAnterior) {
        var recompensas = usuario.getSistemaRecompensas();
        
        // Incrementar total de registros
        recompensas.setTotalRegistros(recompensas.getTotalRegistros() + 1);
        
        // Calcular peso perdido
        if (pesoAnterior != null && registro.getPeso() < pesoAnterior) {
            double pesoPerdido = pesoAnterior - registro.getPeso();
            recompensas.setTotalPesoPermitido(recompensas.getTotalPesoPermitido() + pesoPerdido);
        }
        
        // Atualizar streak (dias consecutivos)
        LocalDate hoje = LocalDate.now();
        LocalDate ultimoRegistro = recompensas.getUltimoRegistro();
        
        if (ultimoRegistro == null) {
            // Primeiro registro
            recompensas.setDiasConsecutivos(1);
        } else if (ultimoRegistro.plusDays(1).equals(hoje)) {
            // Dia consecutivo
            recompensas.setDiasConsecutivos(recompensas.getDiasConsecutivos() + 1);
        } else if (!ultimoRegistro.equals(hoje)) {
            // Perdeu o streak
            recompensas.setDiasConsecutivos(1);
        }
        
        recompensas.setUltimoRegistro(hoje);
        
        // Calcular pontos com multiplicador
        int pontosBase = 5;
        double multiplicador = recompensas.getMultiplicadorStreak();
        int pontosGanhos = (int) (pontosBase * multiplicador);
        
        // Bônus por perda de peso
        if (pesoAnterior != null && registro.getPeso() < pesoAnterior) {
            double pesoPerdido = pesoAnterior - registro.getPeso();
            pontosGanhos += (int) (pesoPerdido * 10); // 10 pontos por kg perdido
        }
        
        recompensas.adicionarPontos(pontosGanhos);
        
        // Atualizar progresso das conquistas
        verificarConquistas(usuario);
    }
    
    private void verificarConquistas(Usuario usuario) {
        var recompensas = usuario.getSistemaRecompensas();
        
        // Primeira Vitória
        recompensas.atualizarProgressoConquista("primeira_vitoria", recompensas.getTotalRegistros());
        
        // Dedicado (7 dias consecutivos)
        recompensas.atualizarProgressoConquista("dedicado", recompensas.getDiasConsecutivos());
        
        // Imparável (30 dias consecutivos)
        recompensas.atualizarProgressoConquista("imparavel", recompensas.getDiasConsecutivos());
        
        // Transformação (5kg perdidos)
        recompensas.atualizarProgressoConquista("transformacao", recompensas.getTotalPesoPermitido().intValue());
        
        // Meta Alcançada
        if (usuario.getPesoMeta() != null && usuario.getPesoAtual() <= usuario.getPesoMeta()) {
            recompensas.atualizarProgressoConquista("meta_alcancada", 1);
        }
        
        // Lenda (100 dias consecutivos)
        recompensas.atualizarProgressoConquista("lenda", recompensas.getDiasConsecutivos());
        
        // Consistente (50 registros totais)
        recompensas.atualizarProgressoConquista("consistente", recompensas.getTotalRegistros());
        
        // Relâmpago (10kg perdidos)
        recompensas.atualizarProgressoConquista("relampago", recompensas.getTotalPesoPermitido().intValue());
        
        // Forte (20 tarefas)
        recompensas.atualizarProgressoConquista("forte", recompensas.getTarefasCompletas());
        
        // Campeão (1000 pontos)
        recompensas.atualizarProgressoConquista("campeao", recompensas.getPontos());
    }
}