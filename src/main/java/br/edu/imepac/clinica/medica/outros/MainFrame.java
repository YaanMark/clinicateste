package br.edu.imepac.clinica.medica.outros;

import br.edu.imepac.clinica.medica.entidades.Funcionarios;
import br.edu.imepac.clinica.medica.outros.ConsultasFrame;
import br.edu.imepac.clinica.medica.outros.ConveniosFrame;
import br.edu.imepac.clinica.medica.outros.EspecialidadesFrame;
import br.edu.imepac.clinica.medica.outros.FuncionariosFrame;
import br.edu.imepac.clinica.medica.outros.MedicosFrame;
import br.edu.imepac.clinica.medica.outros.PacientesFrame;
import br.edu.imepac.clinica.medica.outros.PerfisFrame;
import br.edu.imepac.clinica.medica.outros.ProntuariosFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private Funcionarios funcionarioLogado;

    public MainFrame(Funcionarios funcionarioLogado) {
        this.funcionarioLogado = funcionarioLogado;

        setTitle("Sistema Kairós");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Estilo.COR_FUNDO);
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("BEM-VINDO AO SISTEMA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        titleLabel.setForeground(Estilo.COR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        String[] botoes = {"Consultas", "Convênios", "Especialidades", "Funcionários", "Médicos", "Pacientes", "Perfis", "Prontuários"};

        int row = 1;
        int col = 0;
        int maxCols = 2;

        for (String textoBotao : botoes) {
            JButton button = new JButton(textoBotao);
            Estilo.estilizarBotao(button);
            button.setPreferredSize(new Dimension(200, 60));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (textoBotao.equals("Médicos")) {
                        dispose();
                        new MedicosFrame(funcionarioLogado, MainFrame.this).setVisible(true);
                    } else if (textoBotao.equals("Consultas")) {
                        dispose();
                        new ConsultasFrame(funcionarioLogado, MainFrame.this).setVisible(true);
                    } else if (textoBotao.equals("Convênios")) {
                        dispose();
                        new ConveniosFrame(funcionarioLogado, MainFrame.this).setVisible(true);
                    } else if (textoBotao.equals("Especialidades")) {
                        dispose();
                        new EspecialidadesFrame(funcionarioLogado, MainFrame.this).setVisible(true);
                    } else if (textoBotao.equals("Funcionários")) {
                        dispose();
                        new FuncionariosFrame(funcionarioLogado, MainFrame.this).setVisible(true);
                    } else if (textoBotao.equals("Pacientes")) {
                        dispose();
                        new PacientesFrame(funcionarioLogado, MainFrame.this).setVisible(true);
                    } else if (textoBotao.equals("Perfis")) {
                        dispose();
                        new PerfisFrame(funcionarioLogado, MainFrame.this).setVisible(true);
                    } else if (textoBotao.equals("Prontuários")) {
                        dispose();
                        new ProntuariosFrame(funcionarioLogado, MainFrame.this).setVisible(true);
                    }
                }
            });

            gbc.gridx = col;
            gbc.gridy = row;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.weightx = 0.5;

            mainPanel.add(button, gbc);

            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
    }
}