package com.myproject.dao;

import com.myproject.entity.Curso;
import com.myproject.entity.Instrutor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstrutorDAO {

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample;create=true", "app", "app");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Método para obter todos os instrutores com seus cursos associados
    public List<Instrutor> getAllInstrutores() {
        List<Instrutor> instrutores = new ArrayList<>();
        
        String query = "SELECT * FROM INSTRUTOR";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Instrutor instrutor = new Instrutor();
                instrutor.setId(rs.getLong("ID"));
                instrutor.setNome(rs.getString("NOME"));
                instrutor.setEspecialidade(rs.getString("ESPECIALIDADE"));

                // Carrega a lista de cursos associados ao instrutor
                instrutor.setCursos(getCursosByInstrutorId(instrutor.getId()));

                instrutores.add(instrutor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return instrutores;
    }

    // Método para obter cursos por ID do instrutor
    private List<Curso> getCursosByInstrutorId(long instrutorId) {
        List<Curso> cursos = new ArrayList<>();
        String query = "SELECT * FROM CURSO WHERE INSTRUTORID = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setLong(1, instrutorId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getLong("ID"));
                curso.setNome(rs.getString("NOME"));
                curso.setDescricao(rs.getString("DESCRICAO"));
                curso.setDuracao(rs.getInt("DURACAO"));
                curso.setInstrutorId(instrutorId); // Define o instrutorId no curso
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cursos;
    }

    // Método para adicionar um novo instrutor
    public void addInstrutor(Instrutor instrutor) {
        String sql = "INSERT INTO INSTRUTOR (NOME, ESPECIALIDADE) VALUES (?, ?)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, instrutor.getNome());
            pstmt.setString(2, instrutor.getEspecialidade());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar a especialidade do instrutor
    public void updateInstrutor(Instrutor instrutor) {
        String sql = "UPDATE INSTRUTOR SET ESPECIALIDADE = ? WHERE ID = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, instrutor.getEspecialidade());
            pstmt.setLong(2, instrutor.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir um instrutor
    public void deleteInstrutor(long instrutorId) {
        String sql = "DELETE FROM INSTRUTOR WHERE ID = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setLong(1, instrutorId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
