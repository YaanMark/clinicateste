package br.edu.imepac.clinica.medica.daos;

import br.edu.imepac.clinica.medica.entidades.Consulta;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDao {

    private final String url = "jdbc:mysql://localhost:3306/clinica_medica?useSSL=false&allowPublicKeyRetrieval=true";
    private final String usuario = "root";
    private final String senha = "";
    private final Connection connection;

    public ConsultaDao() throws SQLException {
        connection = DriverManager.getConnection(url, usuario, senha);
    }

    public void salvar(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consulta (data_hora, sintomas, eRetorno, estaAtiva, id_paciente, id_medico) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setTimestamp(1, Timestamp.valueOf(consulta.getData()));
        stmt.setString(2, consulta.getSintomas());
        stmt.setBoolean(3, consulta.iseRetorno());
        stmt.setBoolean(4, consulta.isEstaAtiva());
        stmt.setInt(5, consulta.getIdPaciente());
        stmt.setInt(6, consulta.getIdMedico());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            consulta.setId(rs.getInt(1));
        }
    }

    public Consulta buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM consulta WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Consulta c = new Consulta();
            c.setId(rs.getInt("id"));
            c.setData(rs.getTimestamp("data_hora").toLocalDateTime());
            c.setSintomas(rs.getString("sintomas"));
            c.seteRetorno(rs.getBoolean("eRetorno"));
            c.setEstaAtiva(rs.getBoolean("estaAtiva"));
            c.setIdPaciente(rs.getInt("id_paciente"));
            c.setIdMedico(rs.getInt("id_medico"));
            return c;
        }
        return null;
    }

    public List<Consulta> listarTodos() throws SQLException {
        String sql = "SELECT * FROM consulta";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Consulta> lista = new ArrayList<>();
        while (rs.next()) {
            Consulta c = new Consulta();
            c.setId(rs.getInt("id"));
            c.setData(rs.getTimestamp("data_hora").toLocalDateTime());
            c.setSintomas(rs.getString("sintomas"));
            c.seteRetorno(rs.getBoolean("eRetorno"));
            c.setEstaAtiva(rs.getBoolean("estaAtiva"));
            c.setIdPaciente(rs.getInt("id_paciente"));
            c.setIdMedico(rs.getInt("id_medico"));
            lista.add(c);
        }
        return lista;
    }

    public void atualizar(Consulta consulta) throws SQLException {
        String sql  = "UPDATE consulta SET data_hora = ?, sintomas = ?, eRetorno = ?, estaAtiva = ?, id_paciente = ?, id_medico = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setTimestamp(1, Timestamp.valueOf(consulta.getData()));
        stmt.setString(2, consulta.getSintomas());
        stmt.setBoolean(3, consulta.iseRetorno());
        stmt.setBoolean(4, consulta.isEstaAtiva());
        stmt.setInt(5, consulta.getIdPaciente());
        stmt.setInt(6, consulta.getIdMedico());
        stmt.setInt(7, consulta.getId());
        stmt.executeUpdate();
    }

    public void deletar(long id) throws SQLException {
        String sql = "DELETE FROM consulta WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        stmt.executeUpdate();
    }
}