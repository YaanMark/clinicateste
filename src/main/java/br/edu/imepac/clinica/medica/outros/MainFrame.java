package br.edu.imepac.clinica.medica.outros;

import br.edu.imepac.clinica.medica.entidades.Funcionarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private Funcionarios funcionarioLogado; // Adicione este campo

    public MainFrame(Funcionarios funcionarioLogado) { // Modifique o construtor
        this.funcionarioLogado = funcionarioLogado; // Atribua o funcionário logado

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
                    // Modifique esta parte para lidar com o botão "Médicos"
                    if (textoBotao.equals("Médicos")) {
                        dispose(); // Esconde a MainFrame
                        new MedicosFrame(funcionarioLogado, MainFrame.this).setVisible(true); // Passa o funcionário logado e a referência da MainFrame
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this, "Você clicou em: " + ((JButton) e.getSource()).getText(), "Navegação", JOptionPane.INFORMATION_MESSAGE);
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

    // Remova o método main daqui, ele será chamado do LoginFrame agora
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         new MainFrame().setVisible(true);
    //     });
    // }
}