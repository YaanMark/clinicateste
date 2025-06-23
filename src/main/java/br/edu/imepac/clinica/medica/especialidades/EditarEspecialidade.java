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

        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilo.COR_FUNDO);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblId = new JLabel("ID:");
        lblId.setForeground(Estilo.COR_TEXTO);
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblId, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtId = new JTextField(15);
        estilizarCampoTexto(txtId);
        panel.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setForeground(Estilo.COR_TEXTO);
        lblNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtNome = new JTextField(20);
        estilizarCampoTexto(txtNome);
        panel.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setForeground(Estilo.COR_TEXTO);
        lblDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblDescricao, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescricao = new JTextArea(5, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        estilizarCampoTexto(txtDescricao);
        JScrollPane scrollPane = new JScrollPane(txtDescricao);
        panel.add(scrollPane, gbc);

        // --- Painel para os Botões ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setBackground(Estilo.COR_FUNDO);


        btnEditar = new JButton("Editar");
        Estilo.estilizarBotao(btnEditar);
        buttonPanel.add(btnEditar);

        btnFechar = new JButton("Fechar");
        Estilo.estilizarBotao(btnFechar);
        buttonPanel.add(btnFechar);


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        panel.add(buttonPanel, gbc);


                btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoBotaoEditar();
            }
        });

        btnFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fecharAplicacao();
            }
        });
    }
    private void estilizarCampoTexto(JTextComponent comp) {
        comp.setBackground(Estilo.COR_BOTOES);
        comp.setForeground(Estilo.COR_TEXTO);
        comp.setCaretColor(Estilo.COR_TEXTO);
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
            especialidade.setId(Integer.parseInt(txtId.getText()));

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

        }else {
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
        boolean idPreenchido = !txtDescricao.getText().trim().isEmpty();
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