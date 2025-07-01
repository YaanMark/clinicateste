package br.edu.imepac.clinica.medica.daos;

import br.edu.imepac.clinica.medica.entidades.Funcionarios;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionariosDao {
    private final String url = "jdbc:mysql://localhost:3306/clinica_medica?useSSL=false&allowPublicKeyRetrieval=true";
    private final String usuario = "root";
    private final String senha = "";
    private final Connection connection;

    public FuncionariosDao() throws SQLException {
        connection = DriverManager.getConnection(url, usuario, senha);
    }

    public void salvar(Funcionarios funcionarios) throws SQLException {
        String sql = "INSERT INTO funcionario (usuario, nome, idade, sexo, cpf, rua, bairro, cidade, estado, contato, email, dataNascimento, id_perfil, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, funcionarios.getUsuario());
        stmt.setString(2, funcionarios.getNome());
        stmt.setString(3, funcionarios.getIdade());
        stmt.setString(4, funcionarios.getSexo());
        stmt.setString(5, funcionarios.getCpf());
        stmt.setString(6, funcionarios.getRua());
        stmt.setString(7, funcionarios.getBairro());
        stmt.setString(8, funcionarios.getCidade());
        stmt.setString(9, funcionarios.getEstado());
        stmt.setString(10, funcionarios.getContato());
        stmt.setString(11, funcionarios.getEmail());
        stmt.setDate(12, Date.valueOf(funcionarios.getDataNascimento()));
        stmt.setInt(13, funcionarios.getIdPerfil());
        stmt.setString(14, funcionarios.getSenha());
        stmt.executeUpdate();

    }

    public Funcionarios buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM funcionario WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Funcionarios f = new Funcionarios();
            f.setId(rs.getInt("id"));
            f.setUsuario(rs.getString("usuario"));
            f.setNome(rs.getString("nome"));
            f.setIdade(rs.getString("idade"));
            f.setSexo(rs.getString("sexo"));
            f.setCpf(rs.getString("cpf"));
            f.setRua(rs.getString("rua"));
            f.setBairro(rs.getString("bairro"));
            f.setCidade(rs.getString("cidade"));
            f.setEstado(rs.getString("estado"));
            f.setContato(rs.getString("contato"));
            f.setEmail(rs.getString("email"));
            f.setDataNascimento(LocalDate.parse(rs.getString("dataNascimento")));
            f.setIdPerfil(rs.getInt("id_perfil"));
            f.setSenha(rs.getString("senha").toCharArray());

            return f;
        }
        return null;
    }

    public List<Funcionarios> listarTodos() throws SQLException {
        String sql = "SELECT * FROM funcionario";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Funcionarios> lista = new ArrayList<>();
        while (rs.next()) {
            Funcionarios f = new Funcionarios();
            f.setId(rs.getInt("id"));
            f.setUsuario(rs.getString("usuario"));
            f.setNome(rs.getString("nome"));
            f.setIdade(rs.getString("idade"));
            f.setSexo(rs.getString("sexo"));
            f.setCpf(rs.getString("cpf"));
            f.setRua(rs.getString("rua"));
            f.setBairro(rs.getString("bairro"));
            f.setCidade(rs.getString("cidade"));
            f.setEstado(rs.getString("estado"));
            f.setContato(rs.getString("contato"));
            f.setEmail(rs.getString("email"));
            f.setDataNascimento(LocalDate.parse(rs.getString("datanascimento")));
            f.setIdPerfil(rs.getInt("id_perfil"));
            f.setSenha(rs.getString("senha"));

            lista.add(f);
        }
        return lista;
    }

    public void atualizar(Funcionarios funcionarios) throws SQLException {
        String sql = "UPDATE funcionario SET usuario = ?, nome = ?, idade = ?, sexo = ?, cpf = ?, rua = ?, bairro = ?, cidade = ?, estado = ?, contato = ?, email = ?, dataNascimento = ?, id_perfil = ?, senha = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, funcionarios.getUsuario());
        stmt.setString(2, funcionarios.getNome());
        stmt.setString(3, funcionarios.getIdade());
        stmt.setString(4, funcionarios.getSexo());
        stmt.setString(5, funcionarios.getCpf());
        stmt.setString(6, funcionarios.getRua());
        stmt.setString(7, funcionarios.getBairro());
        stmt.setString(8, funcionarios.getCidade());
        stmt.setString(9, funcionarios.getEstado());
        stmt.setString(10, funcionarios.getContato());
        stmt.setString(11, funcionarios.getEmail());
        stmt.setDate(12, Date.valueOf(funcionarios.getDataNascimento()));
        stmt.setInt(13, funcionarios.getIdPerfil());
        stmt.setString(14, funcionarios.getSenha());
        stmt.setInt(15, funcionarios.getId());
        stmt.executeUpdate();
    }
}