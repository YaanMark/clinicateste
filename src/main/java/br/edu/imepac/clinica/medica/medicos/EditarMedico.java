package br.edu.imepac.clinica.medica.medicos;

import br.edu.imepac.clinica.medica.outros.Estilo;
import br.edu.imepac.clinica.medica.daos.EspecialidadeDao;
import br.edu.imepac.clinica.medica.daos.MedicoDao;
import br.edu.imepac.clinica.medica.entidades.Especialidade;
import br.edu.imepac.clinica.medica.entidades.Medico;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class EditarMedico extends JFrame {

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtCRM;
    private JComboBox<Especialidade> listaEspecialidade;
    private JButton btnEditar;
    private JButton btnFechar;

    private EspecialidadeDao especialidadeDao;
    private MedicoDao medicoDao;
    private JFrame parentFrame;

    public EditarMedico(JFrame parentFrame) {
        super("Editar Médico");
        this.parentFrame = parentFrame;

        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Estilo.COR_FUNDO);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        JLabel lblId = new JLabel("ID do Médico:");
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

        JLabel lblNome = new JLabel("Nome do Médico:");
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

        JLabel labelCrm = new JLabel("CRM:");
        labelCrm.setForeground(Estilo.COR_TEXTO);
        labelCrm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelCrm, gbc);

        txtCRM = new JTextField(25);
        estilizarCampoTexto(txtCRM);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtCRM, gbc);

        JLabel labelIdEspecialidade = new JLabel("Especialidade:");
        labelIdEspecialidade.setForeground(Estilo.COR_TEXTO);
        labelIdEspecialidade.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelIdEspecialidade, gbc);

        listaEspecialidade = new JComboBox<>();
        listaEspecialidade.setBackground(Estilo.COR_BOTOES);
        listaEspecialidade.setForeground(Estilo.COR_TEXTO);
        listaEspecialidade.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        listaEspecialidade.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        listaEspecialidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                listaEspecialidade.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                listaEspecialidade.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(listaEspecialidade, gbc);

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;

        btnEditar = new JButton("Editar");
        Estilo.estilizarBotao(btnEditar);
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botaoEditar();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 8;
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
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(btnFechar, gbc);

        inicializarMedicoDao();
        inicializarEpecialidadeDao();
        carregarEspecialidades();
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

    private void carregarEspecialidades() {
        try {
            List<Especialidade> especialidadesList = especialidadeDao.listarTodas();
            if (especialidadesList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma especialidade cadastrada! Cadastre uma especialidade antes de editar um médico.");
                dispose();
                return;
            }
            especialidadesList.forEach(this.listaEspecialidade::addItem);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar especialidades!" + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(exception.getMessage());
            dispose();
        }
    }

    private void inicializarEpecialidadeDao() {
        try {
            this.especialidadeDao = new EspecialidadeDao();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados - especialidades!");
            dispose();
        }
    }

    private void inicializarMedicoDao() {
        try {
            this.medicoDao = new MedicoDao();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Erro ao inicializar ao banco de dados - médico!");
            dispose();
        }
    }

    private boolean validarDadosObrigatorios() {
        boolean idPreenchido = !txtId.getText().trim().isEmpty();
        boolean nomePreenchido = !txtNome.getText().trim().isEmpty();
        boolean crmPreenchido = !txtCRM.getText().trim().isEmpty();
        boolean especialidadePreenchida = listaEspecialidade.getSelectedItem() != null;
        return nomePreenchido && crmPreenchido && especialidadePreenchida && idPreenchido;
    }

    private void botaoEditar() {
        if (validarDadosObrigatorios()) {
            Medico medico = new Medico();
            medico.setNome(txtNome.getText());
            medico.setCrm(txtCRM.getText());
            try {
                medico.setId(Integer.parseInt(txtId.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, insira um número para o ID.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            medico.setEspecialidade((Especialidade) listaEspecialidade.getSelectedItem());

            try {
                medicoDao.atualizar(medico);
                JOptionPane.showMessageDialog(this, "Médico atualizado com sucesso!");
                txtId.setText("");
                txtNome.setText("");
                txtCRM.setText("");
                listaEspecialidade.setSelectedIndex(0);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao atualizar médico: " + e.getMessage(),
                        "Erro de banco de dados",
                        JOptionPane.ERROR_MESSAGE
                );
                System.err.println("Erro ao atualizar médico: " + e.getMessage());
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
        if (parentFrame != null) {
            parentFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EditarMedico(null).setVisible(true);
        });
    }
}