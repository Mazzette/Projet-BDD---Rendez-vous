package com.company;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import static javax.swing.LayoutStyle.ComponentPlacement.UNRELATED;


public class Patient extends JFrame {
    private String url = "URL";
    private String user = "email";
    private String mdp = "mot de passe";

    private JTextField userField, nomField, prenomField, moyenConnaissanceField, adresseField,
            sexeField, professionField, classificationField, dateProfessionField, nbTelephoneField;
    private JFormattedTextField dateNaissanceField;
    private JPasswordField mdpField;

    //private final JButton newPatient = new JButton("Nouveau Patient");
    //private final JButton checkPatient = new JButton("Valider Patient");

    private Container container;

    private int nbPatient, nbTelephone;
    private String nom, prenom, moyenConnaissance, adresse, dateNaissance, classification, email, mdpassse;
    private char sexe;
    private Profession profession;

    public void setNbPatient(int nbPatient){this.nbPatient = nbPatient;}
    public void setClassification(String classification){this.classification = classification;}
    public void setMoyenConnaissance(String moyenConnaissance){this.moyenConnaissance = moyenConnaissance;}
    public void setNom(String nom){this.nom = nom;}
    public void setPrenom(String prenom){this.prenom = prenom;}
    public void setAdresse(String adresse){this.adresse = adresse;}
    public void setSexe(char sexe){this.sexe = sexe;}
    public void setDateNaissance(String dateNaissance){this.dateNaissance = dateNaissance;}
    public void setProfession (Profession profession){this.profession = profession;}
    public void setNbTelephone (int nbTelephone){this.nbTelephone = nbTelephone;}
    public void setEmail (String email){this.email = email;}
    public void setMdpassse (String mdpasse){this.mdpassse = mdpasse;}

    public int getNbPatient(){return nbPatient;}

    public Patient(String nom, String prenom, String moyenConnaissance, String adresse, String dateNaissance, String classification, char sexe, int nbTelephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.nbTelephone = nbTelephone;
        this.moyenConnaissance = moyenConnaissance;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.classification = classification;
        this.sexe = sexe;
    }

    public Patient(){
        JFrame patient = new JFrame();
        patient.setTitle("Authentification");
        setSize(300,400);
        patient.setLocationRelativeTo(null);
        patient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        patient.setResizable(true);
        patient.setAlwaysOnTop(true);
        patient.setVisible(true);

        JLabel email = new JLabel("Email");
        JLabel mdp = new JLabel("Mot de passe");

        userField = new JTextField(15);
        mdpField = (JPasswordField) new JTextField(15);

        JButton valider = new JButton("Valider");
        valider.addActionListener(e->{
            container.removeAll();
            doAuthentification();
        });

        /*newPatient.addActionListener(e->{
            container.removeAll();
            doNewPatient();
        });*/

        createLayout (user, userField, mdp, mdpField, valider);
    }

    private void doAuthentification(){
        try {
            Connection connect = DriverManager.getConnection(url, user, mdp);
            Statement statement = connect.createStatement();
            String request = "Select MotDePasse from Patient WHERE Email = '" + userField.getText() + "'"; //récupération du mot de passe
            ResultSet result = statement.executeQuery(request);
            if(!result.next()){
                container.removeAll();
                dispose();
                JOptionPane.showMessageDialog(container, "Ce patient n'existe pas");
                new Patient();
            } else {
                String mdp;
                mdp = result.getString("MotDePasse");
                if (mdpField.getText().equals(mdp)) {
                    container.removeAll();
                    dispose();
                    JOptionPane.showMessageDialog(container, "Connexion réussie");
                    storePatientInfo(userField.getText());
                } else {
                    container.removeAll();
                    dispose();
                    JOptionPane.showMessageDialog(container, "Mauvaises informations, veuillez recommencer");
                    new Patient();
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception is " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void storePatientInfo(String user) {
        try {
            Connection connect = DriverManager.getConnection(url, user, mdp);
            Statement statement = connect.createStatement();
            String sql = "select NumeroDossier, Nom, Prenom, DateDeNaissance, Sexe, Adresse, MoyenConnaissance, Email, MotDePasse, Classification, NumeroTel, Profession " +
                    "from Patient " +
                    "where Email = '" + user + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                setNbPatient(resultSet.getInt("NumeroDossier"));
                setNom(resultSet.getString("Nom"));
                setPrenom(resultSet.getString("Prenom"));
                setDateNaissance(resultSet.getString("DateDeNaissance"));
                setSexe(resultSet.getString("Sexe").charAt(0));
                setAdresse(resultSet.getString("Adresse"));
                setMoyenConnaissance(resultSet.getString("MoyenConnaissance"));
                setEmail(resultSet.getString("Email"));
                setMdpassse(resultSet.getString("MotDePasse"));
                setClassification(resultSet.getString("Classification"));
                setNbTelephone(resultSet.getInt("NumeroTel"));
                setProfession(new Profession(resultSet.getInt("NumeroDossier"), resultSet.getString("Profession")));
            }
        } catch (SQLException e) {
            System.out.println("Exception is " + e.getMessage());
        }
    }

    public void createLayout(String user, Component... arg) {
        container = getContentPane();
        var grpLayout = new GroupLayout(container);
        container.setLayout(grpLayout);

        grpLayout.setAutoCreateGaps(true);
        grpLayout.setAutoCreateContainerGaps(true);

        grpLayout.setHorizontalGroup(grpLayout.createSequentialGroup()
                .addGap(50)
                .addGroup(grpLayout.createParallelGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                        .addComponent(arg[3])
                        .addComponent(arg[4]))
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
                .addGap(50)
        );
        pack();
    }

}

