package br.edu.imepac.clinica.medica.daos;

import br.edu.imepac.clinica.medica.entidades.Especialidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDao {

    private final String url = "jdbc:mysql://localhost:3306/clinica_medica?useSSL=false&allowPublicKeyRetrieval=true";
    private final String usuario = "root";
    private final String senha = "";
    private final Connection connection;

    public EspecialidadeDao() throws SQLException {
        connection = DriverManager.getConnection(url, usuario, senha);
    }

    public void salvar(Especialidade especialidade) throws SQLException {
        String sql = "Insert INTO especialidade (nome, descricao) VALUES (?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, especialidade.getNome());
        stmt.setString(2, especialidade.getDescricao());
        stmt.executeUpdate();
    }

    public Especialidade buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Especialidade WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Especialidade e = new Especialidade();
            e.setId(rs.getInt("id"));
            e.setNome(rs.getString("nome"));
            e.setDescricao(rs.getString("descricao"));
            return e;
        }
        return null;
    }

    public List<Especialidade> listarTodas() throws SQLException {
        String sql = "SELECT * FROM Especialidade";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Especialidade> lista = new ArrayList<>();
        while(rs.next()) {
            Especialidade e = new Especialidade();
            e.setId(rs.getInt("id"));
            e.setNome(rs.getString("nome"));
            e.setDescricao(rs.getString("descricao"));
            lista.add(e);
        }
        return lista;
    }

    public void atualizar(Especialidade especialidade) throws SQLException {
        String sql = "UPDATE especialidade SET nome = ?, descricao = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, especialidade.getNome());
        stmt.setString(2, especialidade.getDescricao());
        stmt.setInt(3, especialidade.getId());
        stmt.executeUpdate();
    }

    public void deletar(long id) throws SQLException {
        String sql = "DELETE FROM especialidade WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        stmt.executeUpdate();
    }
}