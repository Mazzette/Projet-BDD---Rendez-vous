package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import static javax.swing.LayoutStyle.ComponentPlacement.UNRELATED;

public class Psy extends JFrame implements Serializable {
    private JPasswordField mdp;
    private JPasswordField mdpField;
    private JTextField userField;
    private final JButton connexion = new JButton("Connexion");
    private final JButton btnMdp = new JButton("Première connexion");
    private final JButton valider = new JButton("Valider");
    private Container pane;

    public Psy(){
        setTitle("Connectez-vous");
        setSize(350,200);
        setLocationRelativeTo(null);
        setVisible(true);

        var userLabel = new JLabel("Utilisateur (admin)");
        var mdpLabel = new JLabel("Mot de passe");
        userField = new JTextField(15);
        mdpField = new JPasswordField(15);

        connexion.addActionListener(e -> {
            connexion.setEnabled(true);
            try {
                CheckCredentials();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnMdp.addActionListener(e -> {
            btnMdp.setEnabled(true);
            pane.removeAll();
            setMdpInterface();
        });
        createLayout(userLabel, userField, mdpLabel, mdpField, connexion, btnMdp);
    }

    public void createLayout(Component... arg) {
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
                        .addComponent(arg[3])
                        .addComponent(arg[4])
                        .addComponent(arg[5]))
                .addGap(50)
        );

        grpLayout.setVerticalGroup(grpLayout.createSequentialGroup()
                .addGap(50)
                .addGroup(grpLayout.createSequentialGroup()
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
        setAlwaysOnTop(true);
        setResizable(true);
        setVisible(true);

        var MdpLabel = new JLabel("Choisir mot de passe");
        mdp = new JPasswordField(15);

        valider.addActionListener(e -> {
            valider.setEnabled(true);
            String password = mdp.getText();
            if(!mdp.getText().isEmpty()) {
                try {
                    TextFileWriter.append("fileMDP.txt", password);
                } catch (Exception ex) {
                    ex.printStackTrace();
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

    public void CheckCredentials() throws IOException {
        File fileMDP = new File("fileMDP.txt");
        BufferedReader reader = null;
        String line;
        String motDePasse = null;
        try{
            reader = new BufferedReader(new FileReader(fileMDP));
        }catch(FileNotFoundException exc){
            System.out.println("Je ne trouve pas le fichier");
        }
        while ((line = reader.readLine()) != null)
            motDePasse = line.toString();
        reader.close();
        /*try{
            FileInputStream fileInputStream = new FileInputStream(fileMDP);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            motDePasse = (String) objectInputStream.toString();
            System.out.println(motDePasse);
            objectInputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }*/

        if(mdpField.getText().equals(motDePasse) && userField.getText().equals("admin")){
            pane.removeAll();
            dispose();
            JOptionPane.showMessageDialog(pane,"Connexion réussie");
            pane.removeAll();
            new InterfacePsy();
        } else{
            JOptionPane.showMessageDialog(pane, "Veuillez rentrer des informations valides.");
            pane.removeAll();
            dispose();
            new Psy();
        }
    }
}


