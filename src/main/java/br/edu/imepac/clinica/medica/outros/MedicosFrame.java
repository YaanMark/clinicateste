package br.edu.imepac.clinica.medica.outros;

import br.edu.imepac.clinica.medica.daos.PerfilDao;
import br.edu.imepac.clinica.medica.entidades.Funcionarios;
import br.edu.imepac.clinica.medica.entidades.Perfil;
import br.edu.imepac.clinica.medica.medicos.CadastroMedico;
import br.edu.imepac.clinica.medica.medicos.EditarMedico;
import br.edu.imepac.clinica.medica.medicos.ListarMedico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MedicosFrame extends JFrame {

    private Funcionarios funcionarioLogado;
    private JFrame parentFrame; // Referência para a MainFrame

    public MedicosFrame(Funcionarios funcionario, JFrame parent) {
        this.funcionarioLogado = funcionario;
        this.parentFrame = parent;

        setTitle("Gerenciamento de Médicos");
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

        JLabel titleLabel = new JLabel("MÉDICOS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Estilo.COR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Aumentando o tamanho dos botões
        Dimension buttonSize = new Dimension(250, 80); // Ajuste estes valores para o tamanho desejado

        JButton cadastrarButton = new JButton("Cadastrar Médico");
        Estilo.estilizarBotao(cadastrarButton);
        cadastrarButton.setPreferredSize(buttonSize); // Define o tamanho preferencial
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(cadastrarButton, gbc);

        JButton editarButton = new JButton("Editar Médico");
        Estilo.estilizarBotao(editarButton);
        editarButton.setPreferredSize(buttonSize); // Define o tamanho preferencial
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(editarButton, gbc);

        JButton listarButton = new JButton("Listar Médicos");
        Estilo.estilizarBotao(listarButton);
        listarButton.setPreferredSize(buttonSize); // Define o tamanho preferencial
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(listarButton, gbc);

        JButton voltarButton = new JButton("Voltar");
        Estilo.estilizarBotao(voltarButton);
        voltarButton.setPreferredSize(buttonSize); // Define o tamanho preferencial
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(voltarButton, gbc);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarEAbrirTela("cadastrar_medico");
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarEAbrirTela("atualizar_medico");
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarEAbrirTela("listar_medico");
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a tela atual
                parentFrame.setVisible(true); // Torna a MainFrame visível novamente
            }
        });
    }

    private void validarEAbrirTela(String permissaoNecessaria) {
        try {
            PerfilDao perfilDao = new PerfilDao();
            Perfil perfilDoFuncionario = perfilDao.buscar(funcionarioLogado.getIdPerfil());

            boolean podeAcessar = false;

            switch (permissaoNecessaria) {
                case "cadastrar_medico":
                    // Você deve mudar isso para isCadastrarMedico() após adicionar a permissão no Perfil.java
                    podeAcessar = perfilDoFuncionario.isCadastrarFuncionario();
                    break;
                case "atualizar_medico":
                    // Você deve mudar isso para isAtualizarMedico() após adicionar a permissão no Perfil.java
                    podeAcessar = perfilDoFuncionario.isAtualizarFuncionario();
                    break;
                case "listar_medico":
                    // Você deve mudar isso para isListarMedico() após adicionar a permissão no Perfil.java
                    podeAcessar = perfilDoFuncionario.isListarFuncionario();
                    break;
                default:
                    podeAcessar = false;
            }


            if (podeAcessar) {
                // Abrir a tela correspondente
                switch (permissaoNecessaria) {
                    case "cadastrar_medico":
                        new CadastroMedico(this).setVisible(true);
                        break;
                    case "atualizar_medico":
                        new EditarMedico(this).setVisible(true);
                        break;
                    case "listar_medico":
                        new ListarMedico(this).setVisible(true);
                        break;
                }
                this.setVisible(false); // Esconde a tela atual
            } else {
                JOptionPane.showMessageDialog(this, "Você não tem permissão para realizar esta ação.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao verificar permissões: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}