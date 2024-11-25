package com.myproject.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import com.myproject.entity.Estudante;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Named("estudanteBean")
@RequestScoped
public class EstudanteBean implements Serializable {
    private List<Estudante> estudantes;
    private Estudante newEstudante = new Estudante(); // Novo estudante a ser adicionado
    private Estudante selectedEstudante; // Estudante selecionado para edição

    // Carregar a lista de instrutores ao inicializar
    public List<Estudante> getEstudantes() {
        if (estudantes == null) {
           Client client = ClientBuilder.newClient();
           

            try {
                
                // Faz a requisição GET e converte a resposta para uma lista de objetos Usuario
                estudantes = client.target("http://localhost:8082/estudante")
                              .request(MediaType.APPLICATION_JSON)
                              .get(new GenericType<List<Estudante>>() {});
            } catch (Exception e) {
                e.printStackTrace();
                estudantes = List.of(); // Caso ocorra um erro, inicializa com uma lista vazia
            } finally {
                client.close(); // Fecha o cliente para liberar recursos
            }   
        }
        return estudantes;
    }

        public Estudante getNewEstudante() {
            return newEstudante;
    }

    public void setNewEstudante(Estudante newEstudante) {
        this.newEstudante = newEstudante;
    }

    // Método para adicionar um novo estudante
    public String addEstudante() {
        
        
         Client client = ClientBuilder.newClient();

            try {
                // Faz a requisição POST para o endpoint
                Response response = client.target("http://localhost:8082/estudante/add")
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(newEstudante, MediaType.APPLICATION_JSON));

                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    // Produto cadastrado com sucesso
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Estudante cadastrado com sucesso!"));
                } else {
                    // Mensagem de erro retornada pelo endpoint
                    String mensagemErro = response.readEntity(String.class);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagemErro));
                }
            } catch (Exception e) {
                // Trata exceções e exibe mensagem de erro
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao cadastrar produto."));
                e.printStackTrace();
            } finally {
                client.close(); // Fecha o cliente REST
            }
              estudantes = getEstudantes(); // Atualiza a lista de estudantes
        newEstudante = new Estudante(); // Limpa o formulário
        return "add_estudante?faces-redirect=true"; // Redireciona para a página de adição
    
        
    }

    // Método para atualizar a especialidade do estudante
    public void updateEstudante(Estudante estudante) {
        if (estudante != null) {
            
            Client client = ClientBuilder.newClient();

            try {
                // Faz a requisição PUT para o endpoint
                Response response = client.target("http://localhost:8082/estudante/update")
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(estudante, MediaType.APPLICATION_JSON));

                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    // Produto cadastrado com sucesso
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Atualização feita com sucesso!"));
                } else {
                    // Mensagem de erro retornada pelo endpoint
                    String mensagemErro = response.readEntity(String.class);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagemErro));
                }
            } catch (Exception e) {
                // Trata exceções e exibe mensagem de erro
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao atualizar."));
                e.printStackTrace();
            } finally {
                client.close(); // Fecha o cliente REST
            }
            estudantes = getEstudantes(); // Atualiza a lista de estudantes
        }
    }

    // Método para excluir um estudante
    public void deleteEstudante(Estudante estudante) {
        
        
        if (estudante != null) {
            Client client = ClientBuilder.newClient();
            try {
                // Faz a requisição DELETE para o endpoint
               Response response = client.target("http://localhost:8082/estudante/delete/" + estudante.getId())
                                      .request()
                                      .delete();

                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    // Deletado com sucesso
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Atualização feita com sucesso!"));
                } else {
                    // Mensagem de erro retornada pelo endpoint
                    String mensagemErro = response.readEntity(String.class);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagemErro));
                }
            } catch (Exception e) {
                // Trata exceções e exibe mensagem de erro
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao atualizar."));
                e.printStackTrace();
            } finally {
                client.close(); // Fecha o cliente REST
            }
            estudantes = getEstudantes(); // Atualiza a lista de estudantes
        }
    }
}
