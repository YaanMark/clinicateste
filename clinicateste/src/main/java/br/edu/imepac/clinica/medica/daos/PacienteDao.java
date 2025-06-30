package br.edu.imepac.clinica.medica.daos;

import br.edu.imepac.clinica.medica.entidades.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteDao {
    private final String url = "jdbc:mysql://localhost:3306/clinica_medica?useSSL=false&allowPublicKeyRetrieval=true";
    private final String usuario = "root";
    private final String senha = "";
    private final Connection connection;

    public PacienteDao() throws SQLException {
        connection = DriverManager.getConnection(url, usuario, senha);
    }

    public void salvar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO Paciente (usuario, nome, idade, sexo, cpf, rua, numero, complemento, bairro, cidade, estado, contato, email, dataNascimentp) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, Paciente.getUsuario());
        stmt.executeUpdate();
    }

    public Paciente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM paciente WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Paciente p = new Paciente();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setCpf(rs.getString("Cpf"));

            return p;
        }
        return null;
    }
}
