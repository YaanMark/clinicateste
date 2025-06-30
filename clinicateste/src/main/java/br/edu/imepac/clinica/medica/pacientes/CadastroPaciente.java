package br.edu.imepac.clinica.medica.pacientes;

import br.edu.imepac.clinica.medica.daos.PacienteDao;
import br.edu.imepac.clinica.medica.entidades.Paciente;
import br.edu.imepac.clinica.medica.outros.Estilo;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class CadastroPaciente extends JFrame {

    private JTextField campoNome;
    private JTextField campoIdade;
    private JTextField campoSexo;
    private JTextField campoCpf;
    private JTextField campoRua;
    private JTextField campoBairro;
    private JTextField campoCidade;
    private JTextField campoEstado;
    private JTextField campoContato;
    private JTextField campoEmail;
    private JDateChooser campoDataNascimento;
    private JButton botaoSalvar;
    private JButton botaoFechar;

    private PacienteDao pacienteDao;

    public CadastroPaciente() {

        inicializarPacienteDao();

        setTitle("Cadastro de Pacientes");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Nome do Paciente:"), gbc);

        campoNome = new JTextField(25);
        estilizarCampo(campoNome);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Idade:"), gbc);

        campoIdade = new JTextField(25);
        estilizarCampo(campoIdade);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(campoIdade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Sexo:"), gbc);

        campoSexo = new JTextField(25);
        estilizarCampo(campoSexo);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(campoSexo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("CPF:"), gbc);

        campoCpf = new JTextField(25);
        estilizarCampo(campoCpf);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(campoCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Rua:"), gbc);

        campoRua = new JTextField(25);
        estilizarCampo(campoRua);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(campoRua, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Bairro:"), gbc);

        campoBairro = new JTextField(25);
        estilizarCampo(campoBairro);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(campoBairro, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Cidade:"), gbc);

        campoCidade = new JTextField(25);
        estilizarCampo(campoCidade);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(campoCidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Estado:"), gbc);

        campoEstado = new JTextField(25);
        estilizarCampo(campoEstado);
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(campoEstado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Contato:"), gbc);

        campoContato = new JTextField(25);
        estilizarCampo(campoContato);
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(campoContato, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Email:"), gbc);

        campoEmail = new JTextField(25);
        estilizarCampo(campoEmail);
        gbc.gridx = 1;
        gbc.gridy = 9;
        panel.add(campoEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Data de Nascimento:"), gbc);

        campoDataNascimento = new JDateChooser();
        campoDataNascimento.setDateFormatString("dd/MM/yyyy");
        estilizarDateChooser(campoDataNascimento);
        gbc.gridx = 1;
        gbc.gridy = 10;
        panel.add(campoDataNascimento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        botaoSalvar = new JButton("Salvar");
        Estilo.estilizarBotao(botaoSalvar);
        botaoSalvar.addActionListener(e -> acaoBotaoSalvar());
        panel.add(botaoSalvar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.WEST;
        botaoFechar = new JButton("Fechar");
        Estilo.estilizarBotao(botaoFechar);
        botaoFechar.addActionListener(e -> dispose());
        panel.add(botaoFechar, gbc);

        add(panel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Estilo.COR_TEXTO);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setBackground(Estilo.COR_BOTOES);
        campo.setForeground(Estilo.COR_TEXTO);
        campo.setCaretColor(Estilo.COR_TEXTO);
        campo.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                campo.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }

            @Override
            public void focusLost(FocusEvent evt) {
                campo.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
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

    private void estilizarComboBox(JComboBox<Integer> comboBox) {
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

    private void acaoBotaoSalvar() {
        if (areAllFieldsFilled(campoNome, campoIdade, campoSexo, campoCpf,
                campoRua, campoBairro, campoCidade, campoEstado, campoContato, campoEmail) &&
                campoDataNascimento.getDate() != null) {

            Paciente paciente = new Paciente();
            paciente.setNome(campoNome.getText());
            paciente.setIdade(campoIdade.getText());
            paciente.setSexo(campoSexo.getText());
            paciente.setCpf(campoCpf.getText());
            paciente.setRua(campoRua.getText());
            paciente.setBairro(campoBairro.getText());
            paciente.setCidade(campoCidade.getText());
            paciente.setEstado(campoEstado.getText());
            paciente.setContato(campoContato.getText());
            paciente.setEmail(campoEmail.getText());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataNascimentoFormatada = sdf.format(campoDataNascimento.getDate());
            paciente.setDataNascimento(LocalDate.parse(dataNascimentoFormatada));

            try {
                pacienteDao.salvar(paciente);

                JOptionPane.showMessageDialog(null, "Paciente cadastrado com sucesso!");
                clearFields();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar paciente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios e selecione uma Data de Nascimento.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean areAllFieldsFilled(JTextField campoNome, JTextField campoIdade, JTextField campoSexo, JTextField campoCpf, JTextField campoRua, JTextField campoBairro, JTextField campoCidade, JTextField campoEstado, JTextField campoContato, JTextField campoEmail, JTextComponent... fields) {
        for (JTextComponent field : fields) {
            if (field instanceof JPasswordField) {
                if (((JPasswordField) field).getPassword().length == 0) {
                    return false;
                }
            } else if (field.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void clearFields() {
        campoNome.setText("");
        campoIdade.setText("");
        campoSexo.setText("");
        campoCpf.setText("");
        campoRua.setText("");
        campoBairro.setText("");
        campoCidade.setText("");
        campoEstado.setText("");
        campoContato.setText("");
        campoEmail.setText("");
        campoDataNascimento.setDate(null);
    }

    private void inicializarPacienteDao() {
        try {
            this.pacienteDao = new PacienteDao();
            System.out.println("PacienteDao inicializado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados para pacientes! Detalhes: " + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroPaciente().setVisible(true));
    }
}