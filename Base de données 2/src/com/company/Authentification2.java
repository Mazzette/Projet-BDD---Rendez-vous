package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Authentification2 implements ActionListener {
    public static void main (String[] args) {

        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);
        JLabel email = new JLabel("Email");
        email.setBounds(10, 20, 80, 25);
        panel.add(email);

        JTextField userText = new JTextField();
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel mdp = new JLabel("Mot de passe");
        mdp.setBounds(10,50,80,25);
        panel.add(mdp);

        JPasswordField mdpText = new JPasswordField();
        mdpText.setBounds(100, 50, 165 , 25);
        panel.add(mdpText);

        JButton login = new JButton("Valider");
        login.setBounds(10,80,80,25);
        login.addActionListener(new Authentification2());
        panel.add(login);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("Patient.");
    }
}
