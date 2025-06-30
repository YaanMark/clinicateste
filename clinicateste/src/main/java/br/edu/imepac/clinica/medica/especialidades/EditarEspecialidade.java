package br.edu.imepac.clinica.medica.especialidades;

import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import br.edu.imepac.clinica.medica.daos.EspecialidadeDao;
import br.edu.imepac.clinica.medica.entidades.Especialidade;

public class EditarEspecialidade extends JFrame {

    private JTextField txtId;
    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JButton btnEditar;
    private JButton btnFechar;

    public EditarEspecialidade() {
        super("Editar Especialidade");

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Estilo.COR_FUNDO);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        JLabel lblId = new JLabel("ID da Especialidade:");
        lblId.setForeground(Estilo.COR_TEXTO);
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblId, gbc);

        txtId = new JTextField(25);
        estilizarCampoTexto(txtId);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtId, gbc);

        JLabel lblNome = new JLabel("Nome da Especialidade:");
        lblNome.setForeground(Estilo.COR_TEXTO);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblNome, gbc);

        txtNome = new JTextField(25);
        estilizarCampoTexto(txtNome);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtNome, gbc);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setForeground(Estilo.COR_TEXTO);
        lblDescricao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblDescricao, gbc);

        txtDescricao = new JTextArea(4, 25);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        estilizarCampoTexto(txtDescricao);
        JScrollPane scrollPane = new JScrollPane(txtDescricao);
        scrollPane.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        txtDescricao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                scrollPane.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                scrollPane.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;

        btnEditar = new JButton("Editar");
        Estilo.estilizarBotao(btnEditar);
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoBotaoEditar();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnEditar, gbc);

        btnFechar = new JButton("Fechar");
        Estilo.estilizarBotao(btnFechar);
        btnFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fecharAplicacao();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(btnFechar, gbc);
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

    private void acaoBotaoEditar() {
        if (validarDadosObrigatorios()) {
            Especialidade especialidade = new Especialidade();
            especialidade.setNome(txtNome.getText());
            especialidade.setDescricao(txtDescricao.getText());
            try {
                especialidade.setId(Integer.parseInt(txtId.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, insira um número para o ID.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                EspecialidadeDao especialidadeDao = new EspecialidadeDao();
                especialidadeDao.atualizar(especialidade);
                JOptionPane.showMessageDialog(this, "Especialidade atualizada com sucesso!");
                txtId.setText("");
                txtNome.setText("");
                txtDescricao.setText("");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao atualizar especialidade: " + exception.getMessage(),
                        "Erro de Banco de Dados",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Todos os campos são obrigatórios",
                    "Dados Incompletos",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void fecharAplicacao() {
        this.dispose();
    }

    private boolean validarDadosObrigatorios() {
        boolean idPreenchido = !txtId.getText().trim().isEmpty();
        boolean nomePreenchido = !txtNome.getText().trim().isEmpty();
        boolean descricaoPreenchida = !txtDescricao.getText().trim().isEmpty();
        return nomePreenchido && descricaoPreenchida && idPreenchido;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EditarEspecialidade().setVisible(true);
        });
    }
}