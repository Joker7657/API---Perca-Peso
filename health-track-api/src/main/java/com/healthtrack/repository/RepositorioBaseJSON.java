package com.healthtrack.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.util.*;

public abstract class RepositorioBaseJSON<T> {
    private final ObjectMapper mapper;
    private final File arquivo;
    private final Map<Long, T> dados = new HashMap<>();
    private Long proximoId = 1L;
    
    public RepositorioBaseJSON(String nomeArquivo) {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.arquivo = new File("data/" + nomeArquivo);
        carregarDoArquivo();
    }
    
    protected abstract Class<T> getClasseEntidade();
    protected abstract Long getIdEntidade(T entidade);
    protected abstract void setIdEntidade(T entidade, Long id);
    
    private void carregarDoArquivo() {
        try {
            if (!arquivo.exists()) {
                arquivo.getParentFile().mkdirs();
                arquivo.createNewFile();
                mapper.writeValue(arquivo, new ArrayList<T>());
                return;
            }
            
            List<T> lista = mapper.readValue(arquivo, 
                mapper.getTypeFactory().constructCollectionType(List.class, getClasseEntidade()));
            
            for (T entidade : lista) {
                Long id = getIdEntidade(entidade);
                if (id != null) {
                    dados.put(id, entidade);
                    if (id >= proximoId) {
                        proximoId = id + 1;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
    
    private void salvarNoArquivo() {
        try {
            mapper.writeValue(arquivo, new ArrayList<>(dados.values()));
        } catch (Exception e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
    
    public T salvar(T entidade) {
        Long id = getIdEntidade(entidade);
        if (id == null) {
            setIdEntidade(entidade, proximoId++);
        }
        dados.put(getIdEntidade(entidade), entidade);
        salvarNoArquivo();
        return entidade;
    }
    
    public Optional<T> buscarPorId(Long id) {
        return Optional.ofNullable(dados.get(id));
    }
    
    public List<T> buscarTodos() {
        return new ArrayList<>(dados.values());
    }
    
    public void removerPorId(Long id) {
        dados.remove(id);
        salvarNoArquivo();
    }
    
    public boolean existePorId(Long id) {
        return dados.containsKey(id);
    }
}