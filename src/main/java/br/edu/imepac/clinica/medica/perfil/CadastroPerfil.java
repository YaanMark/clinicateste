package br.edu.imepac.clinica.medica.perfil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import br.edu.imepac.clinica.medica.daos.PerfilDao;
import br.edu.imepac.clinica.medica.entidades.Perfil;
import br.edu.imepac.clinica.medica.outros.Estilo;

public class CadastroPerfil extends JFrame {

    private JTextField campoNome;
    private JCheckBox checkCadastrarFuncionario;
    private JCheckBox checkAtualizarFuncionario;
    private JCheckBox checkDeletarFuncionario;
    private JCheckBox checkListarFuncionario;
    private JCheckBox checkCadastrarPaciente;
    private JCheckBox checkAtualizarPaciente;
    private JCheckBox checkDeletarPaciente;
    private JCheckBox checkListarPaciente;
    private JCheckBox checkCadastrarConsulta;
    private JCheckBox checkAtualizarConsulta;
    private JCheckBox checkDeletarConsulta;
    private JCheckBox checkListarConsulta;
    private JCheckBox checkCadastrarEspecialidade;
    private JCheckBox checkAtualizarEspecialidade;
    private JCheckBox checkDeletarEspecialidade;
    private JCheckBox checkListarEspecialidade;
    private JCheckBox checkCadastrarConvenio;
    private JCheckBox checkAtualizarConvenio;
    private JCheckBox checkDeletarConvenio;
    private JCheckBox checkListarConvenio;
    private JCheckBox checkCadastrarProntuario;
    private JCheckBox checkAtualizarProntuario;
    private JCheckBox checkDeletarProntuario;
    private JCheckBox checkListarProntuario;

    private JButton botaoSalvar;
    private JButton botaoFechar;

    private PerfilDao perfilDao;
    private JFrame parentFrame;

    public CadastroPerfil(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        inicializarPerfilDao();

        setTitle("Cadastro de Perfis");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        JLabel labelNome = new JLabel("Nome do Perfil:");
        labelNome.setForeground(Estilo.COR_TEXTO);
        labelNome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelNome, gbc);

        campoNome = new JTextField(40);
        campoNome.setBackground(Estilo.COR_BOTOES);
        campoNome.setForeground(Estilo.COR_TEXTO);
        campoNome.setCaretColor(Estilo.COR_TEXTO);
        campoNome.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campoNome.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoNome.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                campoNome.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 3;
        panel.add(campoNome, gbc);

        gbc.gridwidth = 1;

        JLabel labelFuncionario = new JLabel("<html><b>Funcionário</b></html>");
        labelFuncionario.setForeground(Estilo.COR_TEXTO);
        labelFuncionario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(labelFuncionario, gbc);

        checkCadastrarFuncionario = new JCheckBox("Cadastrar Funcionário");
        estilizarCheckBox(checkCadastrarFuncionario);
        gbc.gridx = 0;
        gbc.gridy = ++row;
        panel.add(checkCadastrarFuncionario, gbc);

        checkAtualizarFuncionario = new JCheckBox("Atualizar Funcionário");
        estilizarCheckBox(checkAtualizarFuncionario);
        gbc.gridx = 0;
        gbc.gridy = ++row;
        panel.add(checkAtualizarFuncionario, gbc);

        checkDeletarFuncionario = new JCheckBox("Deletar Funcionário");
        estilizarCheckBox(checkDeletarFuncionario);
        gbc.gridx = 0;
        gbc.gridy = ++row;
        panel.add(checkDeletarFuncionario, gbc);

        checkListarFuncionario = new JCheckBox("Listar Funcionário");
        estilizarCheckBox(checkListarFuncionario);
        gbc.gridx = 0;
        gbc.gridy = ++row;
        panel.add(checkListarFuncionario, gbc);

        int initialRowForPatient = row - 4;
        JLabel labelPaciente = new JLabel("<html><b>Paciente</b></html>");
        labelPaciente.setForeground(Estilo.COR_TEXTO);
        labelPaciente.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.gridy = initialRowForPatient;
        panel.add(labelPaciente, gbc);

