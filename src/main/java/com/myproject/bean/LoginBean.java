/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bean;

import jakarta.inject.Named;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;


@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;

    // Construtor sem argumentos
    public LoginBean() {
    }

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validate() {
        // Validação do login - substitua com sua lógica de autenticação
        if ("admin".equals(username) && "admin".equals(password)) {
            return "gerenciamento_cursos.xhtml?faces-redirect=true";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null) {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciais incorretas", "Usuário ou senha inválidos."));
            }
            return null; // Permanece na mesma página em caso de falha de autenticação
        }
    }
}
