/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.dao;

import com.myproject.entity.Estudante;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudanteDAO {

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample;create=true", "app", "app");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Método para obter todos os estudantes
    public List<Estudante> getAllEstudantes() {
        List<Estudante> estudantes = new ArrayList<>();
        String query = "SELECT * FROM ESTUDANTE";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Estudante estudante = new Estudante();
                estudante.setId(rs.getLong("ID"));
                estudante.setNome(rs.getString("NOME"));
                estudante.setEmail(rs.getString("EMAIL"));
                estudante.setCursoId(rs.getLong("CURSOID"));
                estudantes.add(estudante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estudantes;
    }

    // Método para adicionar um novo estudante
    public void addEstudante(Estudante estudante) {
    String sql = "INSERT INTO ESTUDANTE (NOME, EMAIL, CURSOID) VALUES (?, ?, ?)";
    
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
         
        pstmt.setString(1, estudante.getNome());
        pstmt.setString(2, estudante.getEmail());
        pstmt.setLong(3, estudante.getCursoId());
        pstmt.executeUpdate(); // Executa a inserção no banco de dados
        
    } catch (SQLException e) {
        e.printStackTrace(); // Log do erro em caso de falha
    }
}

    // Método para atualizar os dados do estudante
    public void updateEstudante(Estudante estudante) {
        String sql = "UPDATE ESTUDANTE SET NOME = ?, EMAIL = ?, CURSOID = ? WHERE ID = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, estudante.getNome());
            pstmt.setString(2, estudante.getEmail());
            pstmt.setLong(3, estudante.getCursoId());
            pstmt.setLong(4, estudante.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir um estudante
    public void deleteEstudante(long estudanteId) {
        String sql = "DELETE FROM ESTUDANTE WHERE ID = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setLong(1, estudanteId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

