package com.myproject.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import com.myproject.entity.Instrutor;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Named("instrutorBean")
@RequestScoped
public class InstrutorBean implements Serializable {
    private List<Instrutor> instrutores;
    private Instrutor newInstrutor = new Instrutor(); // Novo instrutor a ser adicionado

    // Carregar a lista de instrutores ao inicializar
    public List<Instrutor> getInstrutores() {
        if (instrutores == null) {
           Client client = ClientBuilder.newClient();
           

            try {
                
                // Faz a requisição GET e converte a resposta para uma lista de objetos Usuario
                instrutores = client.target("http://localhost:8081/instrutor")
                              .request(MediaType.APPLICATION_JSON)
                              .get(new GenericType<List<Instrutor>>() {});
            } catch (Exception e) {
                e.printStackTrace();
                instrutores = List.of(); // Caso ocorra um erro, inicializa com uma lista vazia
            } finally {
                client.close(); // Fecha o cliente para liberar recursos
            }   
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
        
        
         Client client = ClientBuilder.newClient();

            try {
                // Faz a requisição POST para o endpoint
                Response response = client.target("http://localhost:8081/instrutor/add")
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(newInstrutor, MediaType.APPLICATION_JSON));

                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    // Produto cadastrado com sucesso
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto cadastrado com sucesso!"));
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
              instrutores = getInstrutores(); // Atualiza a lista de instrutores
        newInstrutor = new Instrutor(); // Limpa o formulário
        return "add_instrutor?faces-redirect=true"; // Redireciona para a página de adição
    
        
    }

    // Método para atualizar a especialidade do instrutor
    public void updateEspecialidade(Instrutor instrutor) {
        if (instrutor != null) {
            
            Client client = ClientBuilder.newClient();

            try {
                // Faz a requisição PUT para o endpoint
                Response response = client.target("http://localhost:8081/instrutor/update")
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(instrutor, MediaType.APPLICATION_JSON));

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
            instrutores = getInstrutores(); // Atualiza a lista de instrutores
        }
    }

    // Método para excluir um instrutor
    public void deleteInstrutor(Instrutor instrutor) {
        
        
        if (instrutor != null) {
            Client client = ClientBuilder.newClient();
            try {
                // Faz a requisição DELETE para o endpoint
               Response response = client.target("http://localhost:8081/instrutor/delete/" + instrutor.getId())
                                      .request()
                                      .delete();

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
            instrutores = getInstrutores(); // Atualiza a lista de instrutores
        }
    }
}
