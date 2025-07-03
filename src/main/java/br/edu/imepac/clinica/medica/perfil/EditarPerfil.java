package br.edu.imepac.clinica.medica.perfil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import br.edu.imepac.clinica.medica.daos.PerfilDao;
import br.edu.imepac.clinica.medica.entidades.Perfil;
import br.edu.imepac.clinica.medica.outros.Estilo;

public class EditarPerfil extends JFrame {

    private JTextField campoId;
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

    private JButton botaoBuscar;
    private JButton botaoSalvar;
    private JButton botaoFechar;

    private PerfilDao perfilDao;
    private Perfil perfilAtual;
    private JFrame parentFrame;

    public EditarPerfil(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        inicializarPerfilDao();

        setTitle("Editar Perfil");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        JLabel labelId = createLabel("ID do Perfil:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelId, gbc);

        campoId = new JTextField(20);
        estilizarCampoTexto(campoId);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(campoId, gbc);

        botaoBuscar = new JButton("Buscar");
        Estilo.estilizarBotao(botaoBuscar);
        botaoBuscar.addActionListener(e -> acaoBotaoBuscar());
        gbc.gridx = 2;
        gbc.gridy = row++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(botaoBuscar, gbc);

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelNome = createLabel("Nome do Perfil:");
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelNome, gbc);

        campoNome = new JTextField(40);
        estilizarCampoTexto(campoNome);
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(campoNome, gbc);

        gbc.gridwidth = 1;

        JLabel labelFuncionario = createLabel("<html><b>Funcionário</b></html>");
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
        JLabel labelPaciente = createLabel("<html><b>Paciente</b></html>");
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
        JLabel labelConsulta = createLabel("<html><b>Consulta</b></html>");
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

        JLabel labelEspecialidade = createLabel("<html><b>Especialidade</b></html>");
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
        JLabel labelConvenio = createLabel("<html><b>Convênio</b></html>");
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
        JLabel labelProntuario = createLabel("<html><b>Prontuário</b></html>");
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
        botaoSalvar.addActionListener(e -> acaoBotaoSalvar(e));
        buttonPanel.add(botaoSalvar);

        botaoFechar = new JButton("Fechar");
        Estilo.estilizarBotao(botaoFechar);
        botaoFechar.addActionListener(e -> acaoBotaoFechar());
        buttonPanel.add(botaoFechar);

        panel.add(buttonPanel, gbc);

        add(panel);
        toggleCamposEdicao(false);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Estilo.COR_TEXTO);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private void estilizarCampoTexto(JTextField comp) {
        comp.setBackground(Estilo.COR_BOTOES);
        comp.setForeground(Estilo.COR_TEXTO);
        comp.setCaretColor(Estilo.COR_TEXTO);
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comp.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));

        comp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comp.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                comp.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
    }

    private void estilizarCheckBox(JCheckBox checkBox) {
        checkBox.setBackground(Estilo.COR_FUNDO);
        checkBox.setForeground(Estilo.COR_TEXTO);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkBox.setFocusPainted(false);
    }

    private void inicializarPerfilDao() {
        try {
            this.perfilDao = new PerfilDao();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados - perfil!");
            dispose();
        }
    }

    private void toggleCamposEdicao(boolean enable) {
        campoNome.setEnabled(enable);
        checkCadastrarFuncionario.setEnabled(enable);
        checkAtualizarFuncionario.setEnabled(enable);
        checkDeletarFuncionario.setEnabled(enable);
        checkListarFuncionario.setEnabled(enable);
        checkCadastrarPaciente.setEnabled(enable);
        checkAtualizarPaciente.setEnabled(enable);
        checkDeletarPaciente.setEnabled(enable);
        checkListarPaciente.setEnabled(enable);
        checkCadastrarConsulta.setEnabled(enable);
        checkAtualizarConsulta.setEnabled(enable);
        checkDeletarConsulta.setEnabled(enable);
        checkListarConsulta.setEnabled(enable);
        checkCadastrarEspecialidade.setEnabled(enable);
        checkAtualizarEspecialidade.setEnabled(enable);
        checkDeletarEspecialidade.setEnabled(enable);
        checkListarEspecialidade.setEnabled(enable);
        checkCadastrarConvenio.setEnabled(enable);
        checkAtualizarConvenio.setEnabled(enable);
        checkDeletarConvenio.setEnabled(enable);
        checkListarConvenio.setEnabled(enable);
        checkCadastrarProntuario.setEnabled(enable);
        checkAtualizarProntuario.setEnabled(enable);
        checkDeletarProntuario.setEnabled(enable);
        checkListarProntuario.setEnabled(enable);
        botaoSalvar.setEnabled(enable);
    }

    private void clearFields() {
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
        perfilAtual = null;
    }

    private void acaoBotaoBuscar() {
        if (campoId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite o ID do perfil para buscar.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(campoId.getText().trim());
            perfilAtual = perfilDao.buscar(id);
            if (perfilAtual != null) {
                campoNome.setText(perfilAtual.getNome());
                checkCadastrarFuncionario.setSelected(perfilAtual.isCadastrarFuncionario());
                checkAtualizarFuncionario.setSelected(perfilAtual.isAtualizarFuncionario());
                checkDeletarFuncionario.setSelected(perfilAtual.isDeletarFuncionario());
                checkListarFuncionario.setSelected(perfilAtual.isListarFuncionario());
                checkCadastrarPaciente.setSelected(perfilAtual.isCadastrarPaciente());
                checkAtualizarPaciente.setSelected(perfilAtual.isAtualizarPaciente());
                checkDeletarPaciente.setSelected(perfilAtual.isDeletarPaciente());
                checkListarPaciente.setSelected(perfilAtual.isListarPaciente());
                checkCadastrarConsulta.setSelected(perfilAtual.isCadastrarConsulta());
                checkAtualizarConsulta.setSelected(perfilAtual.isAtualizarConsulta());
                checkDeletarConsulta.setSelected(perfilAtual.isDeletarConsulta());
                checkListarConsulta.setSelected(perfilAtual.isListarConsulta());
                checkCadastrarEspecialidade.setSelected(perfilAtual.isCadastrarEspecialidade());
                checkAtualizarEspecialidade.setSelected(perfilAtual.isAtualizarEspecialidade());
                checkDeletarEspecialidade.setSelected(perfilAtual.isDeletarEspecialidade());
                checkListarEspecialidade.setSelected(perfilAtual.isListarEspecialidade());
                checkCadastrarConvenio.setSelected(perfilAtual.isCadastrarConvenio());
                checkAtualizarConvenio.setSelected(perfilAtual.isAtualizarConvenio());
                checkDeletarConvenio.setSelected(perfilAtual.isDeletarConvenio());
                checkListarConvenio.setSelected(perfilAtual.isListarConvenio());
                checkCadastrarProntuario.setSelected(perfilAtual.isCadastrarProntuario());
                checkAtualizarProntuario.setSelected(perfilAtual.isAtualizarProntuario());
                checkDeletarProntuario.setSelected(perfilAtual.isDeletarProntuario());
                checkListarProntuario.setSelected(perfilAtual.isListarProntuario());

                toggleCamposEdicao(true);
                JOptionPane.showMessageDialog(this, "Perfil encontrado! Você pode editar os dados.");
            } else {
                JOptionPane.showMessageDialog(this, "Perfil com ID " + id + " não encontrado.", "Não Encontrado", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                toggleCamposEdicao(false);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            clearFields();
            toggleCamposEdicao(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar perfil: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            clearFields();
            toggleCamposEdicao(false);
        }
    }

    private boolean validarDadosObrigatorios() {
        return !campoNome.getText().trim().isEmpty();
    }

    private void acaoBotaoSalvar(ActionEvent e) {
        if (perfilAtual == null) {
            JOptionPane.showMessageDialog(this, "Nenhum perfil carregado para edição.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validarDadosObrigatorios()) {
            perfilAtual.setNome(campoNome.getText());
            perfilAtual.setCadastrarFuncionario(checkCadastrarFuncionario.isSelected());
            perfilAtual.setAtualizarFuncionario(checkAtualizarFuncionario.isSelected());
            perfilAtual.setDeletarFuncionario(checkDeletarFuncionario.isSelected());
            perfilAtual.setListarFuncionario(checkListarFuncionario.isSelected());
            perfilAtual.setCadastrarPaciente(checkCadastrarPaciente.isSelected());
            perfilAtual.setAtualizarPaciente(checkAtualizarPaciente.isSelected());
            perfilAtual.setDeletarPaciente(checkDeletarPaciente.isSelected());
            perfilAtual.setListarPaciente(checkListarPaciente.isSelected());
            perfilAtual.setCadastrarConsulta(checkCadastrarConsulta.isSelected());
            perfilAtual.setAtualizarConsulta(checkAtualizarConsulta.isSelected());
            perfilAtual.setDeletarConsulta(checkDeletarConsulta.isSelected());
            perfilAtual.setListarConsulta(checkListarConsulta.isSelected());
            perfilAtual.setCadastrarEspecialidade(checkCadastrarEspecialidade.isSelected());
            perfilAtual.setAtualizarEspecialidade(checkAtualizarEspecialidade.isSelected());
            perfilAtual.setDeletarEspecialidade(checkDeletarEspecialidade.isSelected());
            perfilAtual.setListarEspecialidade(checkListarEspecialidade.isSelected());
            perfilAtual.setCadastrarConvenio(checkCadastrarConvenio.isSelected());
            perfilAtual.setAtualizarConvenio(checkAtualizarConvenio.isSelected());
            perfilAtual.setDeletarConvenio(checkDeletarConvenio.isSelected());
            perfilAtual.setListarConvenio(checkListarConvenio.isSelected());
            perfilAtual.setCadastrarProntuario(checkCadastrarProntuario.isSelected());
            perfilAtual.setAtualizarProntuario(checkAtualizarProntuario.isSelected());
            perfilAtual.setDeletarProntuario(checkDeletarProntuario.isSelected());
            perfilAtual.setListarProntuario(checkListarProntuario.isSelected());

            try {
                perfilDao.atualizar(perfilAtual);
                JOptionPane.showMessageDialog(null, "Perfil atualizado com sucesso!");
                clearFields();
                campoId.setText("");
                toggleCamposEdicao(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar perfil: " + ex.getMessage());
                ex.printStackTrace();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EditarPerfil(null).setVisible(true);
            }
        });
    }
}