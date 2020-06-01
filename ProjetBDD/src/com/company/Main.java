package com.company;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main(){
        JFrame fenetre = new JFrame("Bienvenue");
        fenetre.setSize(350,200);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLocationRelativeTo(null);
        JLabel select = new JLabel("Sélectionnez l'utilisateur.");

        JComboBox<String> auth = new JComboBox<>(new String[]{"Admin", "Patient"});
        auth.setPreferredSize(new Dimension(150,30));
        auth.setSelectedItem(null);

        JButton valider = new JButton("Valider");
        valider.addActionListener(e -> {
            String user = (String) auth.getEditor().getItem();
            if (user.equals("Admin")){
                new Psy();
                fenetre.dispose();
                fenetre.removeAll();
            }
            if (user.equals("Patient")){
                new Patient();
                fenetre.dispose();
                fenetre.removeAll();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.add(select);
        top.add(auth);

        JPanel south = new JPanel();
        south.add(valider);

        panel.add(top, BorderLayout.CENTER);
        panel.add(south, BorderLayout.SOUTH);

        fenetre.setContentPane(panel);
        fenetre.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