        checkCadastrarPaciente = new JCheckBox("Cadastrar Paciente");
        estilizarCheckBox(checkCadastrarPaciente);
        gbc.gridx = 1;
        gbc.gridy = ++initialRowForPatient;
        panel.add(checkCadastrarPaciente, gbc);

        checkAtualizarPaciente = new JCheckBox("Atualizar Paciente");
        estilizarCheckBox(checkAtualizarPaciente);
        gbc.gridx = 1;
        gbc.gridy = ++initialRowForPatient;
        panel.add(checkAtualizarPaciente, gbc);

        checkDeletarPaciente = new JCheckBox("Deletar Paciente");
        estilizarCheckBox(checkDeletarPaciente);
        gbc.gridx = 1;
        gbc.gridy = ++initialRowForPatient;
        panel.add(checkDeletarPaciente, gbc);

        checkListarPaciente = new JCheckBox("Listar Paciente");
        estilizarCheckBox(checkListarPaciente);
        gbc.gridx = 1;
        gbc.gridy = ++initialRowForPatient;
        panel.add(checkListarPaciente, gbc);

        int initialRowForConsulta = row - 4;
        JLabel labelConsulta = new JLabel("<html><b>Consulta</b></html>");
        labelConsulta.setForeground(Estilo.COR_TEXTO);
        labelConsulta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridy = initialRowForConsulta;
        panel.add(labelConsulta, gbc);

        checkCadastrarConsulta = new JCheckBox("Cadastrar Consulta");
        estilizarCheckBox(checkCadastrarConsulta);
        gbc.gridx = 2;
        gbc.gridy = ++initialRowForConsulta;
        panel.add(checkCadastrarConsulta, gbc);

        checkAtualizarConsulta = new JCheckBox("Atualizar Consulta");
        estilizarCheckBox(checkAtualizarConsulta);
        gbc.gridx = 2;
        gbc.gridy = ++initialRowForConsulta;
        panel.add(checkAtualizarConsulta, gbc);

        checkDeletarConsulta = new JCheckBox("Deletar Consulta");
        estilizarCheckBox(checkDeletarConsulta);
        gbc.gridx = 2;
        gbc.gridy = ++initialRowForConsulta;
        panel.add(checkDeletarConsulta, gbc);

        checkListarConsulta = new JCheckBox("Listar Consulta");
        estilizarCheckBox(checkListarConsulta);
        gbc.gridx = 2;
        gbc.gridy = ++initialRowForConsulta;
        panel.add(checkListarConsulta, gbc);

        row = Math.max(row, initialRowForPatient);
        row = Math.max(row, initialRowForConsulta) + 1;

        JLabel labelEspecialidade = new JLabel("<html><b>Especialidade</b></html>");
        labelEspecialidade.setForeground(Estilo.COR_TEXTO);
        labelEspecialidade.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(labelEspecialidade, gbc);

        checkCadastrarEspecialidade = new JCheckBox("Cadastrar Especialidade");
        estilizarCheckBox(checkCadastrarEspecialidade);
        gbc.gridx = 0;
        gbc.gridy = ++row;
        panel.add(checkCadastrarEspecialidade, gbc);

        checkAtualizarEspecialidade = new JCheckBox("Atualizar Especialidade");
        estilizarCheckBox(checkAtualizarEspecialidade);
        gbc.gridx = 0;
        gbc.gridy = ++row;
        panel.add(checkAtualizarEspecialidade, gbc);

        checkDeletarEspecialidade = new JCheckBox("Deletar Especialidade");
        estilizarCheckBox(checkDeletarEspecialidade);
        gbc.gridx = 0;
        gbc.gridy = ++row;
        panel.add(checkDeletarEspecialidade, gbc);

        checkListarEspecialidade = new JCheckBox("Listar Especialidade");
        estilizarCheckBox(checkListarEspecialidade);
        gbc.gridx = 0;
        gbc.gridy = ++row;
        panel.add(checkListarEspecialidade, gbc);

