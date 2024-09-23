package com.myproject.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import com.myproject.dao.InstrutorDAO;
import com.myproject.entity.Instrutor;

@Named("instrutorBean")
@RequestScoped
public class InstrutorBean implements Serializable {
    private List<Instrutor> instrutores;
    private InstrutorDAO instrutorDAO = new InstrutorDAO();
    private Instrutor newInstrutor = new Instrutor(); // Novo instrutor a ser adicionado

    // Carregar a lista de instrutores ao inicializar
    public List<Instrutor> getInstrutores() {
        if (instrutores == null) {
            instrutores = instrutorDAO.getAllInstrutores(); // Certifique-se de que instrutores e seus cursos sejam carregados corretamente
        }
        return instrutores;
    }

    public Instrutor getNewInstrutor() {
        return newInstrutor;
    }

    public void setNewInstrutor(Instrutor newInstrutor) {
        this.newInstrutor = newInstrutor;
    }

    // Método para adicionar um novo instrutor
    public String addInstrutor() {
        instrutorDAO.addInstrutor(newInstrutor);
        instrutores = instrutorDAO.getAllInstrutores(); // Atualiza a lista de instrutores
        newInstrutor = new Instrutor(); // Limpa o formulário
        return "add_instrutor?faces-redirect=true"; // Redireciona para a página de adição
    }

    // Método para atualizar a especialidade do instrutor
    public void updateEspecialidade(Instrutor instrutor) {
        if (instrutor != null) {
            instrutorDAO.updateInstrutor(instrutor); // Atualiza a especialidade no banco de dados
            instrutores = instrutorDAO.getAllInstrutores(); // Atualiza a lista de instrutores
        }
    }

    // Método para excluir um instrutor
    public void deleteInstrutor(Instrutor instrutor) {
        if (instrutor != null) {
            instrutorDAO.deleteInstrutor(instrutor.getId()); // Remove o instrutor do banco de dados
            instrutores = instrutorDAO.getAllInstrutores(); // Atualiza a lista de instrutores
        }
    }
}
