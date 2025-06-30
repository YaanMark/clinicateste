package br.edu.imepac.clinica.medica.convenios;

import br.edu.imepac.clinica.medica.daos.ConvenioDao;
import br.edu.imepac.clinica.medica.entidades.Convenio;
import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ListarConvenio extends JFrame {

    private JTable tabelaConvenio;
    private DefaultTableModel tableModel;
    private ConvenioDao convenioDao;

    public ListarConvenio() {
        setTitle("Listar Convênios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Estilo.COR_FUNDO);

        try {
            convenioDao = new ConvenioDao();
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
        tabelaConvenio = new JTable(tableModel);
        tabelaConvenio.setBackground(Estilo.COR_BOTOES);
        tabelaConvenio.setForeground(Estilo.COR_TEXTO);
        tabelaConvenio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaConvenio.getTableHeader().setBackground(Estilo.COR_BORDA_BOTAO);
        tabelaConvenio.getTableHeader().setForeground(Estilo.COR_TEXTO);
        tabelaConvenio.setSelectionBackground(Estilo.COR_BORDA_BOTAO);
        tabelaConvenio.setSelectionForeground(Color.WHITE);

        tabelaConvenio.setRowSelectionAllowed(true);
        tabelaConvenio.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelaConvenio);
        scrollPane.getViewport().setBackground(Estilo.COR_FUNDO);
        add(scrollPane, BorderLayout.CENTER);

        carregarConvenios();

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton botaoExcluir = new JButton("Excluir");
        Estilo.estilizarBotao(botaoExcluir);
        botaoExcluir.addActionListener(e -> {
            int selectedRow = tabelaConvenio.getSelectedRow();
            if (selectedRow >= 0) {
                Object idObject = tableModel.getValueAt(selectedRow, 0);
                int convenioId;

                if (idObject instanceof Integer) {
                    convenioId = (Integer) idObject;
                } else {
                    try {
                        convenioId = Integer.parseInt(idObject.toString());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao obter ID do convênio: Formato inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String convenioNome = (String) tableModel.getValueAt(selectedRow, 1);
                int confirmResult = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir o convênio: " + convenioNome,
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    try {
                        convenioDao.deletar(convenioId);
                        JOptionPane.showMessageDialog(this, "Convênio excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarConvenios(); // Reload data after deletion
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir convênio: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um convênio para excluir.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            }
        });
        panel.add(botaoExcluir);

        JButton botaoFechar = new JButton("Fechar");
        Estilo.estilizarBotao(botaoFechar);
        botaoFechar.addActionListener(e -> dispose());
        panel.add(botaoFechar);

        add(panel, BorderLayout.SOUTH);
    }

    private void carregarConvenios() {
        tableModel.setRowCount(0);
        try {
            List<Convenio> convenios = convenioDao.listar();
            for (Convenio c : convenios) {
                tableModel.addRow(new Object[]{c.getId(), c.getNome(), c.getDescricao()});
            }
            tabelaConvenio.revalidate();
            tabelaConvenio.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar convênios: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ListarConvenio().setVisible(true);
        });
    }
}