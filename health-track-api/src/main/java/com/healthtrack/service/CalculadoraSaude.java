package com.healthtrack.service;

import com.healthtrack.model.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class CalculadoraSaude {
    
    public Double calcularIMC(Double peso, Double altura) {
        if (peso == null || altura == null || altura == 0) {
            return null;
        }
        return peso / (altura * altura);
    }
    
    public String getCategoriaIMC(Double imc) {
        if (imc == null) return "Não calculado";
        
        if (imc < 18.5) return "Abaixo do peso";
        else if (imc < 25) return "Peso normal";
        else if (imc < 30) return "Sobrepeso";
        else if (imc < 35) return "Obesidade Grau I";
        else if (imc < 40) return "Obesidade Grau II";
        else return "Obesidade Grau III";
    }
    
    public Double calcularTMB(Usuario usuario) {
        if (usuario.getPesoAtual() == null || usuario.getAltura() == null || 
            usuario.getDataNascimento() == null || usuario.getGenero() == null) {
            return null;
        }
        
        int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();
        Double peso = usuario.getPesoAtual();
        Double alturaCm = usuario.getAltura() * 100;
        
        if (usuario.getGenero().equals("MASCULINO")) {
            return (10 * peso) + (6.25 * alturaCm) - (5 * idade) + 5;
        } else {
            return (10 * peso) + (6.25 * alturaCm) - (5 * idade) - 161;
        }
    }
    
    public void validarPerdaPeso(Double pesoAtual, Double novoPeso) {
        if (pesoAtual == null || novoPeso == null) return;
        
        double perda = pesoAtual - novoPeso;
        double perdaMaxima = pesoAtual * 0.01;
        
        if (perda > perdaMaxima) {
            throw new IllegalArgumentException(
                "Perda de peso muito rápida! Máximo saudável: " + 
                String.format("%.2f", perdaMaxima) + " kg por semana"
            );
        }
    }
    
    // Fórmula de Jackson-Pollock simplificada para estimar gordura corporal
    public Double calcularPercentualGordura(Usuario usuario) {
        if (usuario.getPesoAtual() == null || usuario.getAltura() == null || 
            usuario.getDataNascimento() == null || usuario.getGenero() == null ||
            usuario.getImc() == null) {
            return null;
        }
        
        int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();
        Double imc = usuario.getImc();
        
        // Fórmula de Deurenberg
        if (usuario.getGenero().equals("MASCULINO")) {
            return (1.20 * imc) + (0.23 * idade) - 16.2;
        } else {
            return (1.20 * imc) + (0.23 * idade) - 5.4;
        }
    }
}