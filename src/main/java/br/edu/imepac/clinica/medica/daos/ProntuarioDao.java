package br.edu.imepac.clinica.medica.daos;

import br.edu.imepac.clinica.medica.entidades.Prontuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProntuarioDao {

    private final String url = "jdbc:mysql://localhost:3306/clinica_medica?useSSL=false&allowPublicKeyRetrieval=true";
    private final String usuario = "root";
    private final String senha = "";
    private final Connection connection;

    public ProntuarioDao() throws SQLException {
        connection = DriverManager.getConnection(url, usuario, senha);
    }

    public void salvar(Prontuario prontuario) throws SQLException {
        String sql = "INSERT INTO prontuario (receituario, exames, observacoes, id_consulta, id_medico, id_paciente) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, prontuario.getReceituario());
        stmt.setString(2, prontuario.getExames());
        stmt.setString(3, prontuario.getObservacoes());
        stmt.setInt(4, prontuario.getId_consulta());
        stmt.setInt(5, prontuario.getId_medico());
        stmt.setInt(6, prontuario.getId_paciente());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            prontuario.setId(rs.getInt(1));
        }
    }

    public Prontuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM prontuario WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Prontuario p = new Prontuario();
            p.setId(rs.getInt("id"));
            p.setReceituario(rs.getString("receituario"));
            p.setExames(rs.getString("exames"));
            p.setObservacoes(rs.getString("observacoes"));
            p.setId_consulta(rs.getInt("id_consulta"));
            p.setId_medico(rs.getInt("id_medico"));
            p.setId_paciente(rs.getInt("id_paciente"));
            return p;
        }
        return null;
    }

    public List<Prontuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM prontuario";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Prontuario> lista = new ArrayList<>();
        while (rs.next()) {
            Prontuario p = new Prontuario();
            p.setId(rs.getInt("id"));
            p.setReceituario(rs.getString("receituario"));
            p.setExames(rs.getString("exames"));
            p.setObservacoes(rs.getString("observacoes"));
            p.setId_consulta(rs.getInt("id_consulta"));
            p.setId_medico(rs.getInt("id_medico"));
            p.setId_paciente(rs.getInt("id_paciente"));
            lista.add(p);
        }
        return lista;
    }

    public void atualizar(Prontuario prontuario) throws SQLException {
        String sql  = "UPDATE prontuario SET receituario = ?, exames = ?, observacoes = ?, id_consulta = ?, id_medico = ?, id_paciente = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, prontuario.getReceituario());
        stmt.setString(2, prontuario.getExames());
        stmt.setString(3, prontuario.getObservacoes());
        stmt.setInt(4, prontuario.getId_consulta());
        stmt.setInt(5, prontuario.getId_medico());
        stmt.setInt(6, prontuario.getId_paciente());
        stmt.setInt(7, prontuario.getId());
        stmt.executeUpdate();
    }

    public void deletar(long id) throws SQLException {
        String sql = "DELETE FROM prontuario WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        stmt.executeUpdate();
    }
}