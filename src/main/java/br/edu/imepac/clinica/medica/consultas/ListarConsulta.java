package br.edu.imepac.clinica.medica.consultas;

import br.edu.imepac.clinica.medica.daos.ConsultaDao;
import br.edu.imepac.clinica.medica.daos.MedicoDao;
import br.edu.imepac.clinica.medica.daos.PacienteDao;
import br.edu.imepac.clinica.medica.entidades.Consulta;
import br.edu.imepac.clinica.medica.entidades.Medico;
import br.edu.imepac.clinica.medica.entidades.Paciente;
import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter; // Importar WindowAdapter
import java.awt.event.WindowEvent;   // Importar WindowEvent
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListarConsulta extends JFrame {

    private JTable tabelaConsultas;
    private DefaultTableModel tableModel;
    private ConsultaDao consultaDao;
    private PacienteDao pacienteDao;
    private MedicoDao medicoDao;

    private Map<Integer, String> pacienteNomes;
    private Map<Integer, String> medicoNomes;
    private JFrame parentFrame; // Adicionado para referência à tela pai

    // Construtor principal para ser chamado pela tela pai
    public ListarConsulta(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setTitle("Listar Consultas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Estilo.COR_FUNDO);

        // Adiciona um WindowListener para lidar com o fechamento da janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (ListarConsulta.this.parentFrame != null) {
                    ListarConsulta.this.parentFrame.setVisible(true);
                }
            }
        });

        try {
            consultaDao = new ConsultaDao();
            pacienteDao = new PacienteDao();
            medicoDao = new MedicoDao();
            carregarNomesReferencia();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        String[] colunas = {
                "ID", "Data e Hora", "Sintomas", "É Retorno?", "Está Ativa?", "Paciente", "Médico"
        };
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3 || columnIndex == 4) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        tabelaConsultas = new JTable(tableModel);
        tabelaConsultas.setBackground(Estilo.COR_BOTOES);
        tabelaConsultas.setForeground(Estilo.COR_TEXTO);
        tabelaConsultas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaConsultas.getTableHeader().setBackground(Estilo.COR_BORDA_BOTAO);
        tabelaConsultas.getTableHeader().setForeground(Estilo.COR_TEXTO);
        tabelaConsultas.setSelectionBackground(Estilo.COR_FOCO_CAMPO);
        tabelaConsultas.setSelectionForeground(Color.WHITE);

        tabelaConsultas.setRowSelectionAllowed(true);
        tabelaConsultas.setColumnSelectionAllowed(false);
        tabelaConsultas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(tabelaConsultas);
        scrollPane.getViewport().setBackground(Estilo.COR_FUNDO);
        add(scrollPane, BorderLayout.CENTER);

        carregarConsultas();

        JPanel panelBotoes = new JPanel();
        panelBotoes.setBackground(Estilo.COR_FUNDO);
        panelBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton btnExcluir = new JButton("Excluir");
        Estilo.estilizarBotao(btnExcluir);
        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaConsultas.getSelectedRow();
            if (selectedRow >= 0) {
                Object idObject = tableModel.getValueAt(selectedRow, 0);
                Long consultaId;

                try {
                    consultaId = Long.parseLong(idObject.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao obter ID da consulta: Formato inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String consultaInfo = "ID: " + consultaId + " - Data: " + tableModel.getValueAt(selectedRow, 1);

                int confirmResult = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir a consulta: " + consultaInfo + "?",
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    try {
                        consultaDao.deletar(consultaId);
                        JOptionPane.showMessageDialog(this, "Consulta excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarConsultas();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione uma consulta para excluir.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            }
        });
        panelBotoes.add(btnExcluir);

        JButton btnFechar = new JButton("Fechar");
        Estilo.estilizarBotao(btnFechar);
        btnFechar.addActionListener(e -> {
            dispose();
            if (parentFrame != null) { // Adicionado para voltar à tela pai
                parentFrame.setVisible(true);
            }
        });
        panelBotoes.add(btnFechar);

        add(panelBotoes, BorderLayout.SOUTH);
    }

    // Adicionado um construtor sem argumentos para compatibilidade com o main, se necessário
    public ListarConsulta() {
        this(null); // Chama o construtor principal passando null para parentFrame
    }

    private void carregarNomesReferencia() throws SQLException {
        pacienteNomes = new HashMap<>();
        List<Paciente> pacientes = pacienteDao.listarTodos();
        for (Paciente p : pacientes) {
            pacienteNomes.put(p.getId(), p.getNome());
        }

        medicoNomes = new HashMap<>();
        List<Medico> medicos = medicoDao.listarTodos();
        for (Medico m : medicos) {
            medicoNomes.put(m.getId(), m.getNome());
        }
    }

    private void carregarConsultas() {
        tableModel.setRowCount(0);
        try {
            List<Consulta> consultas = consultaDao.listarTodos();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (Consulta c : consultas) {
                String nomePaciente = pacienteNomes.getOrDefault(c.getIdPaciente(), "Desconhecido");
                String nomeMedico = medicoNomes.getOrDefault(c.getIdMedico(), "Desconhecido");

                tableModel.addRow(new Object[]{
                        c.getId(),
                        c.getData().format(formatter),
                        c.getSintomas(),
                        c.iseRetorno(),
                        c.isEstaAtiva(),
                        nomePaciente,
                        nomeMedico
                });
            }
            tabelaConsultas.revalidate();
            tabelaConsultas.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar consultas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ListarConsulta(null).setVisible(true); // Passa null para o parentFrame no main
        });
    }
}