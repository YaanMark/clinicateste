package br.edu.imepac.clinica.medica.medicos;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import br.edu.imepac.clinica.medica.daos.MedicoDao;
import br.edu.imepac.clinica.medica.entidades.Medico;
import br.edu.imepac.clinica.medica.daos.EspecialidadeDao;
import br.edu.imepac.clinica.medica.entidades.Especialidade;
import br.edu.imepac.clinica.medica.outros.Estilo;

public class CadastroMedico extends JFrame {

    private JTextField campoNome;
    private JTextField campoCrm;
    private JComboBox campoIdEspcialidade;
    private JButton botaoSalvar;
    private JButton botaoFechar;

    private EspecialidadeDao especialidadeDao;
    private MedicoDao medicoDao;

    public CadastroMedico() {

        inicializarMedicoDao();
        inicializarEpecialidadeDao();

        setTitle("Cadastro de Medicos");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        JLabel labelNome =  new JLabel("Nome do médico:");
        labelNome.setForeground(Estilo.COR_TEXTO);
        labelNome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor =  GridBagConstraints.WEST;
        panel.add(labelNome, gbc);

        campoNome =  new JTextField(25);
        campoNome.setBackground(Estilo.COR_BOTOES);
        campoNome.setForeground(Estilo.COR_TEXTO);
        campoNome.setCaretColor(Estilo.COR_TEXTO);
        campoNome.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campoNome.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoNome.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoNome.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campoNome, gbc);

        JLabel labelCrm =  new JLabel("Crm:");
        labelCrm.setForeground(Estilo.COR_TEXTO);
        labelCrm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.WEST;
        panel.add(labelCrm, gbc);

        campoCrm =  new JTextField(25);
        campoCrm.setBackground(Estilo.COR_BOTOES);
        campoCrm.setForeground(Estilo.COR_TEXTO);
        campoCrm.setCaretColor(Estilo.COR_TEXTO);
        campoCrm.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campoCrm.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoCrm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoCrm.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoCrm.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campoCrm, gbc);

        JLabel labelEspecialidade =  new JLabel("Especialidade ID:");
        labelEspecialidade.setForeground(Estilo.COR_TEXTO);
        labelEspecialidade.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.WEST;
        panel.add(labelEspecialidade, gbc);

        campoIdEspcialidade =  new JComboBox();
        campoIdEspcialidade.setBackground(Estilo.COR_BOTOES);
        campoIdEspcialidade.setForeground(Estilo.COR_TEXTO);
        campoIdEspcialidade.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
        campoIdEspcialidade.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoIdEspcialidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoIdEspcialidade.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoIdEspcialidade.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campoIdEspcialidade, gbc);

        carregarEspecialidades();

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
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(botaoSalvar, gbc);

        botaoFechar = new JButton("Fechar");
        Estilo.estilizarBotao(botaoFechar);
        botaoFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoBotaoFechar();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(botaoFechar, gbc);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadastroMedico().setVisible(true);
            }
        });
    }

    private void acaoBotaoSalvar(ActionEvent e) {
        if(validarDadosObrigatorios()) {
            Medico medico = new Medico();
            medico.setNome(campoNome.getText());
            medico.setCrm(campoCrm.getText());
            medico.setEspecialidade((Especialidade) campoIdEspcialidade.getSelectedItem());
        }

    }

    private void acaoBotaoFechar() {
        dispose();
    }

    private boolean validarDadosObrigatorios() {
        boolean nomePreenchido = !campoNome.getText().trim().isEmpty();
        boolean descricaoPreenchida = !campoCrm.getText().trim().isEmpty();
        boolean especialidadeSelecionada = campoIdEspcialidade.getSelectedItem() != null;
        return nomePreenchido && descricaoPreenchida && especialidadeSelecionada;
    }

    private void inicializarEpecialidadeDao() {
        try {
            this.especialidadeDao = new EspecialidadeDao();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados - especialidades!");
            dispose();
        }
    }

    private void inicializarMedicoDao() {
        try {
            this.medicoDao = new MedicoDao();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Erro ao inicializar ao banco de dados - médico!");
            dispose();
        }
    }

    private void carregarEspecialidades() {
        try {
            List<Especialidade> especialidadesList = especialidadeDao.listarTodas();
            if (especialidadesList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhuma especialidade cadastrada! Cadastre uma especialidade antes de cadastrar um médico.");
                dispose();
                return;
            }
            especialidadesList.forEach(especialidade -> {
                this.campoIdEspcialidade.addItem(especialidade);
            });
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar especialidades!" + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

}
