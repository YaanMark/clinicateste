package br.edu.imepac.clinica.medica.prontuarios;

import br.edu.imepac.clinica.medica.daos.ConsultaDao;
import br.edu.imepac.clinica.medica.daos.MedicoDao;
import br.edu.imepac.clinica.medica.daos.PacienteDao;
import br.edu.imepac.clinica.medica.daos.ProntuarioDao;
import br.edu.imepac.clinica.medica.entidades.Consulta;
import br.edu.imepac.clinica.medica.entidades.Medico;
import br.edu.imepac.clinica.medica.entidades.Paciente;
import br.edu.imepac.clinica.medica.entidades.Prontuario;
import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ListarProntuario extends JFrame {

    private JTable tabelaProntuario;
    private DefaultTableModel tableModel;
    private ProntuarioDao prontuarioDao;
    private MedicoDao medicoDao;
    private PacienteDao pacienteDao;
    private ConsultaDao consultaDao;

    public ListarProntuario() {
        setTitle("Listar Prontuários");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Estilo.COR_FUNDO);

        try {
            prontuarioDao = new ProntuarioDao();
            medicoDao = new MedicoDao();
            pacienteDao = new PacienteDao();
            consultaDao = new ConsultaDao();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        String[] colunas = {"ID Prontuário", "Receituário", "Exames", "Observações", "ID Consulta", "Médico", "Paciente"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProntuario = new JTable(tableModel);
        tabelaProntuario.setBackground(Estilo.COR_BOTOES);
        tabelaProntuario.setForeground(Estilo.COR_TEXTO);
        tabelaProntuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaProntuario.getTableHeader().setBackground(Estilo.COR_BORDA_BOTAO);
        tabelaProntuario.getTableHeader().setForeground(Estilo.COR_TEXTO);
        tabelaProntuario.setSelectionBackground(Estilo.COR_BORDA_BOTAO);
        tabelaProntuario.setSelectionForeground(Color.WHITE);

        tabelaProntuario.setRowSelectionAllowed(true);
        tabelaProntuario.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelaProntuario);
        scrollPane.getViewport().setBackground(Estilo.COR_FUNDO);
        add(scrollPane, BorderLayout.CENTER);

        carregarProntuarios();

        JPanel panelBotoes = new JPanel();
        panelBotoes.setBackground(Estilo.COR_FUNDO);
        panelBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton botaoExcluir = new JButton("Excluir");
        Estilo.estilizarBotao(botaoExcluir);
        botaoExcluir.addActionListener(e -> {
            int selectedRow = tabelaProntuario.getSelectedRow();
            if (selectedRow >= 0) {
                Object idObject = tableModel.getValueAt(selectedRow, 0);
                Long prontuarioId;

                try {
                    prontuarioId = Long.parseLong(idObject.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao obter ID do prontuário: Formato inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirmResult = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir o prontuário ID: " + prontuarioId + "?",
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    try {
                        prontuarioDao.deletar(prontuarioId);
                        JOptionPane.showMessageDialog(this, "Prontuário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarProntuarios();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir prontuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um prontuário para excluir.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            }
        });
        panelBotoes.add(botaoExcluir);

        JButton botaoFechar = new JButton("Fechar");
        Estilo.estilizarBotao(botaoFechar);
        botaoFechar.addActionListener(e -> dispose());
        panelBotoes.add(botaoFechar);

        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void carregarProntuarios() {
        tableModel.setRowCount(0);
        try {
            List<Prontuario> prontuarios = prontuarioDao.listarTodos();
            for (Prontuario p : prontuarios) {
                String nomeMedico = "Não encontrado";
                String nomePaciente = "Não encontrado";

                try {
                    Medico medico = medicoDao.buscarPorId(p.getId_medico());
                    if (medico != null) {
                        nomeMedico = medico.getNome();
                    }
                } catch (SQLException e) {
                    System.err.println("Erro ao buscar médico com ID " + p.getId_medico() + ": " + e.getMessage());
                }

                try {
                    Paciente paciente = pacienteDao.buscarPorId(p.getId_paciente());
                    if (paciente != null) {
                        nomePaciente = paciente.getNome();
                    }
                } catch (SQLException e) {
                    System.err.println("Erro ao buscar paciente com ID " + p.getId_paciente() + ": " + e.getMessage());
                }

                tableModel.addRow(new Object[]{
                        p.getId(),
                        p.getReceituario(),
                        p.getExames(),
                        p.getObservacoes(),
                        p.getId_consulta(),
                        nomeMedico,
                        nomePaciente
                });
            }
            tabelaProntuario.revalidate();
            tabelaProntuario.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar prontuários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ListarProntuario().setVisible(true);
        });
    }
}