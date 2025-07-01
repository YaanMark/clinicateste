package br.edu.imepac.clinica.medica.daos;

import br.edu.imepac.clinica.medica.entidades.Perfil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerfilDao {

    private final String URL = "jdbc:mysql://localhost:3306/clinica_medica?useSSL=false&allowPublicKeyRetrieval=true";
    private final String USER = "root";
    private final String PASS = "";
    private final Connection CONN;

    public PerfilDao() throws SQLException {
        CONN = DriverManager.getConnection(URL, USER, PASS);
    }

    public void salvar(Perfil perfil) throws SQLException {
        String sql = "INSERT INTO perfil (nome, cadastrar_funcionario, atualizar_funcionario, deletar_funcionario, listar_funcionario, " +
                "cadastrar_paciente, atualizar_paciente, deletar_paciente, listar_paciente, cadastrar_consulta, atualizar_consulta, " +
                "deletar_consulta, listar_consulta, cadastrar_especialidade, atualizar_especialidade, deletar_especialidade, " +
                "listar_especialidade, cadastrar_convenio, atualizar_convenio, deletar_convenio, listar_convenio, " +
                "cadastrar_prontuario, atualizar_prontuario, deletar_prontuario, listar_prontuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = CONN.prepareStatement(sql);
        stmt.setString(1, perfil.getNome());
        stmt.setBoolean(2, perfil.isCadastrarFuncionario());
        stmt.setBoolean(3, perfil.isAtualizarFuncionario());
        stmt.setBoolean(4, perfil.isDeletarFuncionario());
        stmt.setBoolean(5, perfil.isListarFuncionario());

        stmt.setBoolean(6, perfil.isCadastrarPaciente());
        stmt.setBoolean(7, perfil.isAtualizarPaciente());
        stmt.setBoolean(8, perfil.isDeletarPaciente());
        stmt.setBoolean(9, perfil.isListarPaciente());

        stmt.setBoolean(10, perfil.isCadastrarConsulta());
        stmt.setBoolean(11, perfil.isAtualizarConsulta());
        stmt.setBoolean(12, perfil.isDeletarConsulta());
        stmt.setBoolean(13, perfil.isListarConsulta());

        stmt.setBoolean(14, perfil.isCadastrarEspecialidade());
        stmt.setBoolean(15, perfil.isAtualizarEspecialidade());
        stmt.setBoolean(16, perfil.isDeletarEspecialidade());
        stmt.setBoolean(17, perfil.isListarEspecialidade());

        stmt.setBoolean(18, perfil.isCadastrarConvenio());
        stmt.setBoolean(19, perfil.isAtualizarConvenio());
        stmt.setBoolean(20, perfil.isDeletarConvenio());
        stmt.setBoolean(21, perfil.isListarConvenio());

        stmt.setBoolean(22, perfil.isCadastrarProntuario());
        stmt.setBoolean(23, perfil.isAtualizarProntuario());
        stmt.setBoolean(24, perfil.isDeletarProntuario());
        stmt.setBoolean(25, perfil.isListarProntuario());
        stmt.executeUpdate();
    }

    public Perfil buscar(int id) throws SQLException {
        String sql = "SELECT * FROM perfil WHERE id = ?";
        PreparedStatement stmt = CONN.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Perfil p = new Perfil();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setCadastrarFuncionario(rs.getBoolean("cadastrar_funcionario"));
            p.setAtualizarFuncionario(rs.getBoolean("atualizar_funcionario"));
            p.setDeletarFuncionario(rs.getBoolean("deletar_funcionario"));
            p.setListarFuncionario(rs.getBoolean("listar_funcionario"));

            p.setCadastrarPaciente(rs.getBoolean("cadastrar_paciente"));
            p.setAtualizarPaciente(rs.getBoolean("atualizar_paciente"));
            p.setDeletarPaciente(rs.getBoolean("deletar_paciente"));
            p.setListarPaciente(rs.getBoolean("listar_paciente"));

            p.setCadastrarConsulta(rs.getBoolean("cadastrar_consulta"));
            p.setAtualizarConsulta(rs.getBoolean("atualizar_consulta"));
            p.setDeletarConsulta(rs.getBoolean("deletar_consulta"));
            p.setListarConsulta(rs.getBoolean("listar_consulta"));

            p.setCadastrarEspecialidade(rs.getBoolean("cadastrar_especialidade"));
            p.setAtualizarEspecialidade(rs.getBoolean("atualizar_especialidade"));
            p.setDeletarEspecialidade(rs.getBoolean("deletar_especialidade"));
            p.setListarEspecialidade(rs.getBoolean("listar_especialidade"));

            p.setCadastrarConvenio(rs.getBoolean("cadastrar_convenio"));
            p.setAtualizarConvenio(rs.getBoolean("atualizar_convenio"));
            p.setDeletarConvenio(rs.getBoolean("deletar_convenio"));
            p.setListarConvenio(rs.getBoolean("listar_convenio"));

            p.setCadastrarProntuario(rs.getBoolean("cadastrar_prontuario"));
            p.setAtualizarProntuario(rs.getBoolean("atualizar_prontuario"));
            p.setDeletarProntuario(rs.getBoolean("deletar_prontuario"));
            p.setListarProntuario(rs.getBoolean("listar_prontuario"));
            return p;
        }
        return null;
    }

    public List<Perfil> listar() throws SQLException {
        String sql = "SELECT * FROM perfil";
        PreparedStatement stmt = CONN.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Perfil> perfis = new ArrayList<>();
        while (rs.next()) {
            Perfil p = new Perfil();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setCadastrarFuncionario(rs.getBoolean("cadastrar_funcionario"));
            p.setAtualizarFuncionario(rs.getBoolean("atualizar_funcionario"));
            p.setDeletarFuncionario(rs.getBoolean("deletar_funcionario"));
            p.setListarFuncionario(rs.getBoolean("listar_funcionario"));

            p.setCadastrarPaciente(rs.getBoolean("cadastrar_paciente"));
            p.setAtualizarPaciente(rs.getBoolean("atualizar_paciente"));
            p.setDeletarPaciente(rs.getBoolean("deletar_paciente"));
            p.setListarPaciente(rs.getBoolean("listar_paciente"));

            p.setCadastrarConsulta(rs.getBoolean("cadastrar_consulta"));
            p.setAtualizarConsulta(rs.getBoolean("atualizar_consulta"));
            p.setDeletarConsulta(rs.getBoolean("deletar_consulta"));
            p.setListarConsulta(rs.getBoolean("listar_consulta"));

            p.setCadastrarEspecialidade(rs.getBoolean("cadastrar_especialidade"));
            p.setAtualizarEspecialidade(rs.getBoolean("atualizar_especialidade"));
            p.setDeletarEspecialidade(rs.getBoolean("deletar_especialidade"));
            p.setListarEspecialidade(rs.getBoolean("listar_especialidade"));

            p.setCadastrarConvenio(rs.getBoolean("cadastrar_convenio"));
            p.setAtualizarConvenio(rs.getBoolean("atualizar_convenio"));
            p.setDeletarConvenio(rs.getBoolean("deletar_convenio"));
            p.setListarConvenio(rs.getBoolean("listar_convenio"));

            p.setCadastrarProntuario(rs.getBoolean("cadastrar_prontuario"));
            p.setAtualizarProntuario(rs.getBoolean("atualizar_prontuario"));
            p.setDeletarProntuario(rs.getBoolean("deletar_prontuario"));
            p.setListarProntuario(rs.getBoolean("listar_prontuario"));
            perfis.add(p);
        }
        return perfis;
    }

    public void atualizar(Perfil perfil) throws SQLException {
        String sql = "UPDATE perfil SET nome=?, cadastrar_funcionario=?, atualizar_funcionario=?, deletar_funcionario=?, listar_funcionario=?, " +
                "cadastrar_paciente=?, atualizar_paciente=?, deletar_paciente=?, listar_paciente=?, cadastrar_consulta=?, atualizar_consulta=?, " +
                "deletar_consulta=?, listar_consulta=?, cadastrar_especialidade=?, atualizar_especialidade=?, deletar_especialidade=?, " +
                "listar_especialidade=?, cadastrar_convenio=?, atualizar_convenio=?, deletar_convenio=?, listar_convenio=?, " +
                "cadastrar_prontuario=?, atualizar_prontuario=?, deletar_prontuario=?, listar_prontuario=? WHERE id=?";
        PreparedStatement stmt = CONN.prepareStatement(sql);
        stmt.setString(1, perfil.getNome());
        stmt.setBoolean(2, perfil.isCadastrarFuncionario());
        stmt.setBoolean(3, perfil.isAtualizarFuncionario());
        stmt.setBoolean(4, perfil.isDeletarFuncionario());
        stmt.setBoolean(5, perfil.isListarFuncionario());

        stmt.setBoolean(6, perfil.isCadastrarPaciente());
        stmt.setBoolean(7, perfil.isAtualizarPaciente());
        stmt.setBoolean(8, perfil.isDeletarPaciente());
        stmt.setBoolean(9, perfil.isListarPaciente());

        stmt.setBoolean(10, perfil.isCadastrarConsulta());
        stmt.setBoolean(11, perfil.isAtualizarConsulta());
        stmt.setBoolean(12, perfil.isDeletarConsulta());
        stmt.setBoolean(13, perfil.isListarConsulta());

        stmt.setBoolean(14, perfil.isCadastrarEspecialidade());
        stmt.setBoolean(15, perfil.isAtualizarEspecialidade());
        stmt.setBoolean(16, perfil.isDeletarEspecialidade());
        stmt.setBoolean(17, perfil.isListarEspecialidade());

        stmt.setBoolean(18, perfil.isCadastrarConvenio());
        stmt.setBoolean(19, perfil.isAtualizarConvenio());
        stmt.setBoolean(20, perfil.isDeletarConvenio());
        stmt.setBoolean(21, perfil.isListarConvenio());

        stmt.setBoolean(22, perfil.isCadastrarProntuario());
        stmt.setBoolean(23, perfil.isAtualizarProntuario());
        stmt.setBoolean(24, perfil.isDeletarProntuario());
        stmt.setBoolean(25, perfil.isListarProntuario());

        stmt.setInt(26, perfil.getId());
        stmt.executeUpdate();
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM perfil WHERE id = ?";
        PreparedStatement stmt = CONN.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}