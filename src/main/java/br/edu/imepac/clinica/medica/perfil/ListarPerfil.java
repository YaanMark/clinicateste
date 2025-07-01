package br.edu.imepac.clinica.medica.perfil;

import br.edu.imepac.clinica.medica.daos.PerfilDao;
import br.edu.imepac.clinica.medica.entidades.Perfil;
import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ListarPerfil extends JFrame {

    private JTable tabelaPerfis;
    private DefaultTableModel tableModel;
    private PerfilDao perfilDao;

    public ListarPerfil() {
        setTitle("Listar Perfis");
        setSize(1000, 700); // Increased size to accommodate more columns
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Estilo.COR_FUNDO);

        try {
            perfilDao = new PerfilDao();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        String[] colunas = {
                "ID", "Nome",
                "Cadastrar Funcionário", "Atualizar Funcionário", "Deletar Funcionário", "Listar Funcionário",
                "Cadastrar Paciente", "Atualizar Paciente", "Deletar Paciente", "Listar Paciente",
                "Cadastrar Consulta", "Atualizar Consulta", "Deletar Consulta", "Listar Consulta",
                "Cadastrar Especialidade", "Atualizar Especialidade", "Deletar Especialidade", "Listar Especialidade",
                "Cadastrar Convênio", "Atualizar Convênio", "Deletar Convênio", "Listar Convênio",
                "Cadastrar Prontuário", "Atualizar Prontuário", "Deletar Prontuário", "Listar Prontuário"
        };
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex >= 2) { // All boolean columns
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        tabelaPerfis = new JTable(tableModel);
        tabelaPerfis.setBackground(Estilo.COR_BOTOES);
        tabelaPerfis.setForeground(Estilo.COR_TEXTO);
        tabelaPerfis.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaPerfis.getTableHeader().setBackground(Estilo.COR_BORDA_BOTAO);
        tabelaPerfis.getTableHeader().setForeground(Estilo.COR_TEXTO);
        tabelaPerfis.setSelectionBackground(Estilo.COR_FOCO_CAMPO);
        tabelaPerfis.setSelectionForeground(Color.WHITE);

        tabelaPerfis.setRowSelectionAllowed(true);
        tabelaPerfis.setColumnSelectionAllowed(false);
        tabelaPerfis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Allow horizontal scrolling

        JScrollPane scrollPane = new JScrollPane(tabelaPerfis);
        scrollPane.getViewport().setBackground(Estilo.COR_FUNDO);
        add(scrollPane, BorderLayout.CENTER);

        carregarPerfis();

        JPanel panelBotoes = new JPanel();
        panelBotoes.setBackground(Estilo.COR_FUNDO);
        panelBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton btnExcluir = new JButton("Excluir");
        Estilo.estilizarBotao(btnExcluir);
        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaPerfis.getSelectedRow();
            if (selectedRow >= 0) {
                Object idObject = tableModel.getValueAt(selectedRow, 0);
                Long perfilId;

                if (idObject instanceof Integer) {
                    perfilId = ((Integer) idObject).longValue();
                } else if (idObject instanceof Long) {
                    perfilId = (Long) idObject;
                } else {
                    try {
                        perfilId = Long.parseLong(idObject.toString());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao obter ID do perfil: Formato inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String perfilNome = (String) tableModel.getValueAt(selectedRow, 1);

                int confirmResult = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir o perfil: " + perfilNome + "?",
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    try {
                        perfilDao.deletar(perfilId.intValue()); // PerfilDao expects int
                        JOptionPane.showMessageDialog(this, "Perfil excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarPerfis();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir perfil: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um perfil para excluir.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            }
        });
        panelBotoes.add(btnExcluir);

        JButton btnFechar = new JButton("Fechar");
        Estilo.estilizarBotao(btnFechar);
        btnFechar.addActionListener(e -> dispose());
        panelBotoes.add(btnFechar);

        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void carregarPerfis() {
        tableModel.setRowCount(0);
        try {
            List<Perfil> perfis = perfilDao.listar();
            for (Perfil p : perfis) {
                tableModel.addRow(new Object[]{
                        p.getId(), p.getNome(),
                        p.isCadastrarFuncionario(), p.isAtualizarFuncionario(), p.isDeletarFuncionario(), p.isListarFuncionario(),
                        p.isCadastrarPaciente(), p.isAtualizarPaciente(), p.isDeletarPaciente(), p.isListarPaciente(),
                        p.isCadastrarConsulta(), p.isAtualizarConsulta(), p.isDeletarConsulta(), p.isListarConsulta(),
                        p.isCadastrarEspecialidade(), p.isAtualizarEspecialidade(), p.isDeletarEspecialidade(), p.isListarEspecialidade(),
                        p.isCadastrarConvenio(), p.isAtualizarConvenio(), p.isDeletarConvenio(), p.isListarConvenio(),
                        p.isCadastrarProntuario(), p.isAtualizarProntuario(), p.isDeletarProntuario(), p.isListarProntuario()
                });
            }
            tabelaPerfis.revalidate();
            tabelaPerfis.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar perfis: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ListarPerfil().setVisible(true);
        });
    }
}