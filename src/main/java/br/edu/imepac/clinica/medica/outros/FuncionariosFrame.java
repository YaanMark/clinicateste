package br.edu.imepac.clinica.medica.outros;

import br.edu.imepac.clinica.medica.daos.PerfilDao;
import br.edu.imepac.clinica.medica.entidades.Funcionarios;
import br.edu.imepac.clinica.medica.entidades.Perfil;
import br.edu.imepac.clinica.medica.funcionarios.CadastroFuncionarios;
import br.edu.imepac.clinica.medica.funcionarios.EditarFuncionarios;
import br.edu.imepac.clinica.medica.funcionarios.ListarFuncionarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FuncionariosFrame extends JFrame {

    private Funcionarios funcionarioLogado;
    private JFrame parentFrame;

    public FuncionariosFrame(Funcionarios funcionario, JFrame parent) {
        this.funcionarioLogado = funcionario;
        this.parentFrame = parent;

        setTitle("Gerenciamento de Funcionários");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);


        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Estilo.COR_FUNDO);
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("FUNCIONÁRIOS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Estilo.COR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        Dimension buttonSize = new Dimension(250, 80);

        JButton cadastrarButton = new JButton("Cadastrar Funcionário");
        Estilo.estilizarBotao(cadastrarButton);
        cadastrarButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(cadastrarButton, gbc);

        JButton editarButton = new JButton("Editar Funcionário");
        Estilo.estilizarBotao(editarButton);
        editarButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(editarButton, gbc);

        JButton listarButton = new JButton("Listar Funcionários");
        Estilo.estilizarBotao(listarButton);
        listarButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(listarButton, gbc);

        JButton voltarButton = new JButton("Voltar");
        Estilo.estilizarBotao(voltarButton);
        voltarButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(voltarButton, gbc);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarEAbrirTela("cadastrar_funcionario");
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarEAbrirTela("atualizar_funcionario");
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarEAbrirTela("listar_funcionario");
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                parentFrame.setVisible(true);
            }
        });
    }

    private void validarEAbrirTela(String permissaoNecessaria) {
        try {
            PerfilDao perfilDao = new PerfilDao();
            Perfil perfilDoFuncionario = perfilDao.buscar(funcionarioLogado.getIdPerfil());

            boolean podeAcessar = false;

            switch (permissaoNecessaria) {
                case "cadastrar_funcionario":
                    podeAcessar = perfilDoFuncionario.isCadastrarFuncionario();
                    break;
                case "atualizar_funcionario":
                    podeAcessar = perfilDoFuncionario.isAtualizarFuncionario();
                    break;
                case "listar_funcionario":
                    podeAcessar = perfilDoFuncionario.isListarFuncionario();
                    break;
                default:
                    podeAcessar = false;
            }


            if (podeAcessar) {
                switch (permissaoNecessaria) {
                    case "cadastrar_funcionario":
                        new CadastroFuncionarios(this).setVisible(true);
                        break;
                    case "atualizar_funcionario":
                        new EditarFuncionarios(this).setVisible(true);
                        break;
                    case "listar_funcionario":
                        new ListarFuncionarios(this).setVisible(true);
                        break;
                }
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Você não tem permissão para realizar esta ação.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao verificar permissões: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}