package com.myproject.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import com.myproject.dao.EstudanteDAO;
import com.myproject.entity.Estudante;

@Named("estudanteBean")
@RequestScoped
public class EstudanteBean implements Serializable {
    private List<Estudante> estudantes;
    private EstudanteDAO estudanteDAO = new EstudanteDAO();
    private Estudante newEstudante = new Estudante(); // Novo estudante a ser adicionado
    private Estudante selectedEstudante; // Estudante selecionado para edição

    // Carregar a lista de estudantes ao inicializar
    public List<Estudante> getEstudantes() {
        if (estudantes == null) {
            estudantes = estudanteDAO.getAllEstudantes();
        }
        return estudantes;
    }

    public Estudante getNewEstudante() {
        return newEstudante;
    }

    public void setNewEstudante(Estudante newEstudante) {
        this.newEstudante = newEstudante;
    }

    public Estudante getSelectedEstudante() {
        return selectedEstudante;
    }

    public void setSelectedEstudante(Estudante selectedEstudante) {
        this.selectedEstudante = selectedEstudante;
    }

    // Método para adicionar um novo estudante
   public String addEstudante() {
    try {
        estudanteDAO.addEstudante(newEstudante); // Chama o DAO para adicionar o estudante
        estudantes = estudanteDAO.getAllEstudantes(); // Atualiza a lista de estudantes
        newEstudante = new Estudante(); // Limpa o formulário
    } catch (Exception e) {
        e.printStackTrace(); // Log de qualquer erro que ocorra durante a adição
    }
    return "add_estudante?faces-redirect=true"; // Redireciona para garantir a atualização da página
}

    // Método para atualizar os dados do estudante
    public void updateEstudante(Estudante estudante) {
        if (estudante != null) {
            estudanteDAO.updateEstudante(estudante); // Atualiza o estudante no banco de dados
            estudantes = estudanteDAO.getAllEstudantes(); // Atualiza a lista de estudantes
        }
    }

    // Método para excluir um estudante
    public void deleteEstudante(Estudante estudante) {
        if (estudante != null) {
            estudanteDAO.deleteEstudante(estudante.getId()); // Remove o estudante do banco de dados
            estudantes = estudanteDAO.getAllEstudantes(); // Atualiza a lista de estudantes
        }
    }
}
