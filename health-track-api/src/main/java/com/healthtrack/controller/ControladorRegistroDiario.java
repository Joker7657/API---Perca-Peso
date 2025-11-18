package com.healthtrack.controller;

import com.healthtrack.model.RegistroDiario;
import com.healthtrack.service.ServicoRegistroDiario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registros-diarios")
public class ControladorRegistroDiario {
    
    @Autowired
    private ServicoRegistroDiario servicoRegistroDiario;
    
    @PostMapping
    public ResponseEntity<RegistroDiario> criarRegistroDiario(@RequestBody RegistroDiario registro) {
        try {
            RegistroDiario registroCriado = servicoRegistroDiario.criarRegistroDiario(registro);
            return ResponseEntity.ok(registroCriado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public List<RegistroDiario> listarRegistrosDoUsuario(@PathVariable Long usuarioId) {
        return servicoRegistroDiario.buscarRegistrosPorUsuario(usuarioId);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RegistroDiario> buscarRegistro(@PathVariable Long id) {
        Optional<RegistroDiario> registro = servicoRegistroDiario.buscarRegistroPorId(id);
        return registro.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RegistroDiario> atualizarRegistro(@PathVariable Long id, @RequestBody RegistroDiario registro) {
        try {
            RegistroDiario registroAtualizado = servicoRegistroDiario.atualizarRegistroDiario(id, registro);
            return ResponseEntity.ok(registroAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerRegistro(@PathVariable Long id) {
        try {
            servicoRegistroDiario.removerRegistroDiario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/usuario/{usuarioId}/hoje")
    public ResponseEntity<RegistroDiario> buscarRegistroDeHoje(@PathVariable Long usuarioId) {
        Optional<RegistroDiario> registroHoje = servicoRegistroDiario.buscarRegistroDeHoje(usuarioId);
        return registroHoje.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }
}