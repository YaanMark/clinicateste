package br.edu.imepac.clinica.medica.outros;

import br.edu.imepac.clinica.medica.daos.PerfilDao;
import br.edu.imepac.clinica.medica.entidades.Funcionarios;
import br.edu.imepac.clinica.medica.entidades.Perfil;
import br.edu.imepac.clinica.medica.convenios.CadastroConvenio;
import br.edu.imepac.clinica.medica.convenios.EditarConvenio;
import br.edu.imepac.clinica.medica.convenios.ListarConvenio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ConveniosFrame extends JFrame {

    private Funcionarios funcionarioLogado;
    private JFrame parentFrame;

    public ConveniosFrame(Funcionarios funcionario, JFrame parent) {
        this.funcionarioLogado = funcionario;
        this.parentFrame = parent;

        setTitle("Gerenciamento de Convênios");
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

        JLabel titleLabel = new JLabel("CONVÊNIOS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Estilo.COR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        Dimension buttonSize = new Dimension(250, 80);

        JButton cadastrarButton = new JButton("Cadastrar Convênio");
        Estilo.estilizarBotao(cadastrarButton);
        cadastrarButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(cadastrarButton, gbc);

        JButton editarButton = new JButton("Editar Convênio");
        Estilo.estilizarBotao(editarButton);
        editarButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(editarButton, gbc);

        JButton listarButton = new JButton("Listar Convênios");
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
                validarEAbrirTela("cadastrar_convenio");
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarEAbrirTela("atualizar_convenio");
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarEAbrirTela("listar_convenio");
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
                case "cadastrar_convenio":
                    podeAcessar = perfilDoFuncionario.isCadastrarFuncionario();
                    break;
                case "atualizar_convenio":
                    podeAcessar = perfilDoFuncionario.isAtualizarFuncionario();
                    break;
                case "listar_convenio":
                    podeAcessar = perfilDoFuncionario.isListarFuncionario();
                    break;
                default:
                    podeAcessar = false;
            }

            if (podeAcessar) {
                switch (permissaoNecessaria) {
                    case "cadastrar_convenio":
                        new CadastroConvenio(this).setVisible(true);
                        break;
                    case "atualizar_convenio":
                        new EditarConvenio(this).setVisible(true);
                        break;
                    case "listar_convenio":
                        new ListarConvenio(this).setVisible(true);
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