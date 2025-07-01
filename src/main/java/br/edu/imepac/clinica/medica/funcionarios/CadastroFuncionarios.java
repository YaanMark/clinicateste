package br.edu.imepac.clinica.medica.funcionarios;

import br.edu.imepac.clinica.medica.daos.FuncionariosDao;
import br.edu.imepac.clinica.medica.daos.PerfilDao;
import br.edu.imepac.clinica.medica.entidades.Funcionarios;
import br.edu.imepac.clinica.medica.entidades.Perfil;
import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CadastroFuncionarios extends JFrame {

    private final JComboBox<Perfil> comboIdPerfil;
    private JTextField campoUsuario;
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
    private com.toedter.calendar.JDateChooser campoDataNascimento;
    private JPasswordField campoSenha;
    private JButton botaoSalvar;
    private JButton botaoFechar;

    private FuncionariosDao funcionariosDao;
    private PerfilDao perfilDao;

    public CadastroFuncionarios() {

        inicializarFuncionarioDao();
        inicializarPerfilDao();

        setTitle("Cadastro de Funcionários");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Nome do funcionário:"), gbc);

        campoNome = new JTextField(25);
        estilizarCampo(campoNome);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Usuário:"), gbc);

        campoUsuario = new JTextField(25);
        estilizarCampo(campoUsuario);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(campoUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Senha:"), gbc);

        campoSenha = new JPasswordField(25);
        estilizarCampo(campoSenha);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(campoSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("ID Perfil:"), gbc);

        comboIdPerfil = new JComboBox<>();
        try {
            List<Perfil> perfis = perfilDao.listar();
            for (Perfil perfil : perfis) {
                comboIdPerfil.addItem(perfil);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar perfis: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        estilizarComboBox(comboIdPerfil);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(comboIdPerfil, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Idade:"), gbc);

        campoIdade = new JTextField(25);
        estilizarCampo(campoIdade);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(campoIdade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Sexo:"), gbc);

        campoSexo = new JTextField(25);
        estilizarCampo(campoSexo);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(campoSexo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("CPF:"), gbc);

        campoCpf = new JTextField(25);
        estilizarCampo(campoCpf);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(campoCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Rua:"), gbc);

        campoRua = new JTextField(25);
        estilizarCampo(campoRua);
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(campoRua, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Bairro:"), gbc);

        campoBairro = new JTextField(25);
        estilizarCampo(campoBairro);
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(campoBairro, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Cidade:"), gbc);

        campoCidade = new JTextField(25);
        estilizarCampo(campoCidade);
        gbc.gridx = 1;
        gbc.gridy = 9;
        panel.add(campoCidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Estado:"), gbc);

        campoEstado = new JTextField(25);
        estilizarCampo(campoEstado);
        gbc.gridx = 1;
        gbc.gridy = 10;
        panel.add(campoEstado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Contato:"), gbc);

        campoContato = new JTextField(25);
        estilizarCampo(campoContato);
        gbc.gridx = 1;
        gbc.gridy = 11;
        panel.add(campoContato, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Email:"), gbc);

        campoEmail = new JTextField(25);
        estilizarCampo(campoEmail);
        gbc.gridx = 1;
        gbc.gridy = 12;
        panel.add(campoEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Data de Nascimento:"), gbc);

        campoDataNascimento = new com.toedter.calendar.JDateChooser();
        campoDataNascimento.setDateFormatString("dd/MM/yyyy");
        estilizarDateChooser(campoDataNascimento);
        gbc.gridx = 1;
        gbc.gridy = 13;
        panel.add(campoDataNascimento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        botaoSalvar = new JButton("Salvar");
        Estilo.estilizarBotao(botaoSalvar);
        botaoSalvar.addActionListener(e -> acaoBotaoSalvar());
        panel.add(botaoSalvar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 14;
        gbc.anchor = GridBagConstraints.WEST;
        botaoFechar = new JButton("Fechar");
        Estilo.estilizarBotao(botaoFechar);
        botaoFechar.addActionListener(e -> dispose());
        panel.add(botaoFechar, gbc);

        add(panel);
    }

    private void estilizarDateChooser(com.toedter.calendar.JDateChooser campoDataNascimento) {
        campoDataNascimento.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoDataNascimento.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campoDataNascimento.setBackground(Estilo.COR_BOTOES);
        campoDataNascimento.setForeground(Estilo.COR_TEXTO);
        ((JTextField)campoDataNascimento.getDateEditor().getUiComponent()).setCaretColor(Estilo.COR_TEXTO);
    }

    private void estilizarComboBox(JComboBox<Perfil> comboIdPerfil) {
        comboIdPerfil.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboIdPerfil.setBackground(Estilo.COR_BOTOES);
        comboIdPerfil.setForeground(Estilo.COR_TEXTO);
        comboIdPerfil.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        comboIdPerfil.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Perfil) {
                    setText(((Perfil) value).getNome());
                }
                return this;
            }
        });
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

    private void acaoBotaoSalvar() {
        if (areAllFieldsFilled(campoUsuario, campoNome, campoIdade, campoSexo, campoCpf,
                campoRua, campoBairro, campoCidade, campoEstado, campoContato, campoEmail, campoSenha)  &&
                campoDataNascimento.getDate() != null && comboIdPerfil.getSelectedItem() != null) {

            Funcionarios funcionario = new Funcionarios();
            funcionario.setUsuario(campoUsuario.getText());
            funcionario.setNome(campoNome.getText());
            funcionario.setIdade(campoIdade.getText());
            funcionario.setSexo(campoSexo.getText());
            funcionario.setCpf(campoCpf.getText());
            funcionario.setRua(campoRua.getText());
            funcionario.setBairro(campoBairro.getText());
            funcionario.setCidade(campoCidade.getText());
            funcionario.setEstado(campoEstado.getText());
            funcionario.setContato(campoContato.getText());
            funcionario.setEmail(campoEmail.getText());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dataNascimentoFormatada = sdf.format(campoDataNascimento.getDate());
            try {
                funcionario.setDataNascimento(LocalDate.parse(dataNascimentoFormatada));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Formato de data de nascimento inválido. Use dd/MM/yyyy.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            funcionario.setSenha(String.valueOf(campoSenha.getPassword()));

            Perfil selectedPerfil = (Perfil) comboIdPerfil.getSelectedItem();
            if (selectedPerfil != null) {
                funcionario.setIdPerfil(selectedPerfil.getId());
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um perfil válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                funcionariosDao.salvar(funcionario);

                System.out.println("Funcionário cadastrado: " + funcionario.getUsuario());

                JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!");
                clearFields();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar funcionário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean areAllFieldsFilled(JTextComponent... fields) {
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
        campoUsuario.setText("");
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
        campoSenha.setText("");
        comboIdPerfil.setSelectedIndex(-1);
    }

    private void inicializarFuncionarioDao() {
        try {
            this.funcionariosDao = new FuncionariosDao();
            System.out.println("DAO de Funcionários inicializado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados para funcionários! Detalhes: " + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            dispose();
        }
    }

    private void inicializarPerfilDao() {
        try {
            this.perfilDao = new PerfilDao();
            System.out.println("DAO de Perfis inicializado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados para perfis! Detalhes: " + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroFuncionarios().setVisible(true));
    }
}