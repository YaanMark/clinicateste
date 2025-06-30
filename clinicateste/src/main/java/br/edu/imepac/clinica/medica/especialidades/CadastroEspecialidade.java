package br.edu.imepac.clinica.medica.especialidades;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import br.edu.imepac.clinica.medica.daos.EspecialidadeDao;
import br.edu.imepac.clinica.medica.entidades.Especialidade;
import br.edu.imepac.clinica.medica.outros.Estilo;

public class CadastroEspecialidade extends JFrame {

    private JTextField campoNomeEspecialidade;
    private JTextArea campoDescricao;
    private JButton botaoSalvar;
    private JButton botaoFechar;

    public CadastroEspecialidade() {
        setTitle("Cadastrar Especialidade");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(Estilo.COR_FUNDO);
        painelPrincipal.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        JLabel labelNome = new JLabel("Nome da Especialidade:");
        labelNome.setForeground(Estilo.COR_TEXTO);
        labelNome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(labelNome, gbc);

        campoNomeEspecialidade = new JTextField(25);
        campoNomeEspecialidade.setBackground(Estilo.COR_BOTOES);
        campoNomeEspecialidade.setForeground(Estilo.COR_TEXTO);
        campoNomeEspecialidade.setCaretColor(Estilo.COR_TEXTO);
        campoNomeEspecialidade.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
        campoNomeEspecialidade.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoNomeEspecialidade.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                campoNomeEspecialidade.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(FocusEvent evt) {
                campoNomeEspecialidade.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(campoNomeEspecialidade, gbc);

        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setForeground(Estilo.COR_TEXTO);
        labelDescricao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(labelDescricao, gbc);

        campoDescricao = new JTextArea(4, 25);
        campoDescricao.setBackground(Estilo.COR_BOTOES);
        campoDescricao.setForeground(Estilo.COR_TEXTO);
        campoDescricao.setCaretColor(Estilo.COR_TEXTO);
        campoDescricao.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        campoDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(campoDescricao);
        scrollPane.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        campoDescricao.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                scrollPane.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(FocusEvent evt) {
                scrollPane.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        painelPrincipal.add(scrollPane, gbc);

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;

        botaoSalvar = new JButton("Salvar");
        Estilo.estilizarBotao(botaoSalvar);
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoBotaoSalvar(e);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(botaoSalvar, gbc);

        botaoFechar = new JButton("Fechar");
        Estilo.estilizarBotao(botaoFechar);
        botaoFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoBotaoFechar();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(botaoFechar, gbc);

        add(painelPrincipal);
    }

    private void acaoBotaoSalvar(java.awt.event.ActionEvent evt) {
        if (validarDadosObrigatorios()) {
            Especialidade especialidade = new Especialidade();
            especialidade.setNome(campoNomeEspecialidade.getText());
            especialidade.setDescricao(campoDescricao.getText());

            try {
                EspecialidadeDao especialidadeDao = new EspecialidadeDao();
                especialidadeDao.salvar(especialidade);
                JOptionPane.showMessageDialog(this, "Especialidade salva com sucesso!");
                campoNomeEspecialidade.setText("");
                campoDescricao.setText("");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao salvar especialidade: " + exception.getMessage(),
                        "Erro de Banco de Dados",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Os campos 'Nome da Especialidade' e 'Descrição' são obrigatórios.",
                    "Dados Incompletos",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void acaoBotaoFechar() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadastroEspecialidade().setVisible(true);
            }
        });
    }

    private boolean validarDadosObrigatorios() {
        boolean nomePreenchido = !campoNomeEspecialidade.getText().trim().isEmpty();
        boolean descricaoPreenchida = !campoDescricao.getText().trim().isEmpty();
        return nomePreenchido && descricaoPreenchida;
    }
}