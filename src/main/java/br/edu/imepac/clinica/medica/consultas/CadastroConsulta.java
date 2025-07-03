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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CadastroConsulta extends JFrame {

    private JTextField campoSintomas;
    private JCheckBox campoERetorno;
    private JCheckBox campoEstaAtiva;
    private JComboBox<Paciente> campoIdPaciente;
    private JComboBox<Medico> campoIdMedico;
    private JButton botaoSalvar;
    private JButton botaoFechar;
    private JDateChooser dateChooser;
    private JSpinner timeSpinner;

    private ConsultaDao consultaDao;
    private PacienteDao pacienteDao;
    private MedicoDao medicoDao;
    private JFrame parentFrame; // Adicionado para referência à tela pai

    // Construtor principal para ser chamado pela tela pai
    public CadastroConsulta(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        inicializarDaos();

        setTitle("Cadastro de Consultas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Alterado para DISPOSE_ON_CLOSE
        setLocationRelativeTo(null);
        setResizable(false);

        // Adiciona um WindowListener para lidar com o fechamento da janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (CadastroConsulta.this.parentFrame != null) {
                    CadastroConsulta.this.parentFrame.setVisible(true);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        JLabel labelData = new JLabel("Data da Consulta:");
        labelData.setForeground(Estilo.COR_TEXTO);
        labelData.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
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
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(dateChooser, gbc);

        JLabel labelHora = new JLabel("Hora da Consulta (HH:MM):");
        labelHora.setForeground(Estilo.COR_TEXTO);
        labelHora.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
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
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(timeSpinner, gbc);

        JLabel labelSintomas = new JLabel("Sintomas:");
        labelSintomas.setForeground(Estilo.COR_TEXTO);
        labelSintomas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelSintomas, gbc);

        campoSintomas = new JTextField(25);
        campoSintomas.setBackground(Estilo.COR_BOTOES);
        campoSintomas.setForeground(Estilo.COR_TEXTO);
        campoSintomas.setCaretColor(Estilo.COR_TEXTO);
        campoSintomas.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campoSintomas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoSintomas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoSintomas.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoSintomas.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campoSintomas, gbc);

        campoERetorno = new JCheckBox("É Retorno?");
        campoERetorno.setForeground(Estilo.COR_TEXTO);
        campoERetorno.setBackground(Estilo.COR_FUNDO);
        campoERetorno.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoERetorno, gbc);

        campoEstaAtiva = new JCheckBox("Está Ativa?");
        campoEstaAtiva.setForeground(Estilo.COR_TEXTO);
        campoEstaAtiva.setBackground(Estilo.COR_FUNDO);
        campoEstaAtiva.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoEstaAtiva, gbc);

        JLabel labelIdPaciente = new JLabel("Paciente:");
        labelIdPaciente.setForeground(Estilo.COR_TEXTO);
        labelIdPaciente.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
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
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campoIdPaciente, gbc);

        JLabel labelIdMedico = new JLabel("Médico:");
        labelIdMedico.setForeground(Estilo.COR_TEXTO);
        labelIdMedico.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
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
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campoIdMedico, gbc);

        carregarPacientes();
        carregarMedicos();

        gbc.fill = GridBagConstraints.NONE;

        botaoSalvar = new JButton("Salvar");
        Estilo.estilizarBotao(botaoSalvar);
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoBotaoSalvar(e);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(botaoSalvar, gbc);

        botaoFechar = new JButton("Fechar");
        Estilo.estilizarBotao(botaoFechar);
        botaoFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoBotaoFechar();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(botaoFechar, gbc);

        add(panel);
    }

    // Adicionado um construtor sem argumentos para compatibilidade com o main, se necessário
    public CadastroConsulta() {
        this(null); // Chama o construtor principal passando null para parentFrame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadastroConsulta(null).setVisible(true); // Passa null para o parentFrame no main
            }
        });
    }

    private void acaoBotaoSalvar(ActionEvent e) {
        if (validarDadosObrigatorios()) {
            Consulta consulta = new Consulta();

            Date selectedDate = dateChooser.getDate();
            Date selectedTime = (Date) timeSpinner.getValue();

            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma data para a consulta.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime dataHora = LocalDateTime.ofInstant(selectedDate.toInstant(), ZoneId.systemDefault())
                    .withHour(selectedTime.toInstant().atZone(ZoneId.systemDefault()).getHour())
                    .withMinute(selectedTime.toInstant().atZone(ZoneId.systemDefault()).getMinute());

            consulta.setData(dataHora);

            consulta.setSintomas(campoSintomas.getText());
            consulta.seteRetorno(campoERetorno.isSelected());
            consulta.setEstaAtiva(campoEstaAtiva.isSelected());

            Paciente pacienteSelecionado = (Paciente) campoIdPaciente.getSelectedItem();
            Medico medicoSelecionado = (Medico) campoIdMedico.getSelectedItem();

            if (pacienteSelecionado != null) {
                consulta.setIdPaciente(pacienteSelecionado.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (medicoSelecionado != null) {
                consulta.setIdMedico(medicoSelecionado.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um médico.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                consultaDao.salvar(consulta);
                JOptionPane.showMessageDialog(null, "Consulta cadastrada com sucesso!");
                limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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

    private boolean validarDadosObrigatorios() {
        boolean dataSelecionada = dateChooser.getDate() != null;
        boolean sintomasPreenchidos = !campoSintomas.getText().trim().isEmpty();
        boolean pacienteSelecionado = campoIdPaciente.getSelectedItem() != null;
        boolean medicoSelecionado = campoIdMedico.getSelectedItem() != null;
        return dataSelecionada && sintomasPreenchidos && pacienteSelecionado && medicoSelecionado;
    }

    private void inicializarDaos() {
        try {
            this.consultaDao = new ConsultaDao();
            this.pacienteDao = new PacienteDao();
            this.medicoDao = new MedicoDao();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            dispose();
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

    private void limparCampos() {
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
    }
}