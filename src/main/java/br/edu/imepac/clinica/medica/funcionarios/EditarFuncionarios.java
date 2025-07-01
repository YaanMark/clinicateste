package br.edu.imepac.clinica.medica.funcionarios;

import br.edu.imepac.clinica.medica.daos.FuncionariosDao;
import br.edu.imepac.clinica.medica.daos.PerfilDao;
import br.edu.imepac.clinica.medica.entidades.Funcionarios;
import br.edu.imepac.clinica.medica.entidades.Perfil;
import br.edu.imepac.clinica.medica.outros.Estilo;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class EditarFuncionarios extends JFrame {

        private JTextField campoId;
        private JTextField campoUsuario;
        private JTextField campoNome;
        private JTextField campoIdade;
        private JTextField campoSexo;
        private JTextField campoCpf;
        private JTextField campoRua;
        private JTextField campoBairro;
        private JTextField campoCidade;
        private JTextField campoEstado;
        private JTextField campoContato;
        private JTextField campoEmail;
        private JDateChooser campoDataNascimento;
        private JComboBox<Perfil> comboIdPerfil;
        private JPasswordField campoSenha;
        private JButton btnBuscar;
        private JButton btnEditar;
        private JButton btnFechar;

        private FuncionariosDao funcionariosDao;
        private PerfilDao perfilDao;
        private Funcionarios funcionarioAtual;

        public EditarFuncionarios() {
            super("Editar Funcionário");

            inicializarDaos();

            setSize(700, 850);
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
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
            panel.add(createLabel("ID do Funcionário:"), gbc);

            campoId = new JTextField(20);
            estilizarCampoTexto(campoId);
            gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1;
            panel.add(campoId, gbc);

            btnBuscar = new JButton("Buscar");
            Estilo.estilizarBotao(btnBuscar);
            btnBuscar.addActionListener(e -> acaoBotaoBuscar());
            gbc.gridx = 2; gbc.gridy = 0; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.WEST;
            panel.add(btnBuscar, gbc);

            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0; gbc.gridy = 1; panel.add(createLabel("Usuário:"), gbc);
            campoUsuario = new JTextField(25); estilizarCampoTexto(campoUsuario); gbc.gridx = 1; panel.add(campoUsuario, gbc);

            gbc.gridx = 0; gbc.gridy = 2; panel.add(createLabel("Nome Completo:"), gbc);
            campoNome = new JTextField(25); estilizarCampoTexto(campoNome); gbc.gridx = 1; panel.add(campoNome, gbc);

            gbc.gridx = 0; gbc.gridy = 3; panel.add(createLabel("Idade:"), gbc);
            campoIdade = new JTextField(25); estilizarCampoTexto(campoIdade); gbc.gridx = 1; panel.add(campoIdade, gbc);

            gbc.gridx = 0; gbc.gridy = 4; panel.add(createLabel("Sexo:"), gbc);
            campoSexo = new JTextField(25); estilizarCampoTexto(campoSexo); gbc.gridx = 1; panel.add(campoSexo, gbc);

            gbc.gridx = 0; gbc.gridy = 5; panel.add(createLabel("CPF:"), gbc);
            campoCpf = new JTextField(25); estilizarCampoTexto(campoCpf); gbc.gridx = 1; panel.add(campoCpf, gbc);

            gbc.gridx = 0; gbc.gridy = 6; panel.add(createLabel("Rua:"), gbc);
            campoRua = new JTextField(25); estilizarCampoTexto(campoRua); gbc.gridx = 1; panel.add(campoRua, gbc);

            gbc.gridx = 0; gbc.gridy = 7; panel.add(createLabel("Bairro:"), gbc);
            campoBairro = new JTextField(25); estilizarCampoTexto(campoBairro); gbc.gridx = 1; panel.add(campoBairro, gbc);

            gbc.gridx = 0; gbc.gridy = 8; panel.add(createLabel("Cidade:"), gbc);
            campoCidade = new JTextField(25); estilizarCampoTexto(campoCidade); gbc.gridx = 1; panel.add(campoCidade, gbc);

            gbc.gridx = 0; gbc.gridy = 9; panel.add(createLabel("Estado:"), gbc);
            campoEstado = new JTextField(25); estilizarCampoTexto(campoEstado); gbc.gridx = 1; panel.add(campoEstado, gbc);

            gbc.gridx = 0; gbc.gridy = 10; panel.add(createLabel("Contato:"), gbc);
            campoContato = new JTextField(25); estilizarCampoTexto(campoContato); gbc.gridx = 1; panel.add(campoContato, gbc);

            gbc.gridx = 0; gbc.gridy = 11; panel.add(createLabel("Email:"), gbc);
            campoEmail = new JTextField(25); estilizarCampoTexto(campoEmail); gbc.gridx = 1; panel.add(campoEmail, gbc);

            gbc.gridx = 0; gbc.gridy = 12; panel.add(createLabel("Data de Nascimento:"), gbc);
            campoDataNascimento = new JDateChooser();
            campoDataNascimento.setDateFormatString("dd/MM/yyyy");
            estilizarDateChooser(campoDataNascimento);
            gbc.gridx = 1; panel.add(campoDataNascimento, gbc);

            gbc.gridx = 0; gbc.gridy = 13; panel.add(createLabel("ID Perfil:"), gbc);
            comboIdPerfil = new JComboBox<>();
            estilizarComboBox(comboIdPerfil);
            gbc.gridx = 1; panel.add(comboIdPerfil, gbc);

            gbc.gridx = 0; gbc.gridy = 14; panel.add(createLabel("Senha:"), gbc);
            campoSenha = new JPasswordField(25);
            estilizarCampoTexto(campoSenha);
            gbc.gridx = 1; panel.add(campoSenha, gbc);

            gbc.gridx = 0; gbc.gridy = 15; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
            btnEditar = new JButton("Editar");
            Estilo.estilizarBotao(btnEditar);
            btnEditar.addActionListener(e -> acaoBotaoEditar());
            panel.add(btnEditar, gbc);

            gbc.gridx = 1; gbc.gridy = 15; gbc.anchor = GridBagConstraints.WEST;
            btnFechar = new JButton("Fechar");
            Estilo.estilizarBotao(btnFechar);
            btnFechar.addActionListener(e -> fecharAplicacao());
            panel.add(btnFechar, gbc);

            carregarPerfis();
            toggleCamposEdicao(false);
        }

        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setForeground(Estilo.COR_TEXTO);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            return label;
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

        private void estilizarDateChooser(JDateChooser dateChooser) {
            dateChooser.setBackground(Estilo.COR_BOTOES);
            dateChooser.setForeground(Estilo.COR_TEXTO);
            dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            dateChooser.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));

            if (dateChooser.getJCalendar() != null) {
                dateChooser.getJCalendar().setBackground(Estilo.COR_FUNDO);
                dateChooser.getJCalendar().setForeground(Estilo.COR_TEXTO);
                dateChooser.getJCalendar().setFont(new Font("Segoe UI", Font.PLAIN, 14));
            }
        }

        private void estilizarComboBox(JComboBox<?> comboBox) {
            comboBox.setBackground(Estilo.COR_BOTOES);
            comboBox.setForeground(Estilo.COR_TEXTO);
            comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            comboBox.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO));
            comboBox.setOpaque(true);

            comboBox.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent evt) {
                    comboBox.setBorder(BorderFactory.createLineBorder(Estilo.COR_FOCO_CAMPO, 2));
                }
                @Override
                public void focusLost(FocusEvent evt) {
                    comboBox.setBorder(BorderFactory.createLineBorder(Estilo.COR_BORDA_BOTAO, 1));
                }
            });
        }

        private void inicializarDaos() {
            try {
                this.funcionariosDao = new FuncionariosDao();
                this.perfilDao = new PerfilDao();
                System.out.println("DAOs de Funcionários e Perfil inicializados com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados! Detalhes: " + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                dispose();
            }
        }

        private void carregarPerfis() {
            try {
                List<Perfil> perfisList = perfilDao.listar();
                if (perfisList.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum perfil cadastrado! Cadastre um perfil antes de editar um funcionário.");
                    dispose();
                    return;
                }

                comboIdPerfil.addItem(null);
                perfisList.forEach(this.comboIdPerfil::addItem);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar perfis! Detalhes: " + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                System.err.println("Erro ao buscar perfis: " + exception.getMessage());
                dispose();
            }
        }

        private void toggleCamposEdicao(boolean enable) {
            campoUsuario.setEnabled(enable);
            campoNome.setEnabled(enable);
            campoIdade.setEnabled(enable);
            campoSexo.setEnabled(enable);
            campoCpf.setEnabled(enable);
            campoRua.setEnabled(enable);
            campoBairro.setEnabled(enable);
            campoCidade.setEnabled(enable);
            campoEstado.setEnabled(enable);
            campoContato.setEnabled(enable);
            campoEmail.setEnabled(enable);
            campoDataNascimento.setEnabled(enable);
            comboIdPerfil.setEnabled(enable);
            campoSenha.setEnabled(enable);
            btnEditar.setEnabled(enable);
        }

        private void clearFields() {
            campoUsuario.setText("");
            campoNome.setText("");
            campoIdade.setText("");
            campoSexo.setText("");
            campoCpf.setText("");
            campoRua.setText("");
            campoBairro.setText("");
            campoCidade.setText("");
            campoEstado.setText("");
            campoContato.setText("");
            campoEmail.setText("");
            campoDataNascimento.setDate(null);
            comboIdPerfil.setSelectedItem(null);
            campoSenha.setText("");
            funcionarioAtual = null;
        }

        private void acaoBotaoBuscar() {
            if (campoId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, digite o ID do funcionário para buscar.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int id = Integer.parseInt(campoId.getText().trim());
                funcionarioAtual = funcionariosDao.buscarPorId(id);

                if (funcionarioAtual != null) {
                    campoUsuario.setText(funcionarioAtual.getUsuario());
                    campoNome.setText(funcionarioAtual.getNome());
                    campoIdade.setText(funcionarioAtual.getIdade());
                    campoSexo.setText(funcionarioAtual.getSexo());
                    campoCpf.setText(funcionarioAtual.getCpf());
                    campoRua.setText(funcionarioAtual.getRua());
                    campoBairro.setText(funcionarioAtual.getBairro());
                    campoCidade.setText(funcionarioAtual.getCidade());
                    campoEstado.setText(funcionarioAtual.getEstado());
                    campoContato.setText(funcionarioAtual.getContato());
                    campoEmail.setText(funcionarioAtual.getEmail());

                    if (funcionarioAtual.getDataNascimento() != null) {
                        Date date = Date.from(funcionarioAtual.getDataNascimento().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        campoDataNascimento.setDate(date);
                    } else {
                        campoDataNascimento.setDate(null);
                    }

                    for (int i = 0; i < comboIdPerfil.getItemCount(); i++) {
                        Perfil p = comboIdPerfil.getItemAt(i);
                        if (p != null && p.getId() == funcionarioAtual.getIdPerfil()) {
                            comboIdPerfil.setSelectedItem(p);
                            break;
                        }
                    }
                    campoSenha.setText(funcionarioAtual.getSenha());

                    toggleCamposEdicao(true);
                    JOptionPane.showMessageDialog(this, "Funcionário encontrado! Você pode editar os dados.");
                } else {
                    JOptionPane.showMessageDialog(this, "Funcionário com ID " + id + " não encontrado.", "Não Encontrado", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    toggleCamposEdicao(false);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                clearFields();
                toggleCamposEdicao(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                clearFields();
                toggleCamposEdicao(false);
            }
        }

        private boolean validarDadosObrigatorios() {

            return !campoUsuario.getText().trim().isEmpty() &&
                    !campoNome.getText().trim().isEmpty() &&
                    !campoIdade.getText().trim().isEmpty() &&
                    !campoSexo.getText().trim().isEmpty() &&
                    !campoCpf.getText().trim().isEmpty() &&
                    !campoRua.getText().trim().isEmpty() &&
                    !campoBairro.getText().trim().isEmpty() &&
                    !campoCidade.getText().trim().isEmpty() &&
                    !campoEstado.getText().trim().isEmpty() &&
                    !campoContato.getText().trim().isEmpty() &&
                    !campoEmail.getText().trim().isEmpty() &&
                    campoDataNascimento.getDate() != null &&
                    comboIdPerfil.getSelectedItem() != null;
        }

        private void acaoBotaoEditar() {
            if (funcionarioAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhum funcionário carregado para edição.", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (validarDadosObrigatorios()) {
                funcionarioAtual.setUsuario(campoUsuario.getText());
                funcionarioAtual.setNome(campoNome.getText());
                funcionarioAtual.setIdade(campoIdade.getText());
                funcionarioAtual.setSexo(campoSexo.getText());
                funcionarioAtual.setCpf(campoCpf.getText());
                funcionarioAtual.setRua(campoRua.getText());
                funcionarioAtual.setBairro(campoBairro.getText());
                funcionarioAtual.setCidade(campoCidade.getText());
                funcionarioAtual.setEstado(campoEstado.getText());
                funcionarioAtual.setContato(campoContato.getText());
                funcionarioAtual.setEmail(campoEmail.getText());

                if (campoDataNascimento.getDate() != null) {
                    funcionarioAtual.setDataNascimento(campoDataNascimento.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                } else {
                    funcionarioAtual.setDataNascimento(null);
                }

                Perfil perfilSelecionado = (Perfil) comboIdPerfil.getSelectedItem();
                if (perfilSelecionado != null) {
                    funcionarioAtual.setIdPerfil(perfilSelecionado.getId());
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione um perfil válido.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                char[] senhaChars = campoSenha.getPassword();
                if (senhaChars.length > 0) {
                    funcionarioAtual.setSenha(new String(senhaChars));
                } else {

                }

                try {
                    funcionariosDao.atualizar(funcionarioAtual);
                    JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
                    clearFields();
                    campoId.setText("");
                    toggleCamposEdicao(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Erro ao atualizar funcionário: " + e.getMessage(),
                            "Erro de banco de dados",
                            JOptionPane.ERROR_MESSAGE
                    );
                    System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Por favor, preencha todos os campos obrigatórios e selecione a Data de Nascimento e o Perfil.",
                        "Dados Incompletos",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

        private void fecharAplicacao() {
            this.dispose();
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new EditarFuncionarios().setVisible(true);
            });
        }
    }