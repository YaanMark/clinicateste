package br.edu.imepac.clinica.medica.especialidades;

import br.edu.imepac.clinica.medica.daos.EspecialidadeDao;
import br.edu.imepac.clinica.medica.entidades.Especialidade;
import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ListarEspecialidade extends JFrame {

    private JTable tabelaEspecialidades;
    private DefaultTableModel tableModel;
    private EspecialidadeDao especialidadeDao;

    public ListarEspecialidade() {
        setTitle("Listar Especialidades");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Estilo.COR_FUNDO);

        try {
            especialidadeDao = new EspecialidadeDao();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        String[] colunas = {"ID", "Nome", "Descrição"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEspecialidades = new JTable(tableModel);
        tabelaEspecialidades.setBackground(Estilo.COR_BOTOES);
        tabelaEspecialidades.setForeground(Estilo.COR_TEXTO);
        tabelaEspecialidades.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaEspecialidades.getTableHeader().setBackground(Estilo.COR_BORDA_BOTAO);
        tabelaEspecialidades.getTableHeader().setForeground(Estilo.COR_TEXTO);
        tabelaEspecialidades.setSelectionBackground(Estilo.COR_FOCO_CAMPO);
        tabelaEspecialidades.setSelectionForeground(Color.WHITE);


        tabelaEspecialidades.setRowSelectionAllowed(true);
        tabelaEspecialidades.setColumnSelectionAllowed(false);


        JScrollPane scrollPane = new JScrollPane(tabelaEspecialidades);
        scrollPane.getViewport().setBackground(Estilo.COR_FUNDO);
        add(scrollPane, BorderLayout.CENTER);

        carregarEspecialidades();

        JPanel panelBotoes = new JPanel();
        panelBotoes.setBackground(Estilo.COR_FUNDO);
        panelBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton btnExcluir = new JButton("Excluir");
        Estilo.estilizarBotao(btnExcluir);
        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaEspecialidades.getSelectedRow();
            if (selectedRow >= 0) {
                // Safely retrieve the ID, handling potential Integer to Long cast
                Object idObject = tableModel.getValueAt(selectedRow, 0);
                Long especialidadeId;

                if (idObject instanceof Integer) {
                    especialidadeId = ((Integer) idObject).longValue();
                } else if (idObject instanceof Long) {
                    especialidadeId = (Long) idObject;
                } else {
                    // Fallback for other types, e.g., String
                    try {
                        especialidadeId = Long.parseLong(idObject.toString());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao obter ID da especialidade: Formato inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String especialidadeNome = (String) tableModel.getValueAt(selectedRow, 1);

                int confirmResult = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir a especialidade: " + especialidadeNome,
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    try {
                        especialidadeDao.deletar(especialidadeId);
                        JOptionPane.showMessageDialog(this, "Especialidade excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarEspecialidades(); // Reload data after deletion
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir especialidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione uma especialidade para excluir.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            }
        });
        panelBotoes.add(btnExcluir);

        JButton btnFechar = new JButton("Fechar");
        Estilo.estilizarBotao(btnFechar);
        btnFechar.addActionListener(e -> dispose());
        panelBotoes.add(btnFechar);

        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void carregarEspecialidades() {
        tableModel.setRowCount(0);
        try {
            List<Especialidade> especialidades = especialidadeDao.listarTodas();
            for (Especialidade e : especialidades) {
                tableModel.addRow(new Object[]{e.getId(), e.getNome(), e.getDescricao()});
            }
            tabelaEspecialidades.revalidate();
            tabelaEspecialidades.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar especialidades: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ListarEspecialidade().setVisible(true);
        });
    }
}