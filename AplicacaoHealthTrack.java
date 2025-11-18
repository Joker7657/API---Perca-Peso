package com.healthtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AplicacaoHealthTrack {
    public static void main(String[] args) {
        SpringApplication.run(AplicacaoHealthTrack.class, args);
        System.out.println("✅ Health Track API está rodando!");
        System.out.println("📊 Acesse: http://localhost:8080");
    }
}