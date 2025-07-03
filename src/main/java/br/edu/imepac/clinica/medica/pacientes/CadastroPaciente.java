package br.edu.imepac.clinica.medica.pacientes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import br.edu.imepac.clinica.medica.daos.PacienteDao;
import br.edu.imepac.clinica.medica.entidades.Paciente;
import br.edu.imepac.clinica.medica.outros.Estilo;

public class CadastroPaciente extends JFrame {

    private JTextField campoNome;
    private JTextField campoIdade;
    private JComboBox<Character> campoSexo;
    private JTextField campoCpf;
    private JTextField campoRua;
    private JTextField campoNumero;
    private JTextField campoComplemento;
    private JTextField campoBairro;
    private JTextField campoCidade;
    private JTextField campoEstado;
    private JTextField campoTelefone;
    private JTextField campoEmail;
    private JDateChooser campoDataNascimento;
    private JButton botaoSalvar;
    private JButton botaoFechar;

    private PacienteDao pacienteDao;
    private JFrame parentFrame;

    public CadastroPaciente(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        inicializarPacienteDao();

        setTitle("Cadastro de Pacientes");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        int row = 0;

        addComponent(panel, gbc, createLabel("Nome do Paciente:"), 0, row, 2);
        campoNome = new JTextField(30);
        estilizarCampoTexto(campoNome);
        addComponent(panel, gbc, campoNome, 0, ++row, 2);

        addComponent(panel, gbc, createLabel("Idade:"), 0, ++row, 2);
        campoIdade = new JTextField(30);
        estilizarCampoTexto(campoIdade);
        addComponent(panel, gbc, campoIdade, 0, ++row, 2);

        addComponent(panel, gbc, createLabel("Sexo (M/F/O):"), 0, ++row, 2);
        campoSexo = new JComboBox<>(new Character[]{'M', 'F', 'O'});
        estilizarComboBox(campoSexo);
        addComponent(panel, gbc, campoSexo, 0, ++row, 2);

        addComponent(panel, gbc, createLabel("CPF:"), 0, ++row, 2);
        campoCpf = new JTextField(30);
        estilizarCampoTexto(campoCpf);
        addComponent(panel, gbc, campoCpf, 0, ++row, 2);

        addComponent(panel, gbc, createLabel("Rua:"), 0, ++row, 2);
        campoRua = new JTextField(30);
        estilizarCampoTexto(campoRua);
        addComponent(panel, gbc, campoRua, 0, ++row, 2);

        addComponent(panel, gbc, createLabel("Número:"), 0, ++row, 2);
        campoNumero = new JTextField(30);
        estilizarCampoTexto(campoNumero);
        addComponent(panel, gbc, campoNumero, 0, ++row, 2);

        addComponent(panel, gbc, createLabel("Complemento:"), 0, ++row, 2);
        campoComplemento = new JTextField(30);
        estilizarCampoTexto(campoComplemento);
        addComponent(panel, gbc, campoComplemento, 0, ++row, 2);

        row = 0;

        addComponent(panel, gbc, createLabel("Bairro:"), 2, row, 2);
        campoBairro = new JTextField(30);
        estilizarCampoTexto(campoBairro);
        addComponent(panel, gbc, campoBairro, 2, ++row, 2);

        addComponent(panel, gbc, createLabel("Cidade:"), 2, ++row, 2);
        campoCidade = new JTextField(30);
        estilizarCampoTexto(campoCidade);
        addComponent(panel, gbc, campoCidade, 2, ++row, 2);

        addComponent(panel, gbc, createLabel("Estado (UF):"), 2, ++row, 2);
        campoEstado = new JTextField(30);
        estilizarCampoTexto(campoEstado);
        addComponent(panel, gbc, campoEstado, 2, ++row, 2);

        addComponent(panel, gbc, createLabel("Telefone:"), 2, ++row, 2);
        campoTelefone = new JTextField(30);
        estilizarCampoTexto(campoTelefone);
        addComponent(panel, gbc, campoTelefone, 2, ++row, 2);

        addComponent(panel, gbc, createLabel("Email:"), 2, ++row, 2);
        campoEmail = new JTextField(30);
        estilizarCampoTexto(campoEmail);
        addComponent(panel, gbc, campoEmail, 2, ++row, 2);

        addComponent(panel, gbc, createLabel("Data de Nascimento:"), 2, ++row, 2);
        campoDataNascimento = new JDateChooser();
        campoDataNascimento.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoDataNascimento.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campoDataNascimento.getCalendarButton().setBackground(Estilo.COR_BOTOES);
        campoDataNascimento.getCalendarButton().setForeground(Estilo.COR_TEXTO);
        addComponent(panel, gbc, campoDataNascimento, 2, ++row, 2);

        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Estilo.COR_FUNDO);

