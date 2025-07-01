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
        String sql = "INSERT INTO paciente (nome, idade, sexo, cpf, rua, numero, complemento, bairro, cidade, estado, contato, email, data_nascimento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, paciente.getNome());
        stmt.setInt(2, paciente.getIdade());
        stmt.setString(3, String.valueOf(paciente.getSexo()));
        stmt.setString(4, paciente.getCpf());
        stmt.setString(5, paciente.getRua());
        stmt.setString(6, paciente.getNumero());
        stmt.setString(7, paciente.getComplemento());
        stmt.setString(8, paciente.getBairro());
        stmt.setString(9, paciente.getCidade());
        stmt.setString(10, paciente.getEstado());
        stmt.setString(11, paciente.getTelefone());
        stmt.setString(12, paciente.getEmail());
        stmt.setDate(13, Date.valueOf(paciente.getDataNascimento()));
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
            p.setIdade(rs.getInt("idade"));
            p.setSexo(rs.getString("sexo").charAt(0));
            p.setCpf(rs.getString("cpf"));
            p.setRua(rs.getString("rua"));
            p.setNumero(rs.getString("numero"));
            p.setComplemento(rs.getString("complemento"));
            p.setBairro(rs.getString("bairro"));
            p.setCidade(rs.getString("cidade"));
            p.setEstado(rs.getString("estado"));
            p.setTelefone(rs.getString("contato"));
            p.setEmail(rs.getString("email"));
            p.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
            return p;
        }
        return null;
    }

    public List<Paciente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM paciente";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Paciente> lista = new ArrayList<>();
        while (rs.next()) {
            Paciente p = new Paciente();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setIdade(rs.getInt("idade"));
            p.setSexo(rs.getString("sexo").charAt(0));
            p.setCpf(rs.getString("cpf"));
            p.setRua(rs.getString("rua"));
            p.setNumero(rs.getString("numero"));
            p.setComplemento(rs.getString("complemento"));
            p.setBairro(rs.getString("bairro"));
            p.setCidade(rs.getString("cidade"));
            p.setEstado(rs.getString("estado"));
            p.setTelefone(rs.getString("contato"));
            p.setEmail(rs.getString("email"));
            p.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
            lista.add(p);
        }
        return lista;
    }

    public void atualizar(Paciente paciente) throws SQLException {
        String sql  = "UPDATE paciente SET nome = ?, idade = ?, sexo = ?, cpf = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, contato = ?, email = ?, data_nascimento = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, paciente.getNome());
        stmt.setInt(2, paciente.getIdade());
        stmt.setString(3, String.valueOf(paciente.getSexo()));
        stmt.setString(4, paciente.getCpf());
        stmt.setString(5, paciente.getRua());
        stmt.setString(6, paciente.getNumero());
        stmt.setString(7, paciente.getComplemento());
        stmt.setString(8, paciente.getBairro());
        stmt.setString(9, paciente.getCidade());
        stmt.setString(10, paciente.getEstado());
        stmt.setString(11, paciente.getTelefone());
        stmt.setString(12, paciente.getEmail());
        stmt.setDate(13, Date.valueOf(paciente.getDataNascimento()));
        stmt.setInt(14, paciente.getId());
        stmt.executeUpdate();
    }

    public void deletar(long id) throws SQLException {
        String sql = "DELETE FROM paciente WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        stmt.executeUpdate();
    }
}