        int initialRowForConvenio = row - 4;
        JLabel labelConvenio = new JLabel("<html><b>Convênio</b></html>");
        labelConvenio.setForeground(Estilo.COR_TEXTO);
        labelConvenio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.gridy = initialRowForConvenio;
        panel.add(labelConvenio, gbc);

        checkCadastrarConvenio = new JCheckBox("Cadastrar Convênio");
        estilizarCheckBox(checkCadastrarConvenio);
        gbc.gridx = 1;
        gbc.gridy = ++initialRowForConvenio;
        panel.add(checkCadastrarConvenio, gbc);

        checkAtualizarConvenio = new JCheckBox("Atualizar Convênio");
        estilizarCheckBox(checkAtualizarConvenio);
        gbc.gridx = 1;
        gbc.gridy = ++initialRowForConvenio;
        panel.add(checkAtualizarConvenio, gbc);

        checkDeletarConvenio = new JCheckBox("Deletar Convênio");
        estilizarCheckBox(checkDeletarConvenio);
        gbc.gridx = 1;
        gbc.gridy = ++initialRowForConvenio;
        panel.add(checkDeletarConvenio, gbc);

        checkListarConvenio = new JCheckBox("Listar Convênio");
        estilizarCheckBox(checkListarConvenio);
        gbc.gridx = 1;
        gbc.gridy = ++initialRowForConvenio;
        panel.add(checkListarConvenio, gbc);

        int initialRowForProntuario = row - 4;
        JLabel labelProntuario = new JLabel("<html><b>Prontuário</b></html>");
        labelProntuario.setForeground(Estilo.COR_TEXTO);
        labelProntuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridy = initialRowForProntuario;
        panel.add(labelProntuario, gbc);

        checkCadastrarProntuario = new JCheckBox("Cadastrar Prontuário");
        estilizarCheckBox(checkCadastrarProntuario);
        gbc.gridx = 2;
        gbc.gridy = ++initialRowForProntuario;
        panel.add(checkCadastrarProntuario, gbc);

        checkAtualizarProntuario = new JCheckBox("Atualizar Prontuário");
        estilizarCheckBox(checkAtualizarProntuario);
        gbc.gridx = 2;
        gbc.gridy = ++initialRowForProntuario;
        panel.add(checkAtualizarProntuario, gbc);

        checkDeletarProntuario = new JCheckBox("Deletar Prontuário");
        estilizarCheckBox(checkDeletarProntuario);
        gbc.gridx = 2;
        gbc.gridy = ++initialRowForProntuario;
        panel.add(checkDeletarProntuario, gbc);

        checkListarProntuario = new JCheckBox("Listar Prontuário");
        estilizarCheckBox(checkListarProntuario);
        gbc.gridx = 2;
        gbc.gridy = ++initialRowForProntuario;
        panel.add(checkListarProntuario, gbc);

