package br.edu.imepac.clinica.medica.pacientes;

import br.edu.imepac.clinica.medica.daos.PacienteDao;
import br.edu.imepac.clinica.medica.entidades.Paciente;
import br.edu.imepac.clinica.medica.outros.Estilo;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditarPaciente extends JFrame {

    private JTextField campoId;
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
    private JButton btnBuscar;
    private JButton btnEditar;
    private JButton btnFechar;

    private PacienteDao pacienteDao;
    private Paciente pacienteAtual;
    private JFrame parentFrame;

    public EditarPaciente(JFrame parentFrame) {
        super("Editar Paciente");
        this.parentFrame = parentFrame;

        inicializarDao();

        setSize(700, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Estilo.COR_FUNDO);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("ID do Paciente:"), gbc);

        campoId = new JTextField(20);
        estilizarCampoTexto(campoId);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1;
        panel.add(campoId, gbc);

        btnBuscar = new JButton("Buscar");
        Estilo.estilizarBotao(btnBuscar);
        btnBuscar.addActionListener(e -> acaoBotaoBuscar());
        gbc.gridx = 2; gbc.gridy = 0; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.WEST;
        panel.add(btnBuscar, gbc);

        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 1; panel.add(createLabel("Nome Completo:"), gbc);
        campoNome = new JTextField(25); estilizarCampoTexto(campoNome); gbc.gridx = 1; panel.add(campoNome, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(createLabel("Idade:"), gbc);
        campoIdade = new JTextField(25); estilizarCampoTexto(campoIdade); gbc.gridx = 1; panel.add(campoIdade, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(createLabel("Sexo:"), gbc);
        campoSexo = new JComboBox<>(new Character[]{'M', 'F', 'O'});
        estilizarComboBox(campoSexo);
        gbc.gridx = 1; panel.add(campoSexo, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(createLabel("CPF:"), gbc);
        campoCpf = new JTextField(25); estilizarCampoTexto(campoCpf); gbc.gridx = 1; panel.add(campoCpf, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panel.add(createLabel("Rua:"), gbc);
        campoRua = new JTextField(25); estilizarCampoTexto(campoRua); gbc.gridx = 1; panel.add(campoRua, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panel.add(createLabel("Número:"), gbc);
        campoNumero = new JTextField(25); estilizarCampoTexto(campoNumero); gbc.gridx = 1; panel.add(campoNumero, gbc);

        gbc.gridx = 0; gbc.gridy = 7; panel.add(createLabel("Complemento:"), gbc);
        campoComplemento = new JTextField(25); estilizarCampoTexto(campoComplemento); gbc.gridx = 1; panel.add(campoComplemento, gbc);

        gbc.gridx = 0; gbc.gridy = 8; panel.add(createLabel("Bairro:"), gbc);
        campoBairro = new JTextField(25); estilizarCampoTexto(campoBairro); gbc.gridx = 1; panel.add(campoBairro, gbc);

        gbc.gridx = 0; gbc.gridy = 9; panel.add(createLabel("Cidade:"), gbc);
        campoCidade = new JTextField(25); estilizarCampoTexto(campoCidade); gbc.gridx = 1; panel.add(campoCidade, gbc);

        gbc.gridx = 0; gbc.gridy = 10; panel.add(createLabel("Estado:"), gbc);
        campoEstado = new JTextField(25); estilizarCampoTexto(campoEstado); gbc.gridx = 1; panel.add(campoEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 11; panel.add(createLabel("Telefone:"), gbc);
        campoTelefone = new JTextField(25); estilizarCampoTexto(campoTelefone); gbc.gridx = 1; panel.add(campoTelefone, gbc);

        gbc.gridx = 0; gbc.gridy = 12; panel.add(createLabel("Email:"), gbc);
        campoEmail = new JTextField(25); estilizarCampoTexto(campoEmail); gbc.gridx = 1; panel.add(campoEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 13; panel.add(createLabel("Data de Nascimento:"), gbc);
        campoDataNascimento = new JDateChooser();
        campoDataNascimento.setDateFormatString("dd/MM/yyyy");
        estilizarDateChooser(campoDataNascimento);
        gbc.gridx = 1; panel.add(campoDataNascimento, gbc);

        gbc.gridx = 0; gbc.gridy = 14; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        btnEditar = new JButton("Editar");
        Estilo.estilizarBotao(btnEditar);
        btnEditar.addActionListener(e -> acaoBotaoEditar());
        panel.add(btnEditar, gbc);

        gbc.gridx = 1; gbc.gridy = 14; gbc.anchor = GridBagConstraints.WEST;
        btnFechar = new JButton("Fechar");
        Estilo.estilizarBotao(btnFechar);
        btnFechar.addActionListener(e -> fecharAplicacao());
        panel.add(btnFechar, gbc);

        toggleCamposEdicao(false);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Estilo.COR_TEXTO);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private void estilizarCampoTexto(JTextComponent comp) {
        comp.setBackground(Estilo.COR_BOTOES);
        comp.setForeground(Estilo.COR_TEXTO);
        comp.setCaretColor(Estilo.COR_TEXTO);
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comp.setBorder(new LineBorder(Estilo.COR_BORDA_BOTAO, 1));

        comp.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                comp.setBorder(new LineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                comp.setBorder(new LineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
    }

    private void estilizarDateChooser(JDateChooser dateChooser) {
        dateChooser.setBackground(Estilo.COR_BOTOES);
        dateChooser.setForeground(Estilo.COR_TEXTO);
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateChooser.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));

        if (dateChooser.getJCalendar() != null) {
            dateChooser.getJCalendar().setBackground(Estilo.COR_FUNDO);
            dateChooser.getJCalendar().setForeground(Estilo.COR_TEXTO);
            dateChooser.getJCalendar().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

    private void estilizarComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(Estilo.COR_BOTOES);
        comboBox.setForeground(Estilo.COR_TEXTO);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBox.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        comboBox.setOpaque(true);

        comboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                comboBox.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            @Override
            public void focusLost(FocusEvent evt) {
                comboBox.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
    }

    private void inicializarDao() {
        try {
            this.pacienteDao = new PacienteDao();
            System.out.println("DAO de Paciente inicializado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados! Detalhes: " + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            dispose();
        }
    }

    private void toggleCamposEdicao(boolean enable) {
        campoNome.setEnabled(enable);
        campoIdade.setEnabled(enable);
        campoSexo.setEnabled(enable);
        campoCpf.setEnabled(enable);
        campoRua.setEnabled(enable);
        campoNumero.setEnabled(enable);
        campoComplemento.setEnabled(enable);
        campoBairro.setEnabled(enable);
        campoCidade.setEnabled(enable);
        campoEstado.setEnabled(enable);
        campoTelefone.setEnabled(enable);
        campoEmail.setEnabled(enable);
        campoDataNascimento.setEnabled(enable);
        btnEditar.setEnabled(enable);
    }

    private void clearFields() {
        campoNome.setText("");
        campoIdade.setText("");
        campoSexo.setSelectedItem(null);
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
        pacienteAtual = null;
    }

    private void acaoBotaoBuscar() {
        if (campoId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite o ID do paciente para buscar.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(campoId.getText().trim());
            pacienteAtual = pacienteDao.buscarPorId(id);

            if (pacienteAtual != null) {
                campoNome.setText(pacienteAtual.getNome());
                campoIdade.setText(String.valueOf(pacienteAtual.getIdade()));
                campoSexo.setSelectedItem(pacienteAtual.getSexo());
                campoCpf.setText(pacienteAtual.getCpf());
                campoRua.setText(pacienteAtual.getRua());
                campoNumero.setText(pacienteAtual.getNumero());
                campoComplemento.setText(pacienteAtual.getComplemento());
                campoBairro.setText(pacienteAtual.getBairro());
                campoCidade.setText(pacienteAtual.getCidade());
                campoEstado.setText(pacienteAtual.getEstado());
                campoTelefone.setText(pacienteAtual.getTelefone());
                campoEmail.setText(pacienteAtual.getEmail());

                if (pacienteAtual.getDataNascimento() != null) {
                    Date date = Date.from(pacienteAtual.getDataNascimento().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    campoDataNascimento.setDate(date);
                } else {
                    campoDataNascimento.setDate(null);
                }

                toggleCamposEdicao(true);
                JOptionPane.showMessageDialog(this, "Paciente encontrado! Você pode editar os dados.");
            } else {
                JOptionPane.showMessageDialog(this, "Paciente com ID " + id + " não encontrado.", "Não Encontrado", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                toggleCamposEdicao(false);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            clearFields();
            toggleCamposEdicao(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar paciente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            clearFields();
            toggleCamposEdicao(false);
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

    private void acaoBotaoEditar() {
        if (pacienteAtual == null) {
            JOptionPane.showMessageDialog(this, "Nenhum paciente carregado para edição.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validarDadosObrigatorios()) {
            try {
                pacienteAtual.setNome(campoNome.getText());
                pacienteAtual.setIdade(Integer.parseInt(campoIdade.getText()));
                pacienteAtual.setSexo((Character) campoSexo.getSelectedItem());
                pacienteAtual.setCpf(campoCpf.getText());
                pacienteAtual.setRua(campoRua.getText());
                pacienteAtual.setNumero(campoNumero.getText());
                pacienteAtual.setComplemento(campoComplemento.getText());
                pacienteAtual.setBairro(campoBairro.getText());
                pacienteAtual.setCidade(campoCidade.getText());
                pacienteAtual.setEstado(campoEstado.getText());
                pacienteAtual.setTelefone(campoTelefone.getText());
                pacienteAtual.setEmail(campoEmail.getText());

                if (campoDataNascimento.getDate() != null) {
                    pacienteAtual.setDataNascimento(campoDataNascimento.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                } else {
                    pacienteAtual.setDataNascimento(null);
                }

                pacienteDao.atualizar(pacienteAtual);
                JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
                clearFields();
                campoId.setText("");
                toggleCamposEdicao(false);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Idade inválida. Por favor, digite um número inteiro para a idade.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao atualizar paciente: " + e.getMessage(),
                        "Erro de banco de dados",
                        JOptionPane.ERROR_MESSAGE
                );
                System.err.println("Erro ao atualizar paciente: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, preencha todos os campos obrigatórios e selecione a Data de Nascimento e o Sexo.",
                    "Dados Incompletos",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void fecharAplicacao() {
        this.dispose();
        if (parentFrame != null) {
            parentFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EditarPaciente(null).setVisible(true);
        });
    }
}