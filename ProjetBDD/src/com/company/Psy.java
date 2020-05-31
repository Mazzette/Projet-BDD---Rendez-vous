package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import static javax.swing.LayoutStyle.ComponentPlacement.UNRELATED;

public class Psy extends JFrame implements Serializable {
    private JPasswordField mdp;
    //private final JPasswordField passwordField;
    //private final JTextField UserField;
    private final JButton connexion = new JButton("Connexion");
    private final JButton btnMdp = new JButton("Première connexion");
    private final JButton valider = new JButton("Valider");
    private Container pane;

    public Psy(){
        setTitle("Connectez-vous");
        setSize(350,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        var userLabel = new JLabel("Utilisateur (admin)");
        var mdpLabel = new JLabel("Mot de passe");
        //UserField = new JTextField(15);
        //passwordField = new JPasswordField(15);

        connexion.addActionListener(e -> {
            connexion.setEnabled(true);
            CheckCredentials();
        });

        btnMdp.addActionListener(e -> {
            btnMdp.setEnabled(true);
            pane.removeAll();
            setMdpInterface();
        });
        //createLayout(userLabel, UserField, mdpLabel, passwordField, connexion, btnMdp);
    }

    public void createLayout(Component... arg) {
        pane = getContentPane();
        var gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(50)
                .addGroup(gl.createParallelGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                        .addComponent(arg[3])
                        .addComponent(arg[4])
                        .addComponent(arg[5]))
                .addGap(50)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(50)
                .addGroup(gl.createSequentialGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1], GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(arg[2])
                        .addComponent(arg[3], GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(UNRELATED)
                        .addComponent(arg[4]))
                .addPreferredGap(UNRELATED)
                .addComponent(arg[5])
                .addGap(50)
        );
        pack();
    }

    public void setMdpInterface(){
        setTitle("Mot de passe");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(true);
        setVisible(true);

        var MdpLabel = new JLabel("Choisir mot de passe");
        mdp = new JPasswordField(15);

        valider.addActionListener(e -> {
            valider.setEnabled(true);
            String password = mdp.getText();
            File file = new File("psychologue.ser");
            if(!mdp.getText().isEmpty()) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(password);
                    objectOutputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                pane.removeAll();
                new Psy();
            } else {
                JOptionPane.showMessageDialog(pane, "Saissisez un mot de passe");
                pane.removeAll();
                dispose();
                setMdpInterface();
            }
        });

        createLayoutMdp(MdpLabel, mdp, valider);
    }

    private void createLayoutMdp(Component... arg) {
        pane = getContentPane();
        var grpLayout = new GroupLayout(pane);
        pane.setLayout(grpLayout);

        grpLayout.setAutoCreateGaps(true);
        grpLayout.setAutoCreateContainerGaps(true);

        grpLayout.setHorizontalGroup(grpLayout.createSequentialGroup()
                .addGap(50)
                .addGroup(grpLayout.createParallelGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                        .addGap(50)
                ));

        grpLayout.setVerticalGroup(grpLayout.createSequentialGroup()
                .addGap(50)
                .addGroup(grpLayout.createSequentialGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1], GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(arg[2])
                        .addGap(50)
                ));
        pack();
    }

    public void CheckCredentials(){
        File file = new File("psychologue.ser"); //Fichier .ser dans lequel se trouve le mdp de la psy
        String mdpse = null;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            mdpse = (String) objectInputStream.readObject();
            objectInputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        /*if(passwordField.getText().equals(mdp) && UserField.getText().equals("admin")){
            pane.removeAll();
            dispose();
            JOptionPane.showMessageDialog(pane,"Connexion réussie");
            pane.removeAll();
            new InterfacePsy();
        } else{
            JOptionPane.showMessageDialog(pane, "Veuillez rentrer des informations valides.");
            pane.removeAll();
            dispose();
            new Psychologue();
        }
    }*/
    }

}