        row = Math.max(row, initialRowForConvenio);
        row = Math.max(row, initialRowForProntuario) + 1;

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Estilo.COR_FUNDO);

        botaoSalvar = new JButton("Salvar");
        Estilo.estilizarBotao(botaoSalvar);
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoBotaoSalvar(e);
            }
        });
        buttonPanel.add(botaoSalvar);

        botaoFechar = new JButton("Fechar");
        Estilo.estilizarBotao(botaoFechar);
        botaoFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoBotaoFechar();
            }
        });
        buttonPanel.add(botaoFechar);

        panel.add(buttonPanel, gbc);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadastroPerfil(null).setVisible(true);
            }
        });
    }

    private void acaoBotaoSalvar(ActionEvent e) {
        if (validarDadosObrigatorios()) {
            Perfil perfil = new Perfil();
            perfil.setNome(campoNome.getText());
            perfil.setCadastrarFuncionario(checkCadastrarFuncionario.isSelected());
            perfil.setAtualizarFuncionario(checkAtualizarFuncionario.isSelected());
            perfil.setDeletarFuncionario(checkDeletarFuncionario.isSelected());
            perfil.setListarFuncionario(checkListarFuncionario.isSelected());
            perfil.setCadastrarPaciente(checkCadastrarPaciente.isSelected());
            perfil.setAtualizarPaciente(checkAtualizarPaciente.isSelected());
            perfil.setDeletarPaciente(checkDeletarPaciente.isSelected());
            perfil.setListarPaciente(checkListarPaciente.isSelected());
            perfil.setCadastrarConsulta(checkCadastrarConsulta.isSelected());
            perfil.setAtualizarConsulta(checkAtualizarConsulta.isSelected());
            perfil.setDeletarConsulta(checkDeletarConsulta.isSelected());
            perfil.setListarConsulta(checkListarConsulta.isSelected());
            perfil.setCadastrarEspecialidade(checkCadastrarEspecialidade.isSelected());
            perfil.setAtualizarEspecialidade(checkAtualizarEspecialidade.isSelected());
            perfil.setDeletarEspecialidade(checkDeletarEspecialidade.isSelected());
            perfil.setListarEspecialidade(checkListarEspecialidade.isSelected());
            perfil.setCadastrarConvenio(checkCadastrarConvenio.isSelected());
            perfil.setAtualizarConvenio(checkAtualizarConvenio.isSelected());
            perfil.setDeletarConvenio(checkDeletarConvenio.isSelected());
            perfil.setListarConvenio(checkListarConvenio.isSelected());
            perfil.setCadastrarProntuario(checkCadastrarProntuario.isSelected());
            perfil.setAtualizarProntuario(checkAtualizarProntuario.isSelected());
            perfil.setDeletarProntuario(checkDeletarProntuario.isSelected());
            perfil.setListarProntuario(checkListarProntuario.isSelected());

            try {
                perfilDao.salvar(perfil);
                JOptionPane.showMessageDialog(null, "Perfil cadastrado com sucesso!");
                limparCampos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar perfil: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome do perfil.");
        }
    }

    private void acaoBotaoFechar() {
        dispose();
        if (parentFrame != null) {
            parentFrame.setVisible(true);
        }
    }

    private boolean validarDadosObrigatorios() {
        return !campoNome.getText().trim().isEmpty();
    }

    private void inicializarPerfilDao() {
        try {
            this.perfilDao = new PerfilDao();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados - perfil!");
            dispose();
        }
    }

    private void estilizarCheckBox(JCheckBox checkBox) {
        checkBox.setBackground(Estilo.COR_FUNDO);
        checkBox.setForeground(Estilo.COR_TEXTO);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkBox.setFocusPainted(false);
    }

    private void limparCampos() {
        campoNome.setText("");
        checkCadastrarFuncionario.setSelected(false);
        checkAtualizarFuncionario.setSelected(false);
        checkDeletarFuncionario.setSelected(false);
        checkListarFuncionario.setSelected(false);
        checkCadastrarPaciente.setSelected(false);
        checkAtualizarPaciente.setSelected(false);
        checkDeletarPaciente.setSelected(false);
        checkListarPaciente.setSelected(false);
        checkCadastrarConsulta.setSelected(false);
        checkAtualizarConsulta.setSelected(false);
        checkDeletarConsulta.setSelected(false);
        checkListarConsulta.setSelected(false);
        checkCadastrarEspecialidade.setSelected(false);
        checkAtualizarEspecialidade.setSelected(false);
        checkDeletarEspecialidade.setSelected(false);
        checkListarEspecialidade.setSelected(false);
        checkCadastrarConvenio.setSelected(false);
        checkAtualizarConvenio.setSelected(false);
        checkDeletarConvenio.setSelected(false);
        checkListarConvenio.setSelected(false);
        checkCadastrarProntuario.setSelected(false);
        checkAtualizarProntuario.setSelected(false);
        checkDeletarProntuario.setSelected(false);
        checkListarProntuario.setSelected(false);
    }
}