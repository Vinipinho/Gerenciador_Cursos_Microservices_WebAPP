package com.myproject.entity;

import java.io.Serializable;

public class Estudante implements Serializable {
    private long id;
    private String nome;
    private String email;
    private long cursoId; // Relacionamento com o curso

    // Construtor vazio
    public Estudante() {
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCursoId() {
        return cursoId;
    }

    public void setCursoId(long cursoId) {
        this.cursoId = cursoId;
    }
}
