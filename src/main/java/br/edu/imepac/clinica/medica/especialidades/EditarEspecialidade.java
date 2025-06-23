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

        // Configurações básicas da janela
        setSize(450, 400); // Tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar a janela
        setLocationRelativeTo(null); // Centraliza a janela na tela
        getContentPane().setBackground(Estilo.COR_FUNDO); // Define a cor de fundo do conteúdo da janela

        // Painel principal com GridBagLayout para flexibilidade
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilo.COR_FUNDO); // Cor de fundo do painel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche horizontalmente

        // --- Campo ID ---
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        JLabel lblId = new JLabel("ID:");
        lblId.setForeground(Estilo.COR_TEXTO); // Cor do texto do label
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblId, gbc);

        gbc.gridx = 1; // Coluna 1
        gbc.gridy = 0; // Linha 0
        txtId = new JTextField(15); // Tamanho preferencial do campo
        estilizarCampoTexto(txtId); // Aplica o estilo personalizado ao campo
        panel.add(txtId, gbc);

        // --- Campo Nome ---
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 1; // Linha 1
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setForeground(Estilo.COR_TEXTO);
        lblNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblNome, gbc);

        gbc.gridx = 1; // Coluna 1
        gbc.gridy = 1; // Linha 1
        txtNome = new JTextField(20);
        estilizarCampoTexto(txtNome);
        panel.add(txtNome, gbc);

        // --- Campo Descrição ---
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 2; // Linha 2
        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setForeground(Estilo.COR_TEXTO);
        lblDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblDescricao, gbc);

        gbc.gridx = 1; // Coluna 1
        gbc.gridy = 2; // Linha 2
        gbc.gridwidth = 1; // Ocupa uma coluna
        gbc.weighty = 0.5; // Permite que a área de texto se expanda verticalmente
        gbc.fill = GridBagConstraints.BOTH; // Preenche em ambas as direções (horizontal e vertical)
        txtDescricao = new JTextArea(5, 20); // 5 linhas, 20 colunas
        txtDescricao.setLineWrap(true); // Quebra de linha automática
        txtDescricao.setWrapStyleWord(true); // Quebra de linha por palavra
        estilizarCampoTexto(txtDescricao); // Aplica o estilo personalizado
        JScrollPane scrollPane = new JScrollPane(txtDescricao); // Adiciona barra de rolagem
        panel.add(scrollPane, gbc);

        // --- Painel para os Botões ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5)); // Centraliza, 15px de espaço
        buttonPanel.setBackground(Estilo.COR_FUNDO); // Cor de fundo do painel de botões


        btnEditar = new JButton("Editar");
        Estilo.estilizarBotao(btnEditar);
        buttonPanel.add(btnEditar);

        btnFechar = new JButton("Fechar");
        Estilo.estilizarBotao(btnFechar);
        buttonPanel.add(btnFechar);

        // Adiciona o painel de botões ao painel principal
        gbc.gridx = 0;
        gbc.gridy = 3; // Posição abaixo da descrição
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.fill = GridBagConstraints.HORIZONTAL; // Para que o buttonPanel ocupe a largura
        gbc.weighty = 0; // Reseta o peso vertical
        panel.add(buttonPanel, gbc);


        // --- Ações dos Botões ---
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

    // --- Métodos para os botões ---

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
        this.dispose(); // Libera os recursos da janela
        System.exit(0); // Encerra a aplicação Java completamente
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