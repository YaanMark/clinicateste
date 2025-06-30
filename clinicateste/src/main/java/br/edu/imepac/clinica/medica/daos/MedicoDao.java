package br.edu.imepac.clinica.medica.daos;

import br.edu.imepac.clinica.medica.entidades.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao {

    private final String url = "jdbc:mysql://localhost:3306/clinica_medica?useSSL=false&allowPublicKeyRetrieval=true";
    private final String usuario = "root";
    private final String senha = "";
    private final Connection connection;

    public MedicoDao() throws SQLException {
        connection = DriverManager.getConnection(url, usuario, senha);
    }

    public void salvar(Medico medico) throws SQLException {
        String sql = "INSERT INTO medico (nome, crm, especialidade_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, medico.getNome());
        stmt.setString(2, medico.getCrm());
        stmt.setInt(3, medico.getEspecialidade().getId());
        stmt.executeUpdate();
    }

    public Medico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM medico WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Medico m = new Medico();
            m.setId(rs.getInt("id"));
            m.setNome(rs.getString("nome"));
            m.setCrm(rs.getString("crm"));
            m.setEspecialidadeId(rs.getInt("especialidade_id"));
            return m;
        }
        return null;
    }

    public List<Medico> listarTodos() throws SQLException {
        String sql = "SELECT * FROM medico";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Medico> lista = new ArrayList<>();
        while (rs.next()) {
            Medico m = new Medico();
            m.setId(rs.getInt("id"));
            m.setNome(rs.getString("nome"));
            m.setCrm(rs.getString("crm"));
            m.setEspecialidadeId(rs.getInt("especialidade_id"));
            lista.add(m);
        }
        return lista;
    }

    public void atualizar(Medico medico) throws SQLException {
        String sql  = "UPDATE medico SET nome = ?, crm = ?, especialidade_id = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, medico.getNome());
        stmt.setString(2, medico.getCrm());
        stmt.setInt(3, medico.getEspecialidade().getId());
        stmt.setInt(4, medico.getId());
        stmt.executeUpdate();
    }

    public void deletar(long id) throws SQLException {
        String sql = "DELETE FROM medico WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        stmt.executeUpdate();
    }

}
