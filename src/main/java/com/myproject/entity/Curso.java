package com.myproject.entity;

public class Curso {
    private long id;
    private String nome;
    private String descricao;
    private int duracao;
    private long instrutorId;
    private String instrutorNome; // Novo campo para armazenar o nome do instrutor

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public long getInstrutorId() {
        return instrutorId;
    }

    public void setInstrutorId(long instrutorId) {
        this.instrutorId = instrutorId;
    }

    public String getInstrutorNome() {
        return instrutorNome;
    }

    public void setInstrutorNome(String instrutorNome) {
        this.instrutorNome = instrutorNome;
    }
}

