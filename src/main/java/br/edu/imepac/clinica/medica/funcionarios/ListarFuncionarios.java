package br.edu.imepac.clinica.medica.funcionarios;

import br.edu.imepac.clinica.medica.daos.FuncionariosDao;
import br.edu.imepac.clinica.medica.entidades.Funcionarios;
import br.edu.imepac.clinica.medica.outros.Estilo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListarFuncionarios extends JFrame {

    private JTable tabelaFuncionarios;
    private DefaultTableModel tableModel;
    private FuncionariosDao funcionariosDao;
    private JFrame parentFrame;

    public ListarFuncionarios(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setTitle("Listar Funcionários");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Estilo.COR_FUNDO);

        try {
            funcionariosDao = new FuncionariosDao();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        String[] colunas = {"ID", "Usuário", "Nome", "Idade", "Sexo", "CPF", "Rua", "Bairro", "Cidade", "Estado", "Contato", "Email", "Data Nasc.", "ID Perfil"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaFuncionarios = new JTable(tableModel);
        tabelaFuncionarios.setBackground(Estilo.COR_BOTOES);
        tabelaFuncionarios.setForeground(Estilo.COR_TEXTO);
        tabelaFuncionarios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaFuncionarios.getTableHeader().setBackground(Estilo.COR_BORDA_BOTAO);
        tabelaFuncionarios.getTableHeader().setForeground(Estilo.COR_TEXTO);
        tabelaFuncionarios.setSelectionBackground(Estilo.COR_BORDA_BOTAO);
        tabelaFuncionarios.setSelectionForeground(Color.WHITE);

        tabelaFuncionarios.setRowSelectionAllowed(true);
        tabelaFuncionarios.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelaFuncionarios);
        scrollPane.getViewport().setBackground(Estilo.COR_FUNDO);
        add(scrollPane, BorderLayout.CENTER);

        carregarFuncionarios();

        JPanel panel = new JPanel();
        panel.setBackground(Estilo.COR_FUNDO);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
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

    private void carregarFuncionarios() {
        tableModel.setRowCount(0);
        try {
            List<Funcionarios> funcionarios = funcionariosDao.listarTodos();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Funcionarios f : funcionarios) {
                tableModel.addRow(new Object[]{
                        f.getId(),
                        f.getUsuario(),
                        f.getNome(),
                        f.getIdade(),
                        f.getSexo(),
                        f.getCpf(),
                        f.getRua(),
                        f.getBairro(),
                        f.getCidade(),
                        f.getEstado(),
                        f.getContato(),
                        f.getEmail(),
                        f.getDataNascimento() != null ? f.getDataNascimento().format(formatter) : "",
                        f.getIdPerfil()
                });
            }
            tabelaFuncionarios.revalidate();
            tabelaFuncionarios.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar funcionários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ListarFuncionarios(null).setVisible(true);
        });
    }
}