package com.healthtrack.controller;

import com.healthtrack.model.Usuario;
import com.healthtrack.service.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class ControladorUsuario {
    
    @Autowired
    private ServicoUsuario servicoUsuario;
    
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioCriado = servicoUsuario.criarUsuario(usuario);
            return ResponseEntity.ok(usuarioCriado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return servicoUsuario.buscarTodosUsuarios();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = servicoUsuario.buscarUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = servicoUsuario.atualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        try {
            servicoUsuario.removerUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credenciais) {
        String email = credenciais.get("email");
        String senha = credenciais.get("senha");
        
        Optional<Usuario> usuario = servicoUsuario.buscarPorEmailESenha(email, senha);
        
        if (usuario.isPresent()) {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("sucesso", true);
            resposta.put("usuario", usuario.get());
            return ResponseEntity.ok(resposta);
        } else {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("sucesso", false);
            resposta.put("mensagem", "Email ou senha incorretos");
            return ResponseEntity.status(401).body(resposta);
        }
    }
}