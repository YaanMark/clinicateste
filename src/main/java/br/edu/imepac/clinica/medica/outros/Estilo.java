package br.edu.imepac.clinica.medica.outros;

import javax.swing.*;
import java.awt.*;

public class Estilo {

    public static final Color COR_FUNDO = new Color(45, 45, 45);
    public static final Color COR_TEXTO = new Color(200, 200, 200);
    public static final Color COR_BOTOES = new Color(70, 70, 70);
    public static final Color COR_BORDA_BOTAO = new Color(100, 100, 100);
    public static final Color COR_FOCO_CAMPO = new Color(0, 150, 250);

    public static void estilizarBotao(JButton button) {
        button.setBackground(COR_BOTOES);
        button.setForeground(COR_TEXTO);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(COR_BORDA_BOTAO, 1));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 35));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(90, 90, 90));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(COR_BOTOES);
            }
        });
    }
}