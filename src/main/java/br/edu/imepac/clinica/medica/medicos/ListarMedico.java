package br.edu.imepac.clinica.medica.medicos;

import br.edu.imepac.clinica.medica.daos.EspecialidadeDao;
import br.edu.imepac.clinica.medica.daos.MedicoDao;
import br.edu.imepac.clinica.medica.entidades.Especialidade;
import br.edu.imepac.clinica.medica.entidades.Medico;
import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ListarMedico extends JFrame {

    private JTable tabelaMedico;
    private DefaultTableModel tableModel;
    private MedicoDao medicoDao;
    private EspecialidadeDao especialidadeDao;
    private Especialidade especialidade;
    private JFrame parentFrame;

    public ListarMedico(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setTitle("Listar Medicos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);


        setLayout(new BorderLayout());
        getContentPane().setBackground(Estilo.COR_FUNDO);

        try {
            especialidadeDao = new EspecialidadeDao();
            medicoDao = new MedicoDao();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        String[] colunas = {"ID", "Nome", "CRM", "Especialidade"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaMedico = new JTable(tableModel);
        tabelaMedico.setBackground(Estilo.COR_BOTOES);
        tabelaMedico.setForeground(Estilo.COR_TEXTO);
        tabelaMedico.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaMedico.getTableHeader().setBackground(Estilo.COR_BORDA_BOTAO);
        tabelaMedico.getTableHeader().setForeground(Estilo.COR_TEXTO);
        tabelaMedico.setSelectionBackground(Estilo.COR_BORDA_BOTAO);
        tabelaMedico.setSelectionForeground(Color.WHITE);

        tabelaMedico.setRowSelectionAllowed(true);
        tabelaMedico.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelaMedico);
        scrollPane.getViewport().setBackground(Estilo.COR_FUNDO);
        add(scrollPane, BorderLayout.CENTER);

        carregarMedicos();

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton botaoExcluir = new JButton("Excluir");
        Estilo.estilizarBotao(botaoExcluir);
        botaoExcluir.addActionListener(e -> {
            int selectedRow = tabelaMedico.getSelectedRow();
            if (selectedRow >= 0) {
                Object idObject = tableModel.getValueAt(selectedRow, 0);
                Long medicoId;

                if (idObject instanceof Integer) {
                    medicoId = ((Integer) idObject).longValue();
                } else if (idObject instanceof Long) {
                    medicoId = (Long) idObject;
                } else {
                    try {
                        medicoId = Long.parseLong(idObject.toString());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao obter ID do médico: Formato inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String medicoNome = (String) tableModel.getValueAt(selectedRow, 1);
                int confirmResult = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir o médico: " + medicoNome,
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    try {
                        medicoDao.deletar(medicoId);
                        JOptionPane.showMessageDialog(this, "Médico excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarMedicos();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir médico: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um médico para excluir.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            }
        });
        panel.add(botaoExcluir);

        JButton botaoFechar = new JButton("Fechar");
        Estilo.estilizarBotao(botaoFechar);
        botaoFechar.addActionListener(e -> {
            dispose();
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
        });
        panel.add(botaoFechar);

        add(panel, BorderLayout.SOUTH);
    }

    private void carregarMedicos() {
        tableModel.setRowCount(0);
        try {
            List<Medico> medicos = medicoDao.listarTodos();
            for (Medico m : medicos) {
                Especialidade especialidade = new Especialidade();
                int idEspecialidade = m.getEspecialidadeId();
                especialidade = especialidadeDao.buscarPorId(idEspecialidade);
                tableModel.addRow(new Object[]{m.getId(), m.getNome(), m.getCrm(), especialidade.getNome()});
            }
            tabelaMedico.revalidate();
            tabelaMedico.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar medicos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ListarMedico(null).setVisible(true);
        });
    }

}