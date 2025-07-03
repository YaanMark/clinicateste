package br.edu.imepac.clinica.medica.consultas;

import br.edu.imepac.clinica.medica.daos.ConsultaDao;
import br.edu.imepac.clinica.medica.daos.MedicoDao;
import br.edu.imepac.clinica.medica.daos.PacienteDao;
import br.edu.imepac.clinica.medica.entidades.Consulta;
import br.edu.imepac.clinica.medica.entidades.Medico;
import br.edu.imepac.clinica.medica.entidades.Paciente;
import br.edu.imepac.clinica.medica.outros.Estilo;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter; // Importar WindowAdapter
import java.awt.event.WindowEvent;   // Importar WindowEvent
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class EditarConsulta extends JFrame {

    private JTextField campoId;
    private JTextField campoSintomas;
    private JCheckBox campoERetorno;
    private JCheckBox campoEstaAtiva;
    private JComboBox<Paciente> campoIdPaciente;
    private JComboBox<Medico> campoIdMedico;
    private JButton botaoBuscar;
    private JButton botaoSalvar;
    private JButton botaoFechar;
    private JDateChooser dateChooser;
    private JSpinner timeSpinner;

    private ConsultaDao consultaDao;
    private PacienteDao pacienteDao;
    private MedicoDao medicoDao;
    private Consulta consultaAtual;
    private JFrame parentFrame; // Adicionado para referência à tela pai

    // Construtor principal para ser chamado pela tela pai
    public EditarConsulta(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        inicializarDaos();

        setTitle("Editar Consulta");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Adiciona um WindowListener para lidar com o fechamento da janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (EditarConsulta.this.parentFrame != null) {
                    EditarConsulta.this.parentFrame.setVisible(true);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        JLabel labelId = createLabel("ID da Consulta:");
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

        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelData = createLabel("Data da Consulta:");
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelData, gbc);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateChooser.setBackground(Estilo.COR_BOTOES);
        ((JTextFieldDateEditor) dateChooser.getDateEditor()).setBackground(Estilo.COR_BOTOES);
        ((JTextFieldDateEditor) dateChooser.getDateEditor()).setForeground(Estilo.COR_TEXTO);
        ((JTextFieldDateEditor) dateChooser.getDateEditor()).setCaretColor(Estilo.COR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(dateChooser, gbc);

        JLabel labelHora = createLabel("Hora da Consulta (HH:MM):");
        labelHora.setForeground(Estilo.COR_TEXTO);
        labelHora.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelHora, gbc);

        SpinnerDateModel sm = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE);
        timeSpinner = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(de);
        timeSpinner.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        timeSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        timeSpinner.setBackground(Estilo.COR_BOTOES);
        ((JFormattedTextField)timeSpinner.getEditor().getComponent(0)).setBackground(Estilo.COR_BOTOES);
        ((JFormattedTextField)timeSpinner.getEditor().getComponent(0)).setForeground(Estilo.COR_TEXTO);
        ((JFormattedTextField)timeSpinner.getEditor().getComponent(0)).setCaretColor(Estilo.COR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(timeSpinner, gbc);

        JLabel labelSintomas = createLabel("Sintomas:");
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelSintomas, gbc);

        campoSintomas = new JTextField(25);
        estilizarCampoTexto(campoSintomas);
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(campoSintomas, gbc);

        campoERetorno = new JCheckBox("É Retorno?");
        estilizarCheckBox(campoERetorno);
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(campoERetorno, gbc);

        campoEstaAtiva = new JCheckBox("Está Ativa?");
        estilizarCheckBox(campoEstaAtiva);
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(campoEstaAtiva, gbc);

        JLabel labelIdPaciente = createLabel("Paciente:");
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelIdPaciente, gbc);

        campoIdPaciente = new JComboBox<>();
        campoIdPaciente.setBackground(Estilo.COR_BOTOES);
        campoIdPaciente.setForeground(Estilo.COR_TEXTO);
        campoIdPaciente.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campoIdPaciente.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoIdPaciente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoIdPaciente.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoIdPaciente.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(campoIdPaciente, gbc);

        JLabel labelIdMedico = createLabel("Médico:");
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(labelIdMedico, gbc);

        campoIdMedico = new JComboBox<>();
        campoIdMedico.setBackground(Estilo.COR_BOTOES);
        campoIdMedico.setForeground(Estilo.COR_TEXTO);
        campoIdMedico.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campoIdMedico.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoIdMedico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoIdMedico.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoIdMedico.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(campoIdMedico, gbc);

        carregarPacientes();
        carregarMedicos();

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 2;

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

        gbc.gridx = 0;
        gbc.gridy = row++;
        panel.add(buttonPanel, gbc);

        add(panel);
        toggleCamposEdicao(false);
    }

    // Adicionado um construtor sem argumentos para compatibilidade com o main, se necessário
    public EditarConsulta() {
        this(null); // Chama o construtor principal passando null para parentFrame
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

    private void inicializarDaos() {
        try {
            this.consultaDao = new ConsultaDao();
            this.pacienteDao = new PacienteDao();
            this.medicoDao = new MedicoDao();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void toggleCamposEdicao(boolean enable) {
        dateChooser.setEnabled(enable);
        timeSpinner.setEnabled(enable);
        campoSintomas.setEnabled(enable);
        campoERetorno.setEnabled(enable);
        campoEstaAtiva.setEnabled(enable);
        campoIdPaciente.setEnabled(enable);
        campoIdMedico.setEnabled(enable);
        botaoSalvar.setEnabled(enable);
    }

    private void clearFields() {
        campoId.setText("");
        dateChooser.setDate(null);
        timeSpinner.setValue(new Date());
        campoSintomas.setText("");
        campoERetorno.setSelected(false);
        campoEstaAtiva.setSelected(false);
        if (campoIdPaciente.getItemCount() > 0) {
            campoIdPaciente.setSelectedIndex(0);
        }
        if (campoIdMedico.getItemCount() > 0) {
            campoIdMedico.setSelectedIndex(0);
        }
        consultaAtual = null;
    }

    private void acaoBotaoBuscar() {
        if (campoId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite o ID da consulta para buscar.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(campoId.getText().trim());
            consultaAtual = consultaDao.buscarPorId(id);
            if (consultaAtual != null) {
                dateChooser.setDate(Date.from(consultaAtual.getData().atZone(ZoneId.systemDefault()).toInstant()));
                timeSpinner.setValue(Date.from(consultaAtual.getData().atZone(ZoneId.systemDefault()).toInstant()));
                campoSintomas.setText(consultaAtual.getSintomas());
                campoERetorno.setSelected(consultaAtual.iseRetorno());
                campoEstaAtiva.setSelected(consultaAtual.isEstaAtiva());

                for (int i = 0; i < campoIdPaciente.getItemCount(); i++) {
                    Paciente p = campoIdPaciente.getItemAt(i);
                    if (p.getId() == consultaAtual.getIdPaciente()) {
                        campoIdPaciente.setSelectedItem(p);
                        break;
                    }
                }

                for (int i = 0; i < campoIdMedico.getItemCount(); i++) {
                    Medico m = campoIdMedico.getItemAt(i);
                    if (m.getId() == consultaAtual.getIdMedico()) {
                        campoIdMedico.setSelectedItem(m);
                        break;
                    }
                }

                toggleCamposEdicao(true);
                JOptionPane.showMessageDialog(this, "Consulta encontrada! Você pode editar os dados.");
            } else {
                JOptionPane.showMessageDialog(this, "Consulta com ID " + id + " não encontrada.", "Não Encontrado", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                toggleCamposEdicao(false);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            clearFields();
            toggleCamposEdicao(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar consulta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            clearFields();
            toggleCamposEdicao(false);
        }
    }

    private boolean validarDadosObrigatorios() {
        boolean dataSelecionada = dateChooser.getDate() != null;
        boolean sintomasPreenchidos = !campoSintomas.getText().trim().isEmpty();
        boolean pacienteSelecionado = campoIdPaciente.getSelectedItem() != null;
        boolean medicoSelecionado = campoIdMedico.getSelectedItem() != null;
        return dataSelecionada && sintomasPreenchidos && pacienteSelecionado && medicoSelecionado;
    }

    private void acaoBotaoSalvar(ActionEvent e) {
        if (consultaAtual == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma consulta carregada para edição.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validarDadosObrigatorios()) {
            Date selectedDate = dateChooser.getDate();
            Date selectedTime = (Date) timeSpinner.getValue();

            LocalDateTime dataHora = LocalDateTime.ofInstant(selectedDate.toInstant(), ZoneId.systemDefault())
                    .withHour(selectedTime.toInstant().atZone(ZoneId.systemDefault()).getHour())
                    .withMinute(selectedTime.toInstant().atZone(ZoneId.systemDefault()).getMinute());

            consultaAtual.setData(dataHora);
            consultaAtual.setSintomas(campoSintomas.getText());
            consultaAtual.seteRetorno(campoERetorno.isSelected());
            consultaAtual.setEstaAtiva(campoEstaAtiva.isSelected());

            Paciente pacienteSelecionado = (Paciente) campoIdPaciente.getSelectedItem();
            Medico medicoSelecionado = (Medico) campoIdMedico.getSelectedItem();

            if (pacienteSelecionado != null) {
                consultaAtual.setIdPaciente(pacienteSelecionado.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (medicoSelecionado != null) {
                consultaAtual.setIdMedico(medicoSelecionado.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um médico.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                consultaDao.atualizar(consultaAtual);
                JOptionPane.showMessageDialog(null, "Consulta atualizada com sucesso!");
                clearFields();
                toggleCamposEdicao(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Campos Obrigatórios", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void acaoBotaoFechar() {
        dispose();
        if (parentFrame != null) { // Adicionado para voltar à tela pai
            parentFrame.setVisible(true);
        }
    }

    private void carregarPacientes() {
        try {
            List<Paciente> pacientesList = pacienteDao.listarTodos();
            if (pacientesList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum paciente cadastrado! Cadastre um paciente antes de cadastrar uma consulta.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                pacientesList.forEach(paciente -> this.campoIdPaciente.addItem(paciente));
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar pacientes: " + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarMedicos() {
        try {
            List<Medico> medicosList = medicoDao.listarTodos();
            if (medicosList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum médico cadastrado! Cadastre um médico antes de cadastrar uma consulta.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                medicosList.forEach(medico -> this.campoIdMedico.addItem(medico));
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar médicos: " + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EditarConsulta(null).setVisible(true)); // Passa null para o parentFrame no main
    }
}