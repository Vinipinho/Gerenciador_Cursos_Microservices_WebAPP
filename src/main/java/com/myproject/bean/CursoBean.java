/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bean;

import jakarta.enterprise.context.RequestScoped; // Alterado para RequestScoped
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import com.myproject.dao.CursoDAO;
import com.myproject.dao.InstrutorDAO;
import com.myproject.entity.Curso;
import com.myproject.entity.Instrutor;

@Named("cursoBean")
@RequestScoped // Alterado para RequestScoped
public class CursoBean implements Serializable {
    private List<Curso> cursos;
    private CursoDAO cursoDAO = new CursoDAO();
    private InstrutorDAO instrutorDAO = new InstrutorDAO();
    private List<Instrutor> instrutores;
    private Curso newCurso = new Curso(); // Novo curso a ser adicionado
    private Curso selectedCurso; // Curso selecionado para edição

    // Carregar a lista de cursos ao inicializar
    public List<Curso> getCursos() {
        if (cursos == null) {
            cursos = cursoDAO.getAllCursos();
        }
        return cursos;
    }
    
    public void updateCurso(Curso curso) {
    if (curso != null) {
        cursoDAO.updateCurso(curso); // Atualiza o curso no banco de dados
        cursos = cursoDAO.getAllCursos(); // Atualiza a lista de cursos após a alteração
    }
}


    public Curso getNewCurso() {
        return newCurso;
    }

    public void setNewCurso(Curso newCurso) {
        this.newCurso = newCurso;
    }

    public Curso getSelectedCurso() {
        return selectedCurso;
    }

    public void setSelectedCurso(Curso selectedCurso) {
        this.selectedCurso = selectedCurso;
    }

    // Método para adicionar um novo curso
    public String addCurso() {
        cursoDAO.addCurso(newCurso);
        cursos = cursoDAO.getAllCursos(); // Atualiza a lista de cursos
        newCurso = new Curso(); // Limpa o formulário
        return "gerenciamento_cursos?faces-redirect=true"; // Redireciona para a página de gerenciamento
    }

    // Método para atualizar a descrição do curso
    public void updateCurso() {
        if (selectedCurso != null) {
            cursoDAO.updateCurso(selectedCurso); // Atualiza o curso no banco de dados
            cursos = cursoDAO.getAllCursos(); // Atualiza a lista de cursos
            selectedCurso = null; // Limpa o curso selecionado
        }
    }

    // Método para excluir um curso e atualizar a lista de instrutores
    public void deleteCurso(Curso curso) {
        if (curso != null) {
            cursoDAO.deleteCurso(curso.getId()); // Remove o curso do banco de dados
            cursos = cursoDAO.getAllCursos(); // Atualiza a lista de cursos
            instrutores = instrutorDAO.getAllInstrutores(); // Atualiza a lista de instrutores
        }
    }

    // Getter para a lista de instrutores
    public List<Instrutor> getInstrutores() {
        if (instrutores == null) {
            instrutores = instrutorDAO.getAllInstrutores();
        }
        return instrutores;
    }
}
