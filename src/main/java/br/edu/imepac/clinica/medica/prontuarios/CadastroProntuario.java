package br.edu.imepac.clinica.medica.prontuarios;

import br.edu.imepac.clinica.medica.daos.ConsultaDao;
import br.edu.imepac.clinica.medica.daos.MedicoDao;
import br.edu.imepac.clinica.medica.daos.PacienteDao;
import br.edu.imepac.clinica.medica.daos.ProntuarioDao;
import br.edu.imepac.clinica.medica.entidades.Consulta;
import br.edu.imepac.clinica.medica.entidades.Medico;
import br.edu.imepac.clinica.medica.entidades.Paciente;
import br.edu.imepac.clinica.medica.entidades.Prontuario;
import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CadastroProntuario extends JFrame {

    private JTextField campoIdConsultaBusca;
    private JTextArea campoReceituario;
    private JTextArea campoExames;
    private JTextArea campoObservacoes;
    private JComboBox<Medico> campoIdMedico;
    private JComboBox<Paciente> campoIdPaciente;
    private JButton botaoSalvar;
    private JButton botaoBuscarConsulta;
    private JButton botaoFechar;

    private ProntuarioDao prontuarioDao;
    private ConsultaDao consultaDao;
    private MedicoDao medicoDao;
    private PacienteDao pacienteDao;
    private Consulta consultaSelecionada;

    public CadastroProntuario() {
        inicializarDaos();

        setTitle("Cadastro de Prontuários");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        JLabel labelIdConsultaBusca = createLabel("ID da Consulta:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelIdConsultaBusca, gbc);

        campoIdConsultaBusca = new JTextField(20);
        estilizarCampoTexto(campoIdConsultaBusca);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(campoIdConsultaBusca, gbc);

        botaoBuscarConsulta = new JButton("Buscar Consulta");
        Estilo.estilizarBotao(botaoBuscarConsulta);
        botaoBuscarConsulta.addActionListener(e -> acaoBotaoBuscarConsulta());
        gbc.gridx = 2;
        gbc.gridy = row++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(botaoBuscarConsulta, gbc);

        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelReceituario = createLabel("Receituário:");
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelReceituario, gbc);

        campoReceituario = new JTextArea(5, 30);
        estilizarAreaTexto(campoReceituario);
        JScrollPane scrollReceituario = new JScrollPane(campoReceituario);
        scrollReceituario.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollReceituario, gbc);

        JLabel labelExames = createLabel("Exames:");
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelExames, gbc);

        campoExames = new JTextArea(5, 30);
        estilizarAreaTexto(campoExames);
        JScrollPane scrollExames = new JScrollPane(campoExames);
        scrollExames.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollExames, gbc);

        JLabel labelObservacoes = createLabel("Observações:");
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelObservacoes, gbc);

        campoObservacoes = new JTextArea(5, 30);
        estilizarAreaTexto(campoObservacoes);
        JScrollPane scrollObservacoes = new JScrollPane(campoObservacoes);
        scrollObservacoes.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollObservacoes, gbc);

        JLabel labelIdMedico = createLabel("Médico:");
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelIdMedico, gbc);

        campoIdMedico = new JComboBox<>();
        estilizarComboBox(campoIdMedico);
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campoIdMedico, gbc);

        JLabel labelIdPaciente = createLabel("Paciente:");
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelIdPaciente, gbc);

        campoIdPaciente = new JComboBox<>();
        estilizarComboBox(campoIdPaciente);
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campoIdPaciente, gbc);

        carregarMedicos();
        carregarPacientes();

        gbc.fill = GridBagConstraints.NONE;

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

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);
        toggleCamposEdicao(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadastroProntuario().setVisible(true);
            }
        });
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

    private void estilizarAreaTexto(JTextArea comp) {
        comp.setBackground(Estilo.COR_BOTOES);
        comp.setForeground(Estilo.COR_TEXTO);
        comp.setCaretColor(Estilo.COR_TEXTO);
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comp.setLineWrap(true);
        comp.setWrapStyleWord(true);
        comp.setBorder(new EmptyBorder(0,0,0,0));

        comp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ((JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, comp)).setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                ((JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, comp)).setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
    }

    private void estilizarComboBox(JComboBox<?> comp) {
        comp.setBackground(Estilo.COR_BOTOES);
        comp.setForeground(Estilo.COR_TEXTO);
        comp.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comp.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                comp.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
    }

    private void inicializarDaos() {
        try {
            this.prontuarioDao = new ProntuarioDao();
            this.consultaDao = new ConsultaDao();
            this.medicoDao = new MedicoDao();
            this.pacienteDao = new PacienteDao();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void toggleCamposEdicao(boolean enable) {
        campoReceituario.setEnabled(enable);
        campoExames.setEnabled(enable);
        campoObservacoes.setEnabled(enable);
        campoIdMedico.setEnabled(enable);
        campoIdPaciente.setEnabled(enable);
        botaoSalvar.setEnabled(enable);
    }

    private void clearFields() {
        campoIdConsultaBusca.setText("");
        campoReceituario.setText("");
        campoExames.setText("");
        campoObservacoes.setText("");
        if (campoIdMedico.getItemCount() > 0) {
            campoIdMedico.setSelectedIndex(0);
        }
        if (campoIdPaciente.getItemCount() > 0) {
            campoIdPaciente.setSelectedIndex(0);
        }
        consultaSelecionada = null;
        toggleCamposEdicao(false);
    }

    private void acaoBotaoBuscarConsulta() {
        if (campoIdConsultaBusca.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite o ID da consulta para buscar.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(campoIdConsultaBusca.getText().trim());
            consultaSelecionada = consultaDao.buscarPorId(id);
            if (consultaSelecionada != null) {
                for (int i = 0; i < campoIdMedico.getItemCount(); i++) {
                    Medico m = campoIdMedico.getItemAt(i);
                    if (m.getId() == consultaSelecionada.getIdMedico()) {
                        campoIdMedico.setSelectedItem(m);
                        break;
                    }
                }

                for (int i = 0; i < campoIdPaciente.getItemCount(); i++) {
                    Paciente p = campoIdPaciente.getItemAt(i);
                    if (p.getId() == consultaSelecionada.getIdPaciente()) {
                        campoIdPaciente.setSelectedItem(p);
                        break;
                    }
                }
                toggleCamposEdicao(true);
                JOptionPane.showMessageDialog(this, "Consulta encontrada! Médico e Paciente preenchidos.");
            } else {
                JOptionPane.showMessageDialog(this, "Consulta com ID " + id + " não encontrada.", "Não Encontrado", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar consulta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            clearFields();
        }
    }

    private void acaoBotaoSalvar(ActionEvent e) {
        if (consultaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma consulta selecionada para vincular o prontuário.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validarDadosObrigatorios()) {
            Prontuario prontuario = new Prontuario();
            prontuario.setReceituario(campoReceituario.getText());
            prontuario.setExames(campoExames.getText());
            prontuario.setObservacoes(campoObservacoes.getText());

            prontuario.setId_consulta(consultaSelecionada.getId());

            Medico medicoSelecionado = (Medico) campoIdMedico.getSelectedItem();
            Paciente pacienteSelecionado = (Paciente) campoIdPaciente.getSelectedItem();

            if (medicoSelecionado != null) {
                prontuario.setId_medico(medicoSelecionado.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um médico.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (pacienteSelecionado != null) {
                prontuario.setId_paciente(pacienteSelecionado.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                prontuarioDao.salvar(prontuario);
                JOptionPane.showMessageDialog(null, "Prontuário cadastrado com sucesso!");
                clearFields();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar prontuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Campos Obrigatórios", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void acaoBotaoFechar() {
        dispose();
    }

    private boolean validarDadosObrigatorios() {
        boolean receituarioPreenchido = !campoReceituario.getText().trim().isEmpty();
        boolean examesPreenchidos = !campoExames.getText().trim().isEmpty();
        boolean observacoesPreenchidas = !campoObservacoes.getText().trim().isEmpty();
        boolean medicoSelecionado = campoIdMedico.getSelectedItem() != null;
        boolean pacienteSelecionado = campoIdPaciente.getSelectedItem() != null;
        return receituarioPreenchido && examesPreenchidos && observacoesPreenchidas &&
                medicoSelecionado && pacienteSelecionado;
    }

    private void carregarMedicos() {
        try {
            List<Medico> medicosList = medicoDao.listarTodos();
            if (medicosList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum médico cadastrado! Cadastre um médico antes de cadastrar um prontuário.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                medicosList.forEach(medico -> this.campoIdMedico.addItem(medico));
            }
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar médicos: " + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarPacientes() {
        try {
            List<Paciente> pacientesList = pacienteDao.listarTodos();
            if (pacientesList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum paciente cadastrado! Cadastre um paciente antes de cadastrar um prontuário.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                pacientesList.forEach(paciente -> this.campoIdPaciente.addItem(paciente));
            }
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar pacientes: " + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}