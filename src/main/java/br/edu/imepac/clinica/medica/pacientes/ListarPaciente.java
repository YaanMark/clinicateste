package br.edu.imepac.clinica.medica.pacientes;

import br.edu.imepac.clinica.medica.daos.PacienteDao;
import br.edu.imepac.clinica.medica.entidades.Paciente;
import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListarPaciente extends JFrame {

    private JTable tabelaPacientes;
    private DefaultTableModel tableModel;
    private PacienteDao pacienteDao;
    private JFrame parentFrame;

    public ListarPaciente(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setTitle("Listar Pacientes");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Estilo.COR_FUNDO);

        try {
            pacienteDao = new PacienteDao();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        String[] colunas = {"ID", "Nome", "Idade", "Sexo", "CPF", "Rua", "Número", "Complemento", "Bairro", "Cidade", "Estado", "Telefone", "Email", "Data Nasc."};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaPacientes = new JTable(tableModel);
        tabelaPacientes.setBackground(Estilo.COR_BOTOES);
        tabelaPacientes.setForeground(Estilo.COR_TEXTO);
        tabelaPacientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaPacientes.getTableHeader().setBackground(Estilo.COR_BORDA_BOTAO);
        tabelaPacientes.getTableHeader().setForeground(Estilo.COR_TEXTO);
        tabelaPacientes.setSelectionBackground(Estilo.COR_BORDA_BOTAO);
        tabelaPacientes.setSelectionForeground(Color.WHITE);

        tabelaPacientes.setRowSelectionAllowed(true);
        tabelaPacientes.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelaPacientes);
        scrollPane.getViewport().setBackground(Estilo.COR_FUNDO);
        add(scrollPane, BorderLayout.CENTER);

        carregarPacientes();

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton botaoExcluir = new JButton("Excluir");
        Estilo.estilizarBotao(botaoExcluir);
        botaoExcluir.addActionListener(e -> {
            int selectedRow = tabelaPacientes.getSelectedRow();
            if (selectedRow >= 0) {
                Object idObject = tableModel.getValueAt(selectedRow, 0);
                Long pacienteId;

                if (idObject instanceof Integer) {
                    pacienteId = ((Integer) idObject).longValue();
                } else if (idObject instanceof Long) {
                    pacienteId = (Long) idObject;
                } else {
                    try {
                        pacienteId = Long.parseLong(idObject.toString());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao obter ID do paciente: Formato inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String pacienteNome = (String) tableModel.getValueAt(selectedRow, 1);
                int confirmResult = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir o paciente: " + pacienteNome,
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    try {
                        pacienteDao.deletar(pacienteId);
                        JOptionPane.showMessageDialog(this, "Paciente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarPacientes();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir paciente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um paciente para excluir.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
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

    private void carregarPacientes() {
        tableModel.setRowCount(0);
        try {
            List<Paciente> pacientes = pacienteDao.listarTodos();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Paciente p : pacientes) {
                tableModel.addRow(new Object[]{
                        p.getId(),
                        p.getNome(),
                        p.getIdade(),
                        p.getSexo(),
                        p.getCpf(),
                        p.getRua(),
                        p.getNumero(),
                        p.getComplemento(),
                        p.getBairro(),
                        p.getCidade(),
                        p.getEstado(),
                        p.getTelefone(),
                        p.getEmail(),
                        p.getDataNascimento() != null ? p.getDataNascimento().format(formatter) : ""
                });
            }
            tabelaPacientes.revalidate();
            tabelaPacientes.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar pacientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ListarPaciente(null).setVisible(true);
        });
    }
}