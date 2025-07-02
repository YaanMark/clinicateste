package br.edu.imepac.clinica.medica.outros;

import br.edu.imepac.clinica.medica.daos.FuncionariosDao;
import br.edu.imepac.clinica.medica.entidades.Funcionarios;
import br.edu.imepac.clinica.medica.outros.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Sistema Kairós");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Estilo.COR_FUNDO);
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("ACESSO AO SISTEMA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Estilo.COR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        JLabel usuarioLabel = new JLabel("Usuário:");
        usuarioLabel.setForeground(Estilo.COR_TEXTO);
        usuarioLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usuarioLabel, gbc);

        usuarioField = new JTextField(20);
        usuarioField.setBackground(Estilo.COR_BOTOES);
        usuarioField.setForeground(Estilo.COR_TEXTO);
        usuarioField.setCaretColor(Estilo.COR_TEXTO);
        usuarioField.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(usuarioField, gbc);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setForeground(Estilo.COR_TEXTO);
        senhaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(senhaLabel, gbc);

        senhaField = new JPasswordField(20);
        senhaField.setBackground(Estilo.COR_BOTOES);
        senhaField.setForeground(Estilo.COR_TEXTO);
        senhaField.setCaretColor(Estilo.COR_TEXTO);
        senhaField.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(senhaField, gbc);

        loginButton = new JButton("Entrar");
        Estilo.estilizarBotao(loginButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
    }

    private void realizarLogin() {
        String usuario = usuarioField.getText();
        String senha = new String(senhaField.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro de Login", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            FuncionariosDao dao = new FuncionariosDao();
            Funcionarios funcionarioLogado = dao.validarLogin(usuario, senha);

            if (funcionarioLogado != null) {

                SwingUtilities.invokeLater(() -> {
                    new MainFrame(funcionarioLogado).setVisible(true); // Passa o funcionário logado
                });

            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}