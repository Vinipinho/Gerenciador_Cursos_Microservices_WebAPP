/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.dao;

import com.myproject.entity.Curso;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample;create=true", "app", "app");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Método para obter todos os cursos, incluindo o nome do instrutor relacionado
    public List<Curso> getAllCursos() {
        List<Curso> cursos = new ArrayList<>();
        String query = "SELECT c.*, i.NOME AS INSTRUTOR_NOME FROM CURSO c LEFT JOIN INSTRUTOR i ON c.INSTRUTORID = i.ID";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getLong("ID"));
                curso.setNome(rs.getString("NOME"));
                curso.setDescricao(rs.getString("DESCRICAO"));
                curso.setDuracao(rs.getInt("DURACAO"));
                curso.setInstrutorId(rs.getLong("INSTRUTORID"));
                curso.setInstrutorNome(rs.getString("INSTRUTOR_NOME")); // Atribui o nome do instrutor
                cursos.add(curso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursos;
    }

    // Método para adicionar um novo curso
    public void addCurso(Curso curso) {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO CURSO (NOME, DESCRICAO, DURACAO, INSTRUTORID) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, curso.getNome());
            pstmt.setString(2, curso.getDescricao());
            pstmt.setInt(3, curso.getDuracao());
            pstmt.setLong(4, curso.getInstrutorId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar todos os campos do curso
    public void updateCurso(Curso curso) {
        String query = "UPDATE CURSO SET NOME = ?, DESCRICAO = ?, DURACAO = ?, INSTRUTORID = ? WHERE ID = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, curso.getNome());       // Atualiza o nome do curso
            pstmt.setString(2, curso.getDescricao());  // Atualiza a descrição do curso
            pstmt.setInt(3, curso.getDuracao());       // Atualiza a duração do curso
            pstmt.setLong(4, curso.getInstrutorId());  // Atualiza o ID do instrutor
            pstmt.setLong(5, curso.getId());           // Define o ID do curso que será atualizado
            
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para excluir um curso
    public void deleteCurso(long cursoId) {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM CURSO WHERE ID = ?")) {
            pstmt.setLong(1, cursoId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
