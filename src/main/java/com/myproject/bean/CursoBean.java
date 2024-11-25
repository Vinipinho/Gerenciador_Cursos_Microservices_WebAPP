package com.myproject.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import com.myproject.entity.Curso;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Named("cursoBean")
@RequestScoped
public class CursoBean implements Serializable {
    private List<Curso> cursos;
    private Curso newCurso = new Curso(); // Novo curso a ser adicionado

    // Carregar a lista de Cursos ao inicializar
    public List<Curso> getCursos() {
        if (cursos == null) {
            Client client = ClientBuilder.newClient();
            try {
                cursos = client.target("http://localhost:8084/curso")
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Curso>>() {});
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao carregar cursos."));
                e.printStackTrace();
                cursos = List.of(); // Inicializa com uma lista vazia em caso de erro
            } finally {
                client.close();
            }
        }
        return cursos;
    }

    public Curso getNewCurso() {
        return newCurso;
    }

    public void setNewCurso(Curso newCurso) {
        this.newCurso = newCurso;
    }

    // Método para adicionar um novo curso
    public String addCurso() {
        Client client = ClientBuilder.newClient();
        try {
            Response response = client.target("http://localhost:8084/curso/add")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(newCurso, MediaType.APPLICATION_JSON));

            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Curso cadastrado com sucesso!"));
                cursos = null; // Força atualização da lista de cursos
            } else {
                String mensagemErro = response.readEntity(String.class);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagemErro));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao cadastrar curso."));
            e.printStackTrace();
        } finally {
            client.close();
        }
        newCurso = new Curso(); // Limpa o formulário
        return "add_curso?faces-redirect=true";
    }

    // Método para atualizar um curso existente
    public void updateCurso(Curso curso) {
        if (curso != null) {
            Client client = ClientBuilder.newClient();
            try {
                Response response = client.target("http://localhost:8084/curso/update")
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(curso, MediaType.APPLICATION_JSON));

                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage("Curso atualizado com sucesso!"));
                } else {
                    String mensagemErro = response.readEntity(String.class);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagemErro));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao atualizar curso."));
                e.printStackTrace();
            } finally {
                client.close();
            }
            cursos = null; // Força atualização da lista de cursos
        }
    }

    // Método para excluir um curso
    public void deleteCurso(Curso curso) {
        if (curso != null) {
            Client client = ClientBuilder.newClient();
            try {
                Response response = client.target("http://localhost:8084/curso/delete/" + curso.getId())
                        .request()
                        .delete();

                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage("Curso excluído com sucesso!"));
                    cursos = null; // Força atualização da lista de cursos
                } else {
                    String mensagemErro = response.readEntity(String.class);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagemErro));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao excluir curso."));
                e.printStackTrace();
            } finally {
                client.close();
            }
        }
    }
}
