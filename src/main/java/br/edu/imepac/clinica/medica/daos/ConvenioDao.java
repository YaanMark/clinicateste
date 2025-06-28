package br.edu.imepac.clinica.medica.daos;

import br.edu.imepac.clinica.medica.entidades.Convenio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConvenioDao {

    private final String URL = "jdbc:mysql://localhost:3306/clinica_medica?useSSL=false&allowPublicKeyRetrieval=true";
    private final String USER = "root";
    private final String PASS = "";
    private final Connection CONN;

    public ConvenioDao() throws SQLException{
        CONN = DriverManager.getConnection(URL, USER, PASS);
    }

    public void salvar(Convenio convenio) throws SQLException{
        String sql = "INSERT INTO convenios (nome, descricao) VALUES (?,?)";
        PreparedStatement stmt = CONN.prepareStatement(sql);
        stmt.setString(1, convenio.getNome());
        stmt.setString(2, convenio.getDescricao());
        stmt.executeUpdate();
    }

    public Convenio buscar(int id) throws SQLException{
        String sql = "SELECT * FROM convenios WHERE id = ?";
        PreparedStatement stmt = CONN.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            Convenio c = new Convenio();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setDescricao(rs.getString("descricao"));
            return c;
        }
        return null;
    }

    public List<Convenio> listar() throws SQLException{
        String sql = "SELECT * FROM convenios";
        PreparedStatement stmt = CONN.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Convenio> convenios = new ArrayList<>();
        while(rs.next()){
            Convenio c = new Convenio();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setDescricao(rs.getString("descricao"));
            convenios.add(c);
        }
        return convenios;
    }

    public void atualizar(Convenio convenio) throws SQLException{
        String sql = "UPDATE convenios SET nome=?, descricao=? WHERE id=?";
        PreparedStatement stmt = CONN.prepareStatement(sql);
        stmt.setString(1, convenio.getNome());
        stmt.setString(2, convenio.getDescricao());
        stmt.setInt(3, convenio.getId());
        stmt.executeUpdate();
    }

    public void deletar(int id) throws SQLException{
        String sql = "DELETE FROM convenios WHERE id = ?";
        PreparedStatement stmt = CONN.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

}