        botaoSalvar = new JButton("Salvar");
        Estilo.estilizarBotao(botaoSalvar);
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoBotaoSalvar();
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
        gbc.gridy = Math.max(row, 16);
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    private void addComponent(JPanel panel, GridBagConstraints gbc, JComponent component, int gridx, int gridy, int gridwidth) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        panel.add(component, gbc);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Estilo.COR_TEXTO);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private void estilizarCampoTexto(JTextField campo) {
        campo.setBackground(Estilo.COR_BOTOES);
        campo.setForeground(Estilo.COR_TEXTO);
        campo.setCaretColor(Estilo.COR_TEXTO);
        campo.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
    }

    private void estilizarComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(Estilo.COR_BOTOES);
        comboBox.setForeground(Estilo.COR_TEXTO);
        comboBox.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comboBox.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboBox.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadastroPaciente(null).setVisible(true);
            }
        });
    }

    private void acaoBotaoSalvar() {
        if(validarDadosObrigatorios()) {
            Paciente paciente = new Paciente();
            paciente.setNome(campoNome.getText());
            try {
                paciente.setIdade(Integer.parseInt(campoIdade.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Idade inválida. Por favor, insira um número.");
                return;
            }
            paciente.setSexo((Character) campoSexo.getSelectedItem());
            paciente.setCpf(campoCpf.getText());
            paciente.setRua(campoRua.getText());
            paciente.setNumero(campoNumero.getText());
            paciente.setComplemento(campoComplemento.getText());
            paciente.setBairro(campoBairro.getText());
            paciente.setCidade(campoCidade.getText());
            paciente.setEstado(campoEstado.getText());
            paciente.setTelefone(campoTelefone.getText());
            paciente.setEmail(campoEmail.getText());

            Date selectedDate = campoDataNascimento.getDate();
            if (selectedDate != null) {
                paciente.setDataNascimento(selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione a data de nascimento.");
                return;
            }

            try {
                pacienteDao.salvar(paciente);
                JOptionPane.showMessageDialog(null, "Paciente cadastrado com sucesso!");
                limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar paciente: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios.");
        }
    }

    private void acaoBotaoFechar() {
        dispose();
        if (parentFrame != null) {
            parentFrame.setVisible(true);
        }
    }

    private boolean validarDadosObrigatorios() {
        return !campoNome.getText().trim().isEmpty() &&
                !campoIdade.getText().trim().isEmpty() &&
                campoSexo.getSelectedItem() != null &&
                !campoCpf.getText().trim().isEmpty() &&
                !campoRua.getText().trim().isEmpty() &&
                !campoNumero.getText().trim().isEmpty() &&
                !campoBairro.getText().trim().isEmpty() &&
                !campoCidade.getText().trim().isEmpty() &&
                !campoEstado.getText().trim().isEmpty() &&
                !campoTelefone.getText().trim().isEmpty() &&
                !campoEmail.getText().trim().isEmpty() &&
                campoDataNascimento.getDate() != null;
    }

    private void limparCampos() {
        campoNome.setText("");
        campoIdade.setText("");
        campoSexo.setSelectedIndex(0);
        campoCpf.setText("");
        campoRua.setText("");
        campoNumero.setText("");
        campoComplemento.setText("");
        campoBairro.setText("");
        campoCidade.setText("");
        campoEstado.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");
        campoDataNascimento.setDate(null);
    }

    private void inicializarPacienteDao() {
        try {
            this.pacienteDao = new PacienteDao();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados - pacientes!");
            dispose();
        }
    }
}