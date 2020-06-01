package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static javax.swing.LayoutStyle.ComponentPlacement.UNRELATED;


public class Patient extends JFrame {

    private final String url = "url";
    private final String user = "user", mdp = "password";

    private JTextField userField, nomField, prenomField, moyenConnaissanceField, adresseField,
            sexeField, professionField, classificationField, dateProfessionField, nbTelephoneField;
    private JFormattedTextField dateNaissanceField;
    private JPasswordField mdpField;

    private final JButton newPatient = new JButton("Nouveau Patient");
    private final JButton validerPatient = new JButton("Valider Patient");

    private Container container;

    private int nbDossier;
    private int nbTelephone;
    private String nom, prenom, moyenConnaissance, adresse, dateNaissance, classification, email, password;
    private char sexe;
    private Profession profession;

    public void setNbDossier(int nbDossier) { this.nbDossier = nbDossier; }
    public void setClassification(String classification) { this.classification = classification; }
    public void setMoyenConnaissance(String moyenConnaissance) { this.moyenConnaissance = moyenConnaissance; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setSexe(char sexe) { this.sexe = sexe; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }
    public void setProfession(Profession profession) { this.profession = profession; }
    public void setNbTelephone(int nbTelephone) { this.nbTelephone = nbTelephone; }
    public void setEmail(String email) { this.email = email; }
    public void setMdp(String password) { this.password = password; }
    public int getNbDossier(){ return nbDossier; }

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

    public Patient() {
        setTitle("Connexion Patient");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setAlwaysOnTop(true);
        setVisible(true);

        JLabel username = new JLabel("Email");
        JLabel motDePasse = new JLabel("Mot de passe");

        userField = new JTextField(15);
        mdpField = new JPasswordField(15);

        JButton btnConnexion = new JButton("Connexion");
        btnConnexion.addActionListener(e -> {
            container.removeAll();
            doAuthentification();
        });

        newPatient.addActionListener(e -> {
            container.removeAll();
            doNewPatient();
        });

        createLayout(username, userField, motDePasse, mdpField, btnConnexion, newPatient);
    }

    private void doAuthentification() {
        try {
            Connection connect = DriverManager.getConnection(url, user, mdp);
            Statement statement = connect.createStatement();
            String request = "Select MotDePasse from Patient WHERE Email = '" + userField.getText() + "'";
            ResultSet result = statement.executeQuery(request);
            if(!result.next()){
                container.removeAll();
                dispose();
                JOptionPane.showMessageDialog(container, "Vous n'êtes pas patient");
                new Patient();
            } else {
                String mdp;
                mdp = result.getString("MotDePasse");
                if (mdpField.getText().equals(mdp)) {
                    container.removeAll();
                    dispose();
                    JOptionPane.showMessageDialog(container, "Connexion réussie");
                    storePatientInfo(userField.getText());
                    chooseAction();
                } else {
                    container.removeAll();
                    dispose();
                    JOptionPane.showMessageDialog(container, "Veuillez saisir des informations valides");
                    new Patient();
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception is " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createLayout(Component... arg) {
        container = getContentPane();
        var groupLayout = new GroupLayout(container);
        container.setLayout(groupLayout);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGap(50)
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                        .addComponent(arg[3])
                        .addComponent(arg[4])
                        .addComponent(arg[5]))
                .addGap(50)
        );

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGap(50)
                .addGroup(groupLayout.createSequentialGroup()
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


    private void doNewPatient() {
        setTitle("Compte Patient");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setAlwaysOnTop(true);

        JLabel nom = new JLabel("Nom");
        JLabel prenom = new JLabel("Prenom");
        JLabel moyenDeConnaissance = new JLabel("Moyen de connaissance");
        JLabel adresse = new JLabel("Adresse");
        JLabel sexe = new JLabel("Sexe (F ou M)");
        JLabel dateNaissance = new JLabel("Date de Naissance (YYYY-MM-DD)");
        JLabel profession = new JLabel("Profession actuelle");
        JLabel dateProfession = new JLabel("Date de début de la profession (YYY-MM-DD)");
        JLabel classification = new JLabel("Classification (Homme, Femme, Enfant, Ado)");
        JLabel nbTelephone = new JLabel("Numero de téléphone - XXXXXXXXXX");
        JLabel user = new JLabel("Email");
        JLabel mdp = new JLabel("Mot de Passe");

        nomField = new JTextField(15);
        prenomField = new JTextField(15);
        moyenConnaissanceField = new JTextField(15);
        adresseField = new JTextField(15);
        sexeField = new JTextField(15);
        dateNaissanceField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        professionField = new JTextField(15);
        dateProfessionField = new JTextField(15);
        nbTelephoneField = new JTextField(15);
        classificationField = new JTextField(15);
        userField = new JTextField(15);
        mdpField = new JPasswordField(15);

        newPatient.addActionListener(e -> {
            container.removeAll();
            doAuthentification();
        });

        validerPatient.addActionListener(e -> {
            try {
                WritePatientInfo();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            dispose();
            container.removeAll();
            chooseAction();
        });

        createLayoutPatient(nom, nomField, prenom, prenomField, moyenDeConnaissance, moyenConnaissanceField,
                adresse, adresseField, sexe, sexeField, dateNaissance, dateNaissanceField, profession, professionField,
                dateProfession, dateProfessionField, classification, classificationField, nbTelephone, nbTelephoneField,
                user, userField, mdp, mdpField, validerPatient);
        setVisible(true);
    }

    private void createLayoutPatient(Component... arg) {
        container = getContentPane();
        var groupLayout = new GroupLayout(container);
        container.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGap(50)
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                        .addComponent(arg[3])
                        .addComponent(arg[4])
                        .addComponent(arg[5])
                        .addComponent(arg[6])
                        .addComponent(arg[7])
                        .addComponent(arg[8])
                        .addComponent(arg[9])
                        .addComponent(arg[10])
                        .addComponent(arg[11])
                        .addComponent(arg[12])
                        .addComponent(arg[13])
                        .addComponent(arg[14])
                        .addComponent(arg[15])
                        .addComponent(arg[16])
                        .addComponent(arg[17])
                        .addComponent(arg[18])
                        .addComponent(arg[19])
                        .addComponent(arg[20])
                        .addComponent(arg[21])
                        .addComponent(arg[22])
                        .addComponent(arg[23])
                        .addComponent(arg[24])
                        .addGap(50)
                ));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGap(50)
                .addGroup(groupLayout.createSequentialGroup()
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
                .addComponent(arg[6])
                .addComponent(arg[7])
                .addComponent(arg[8])
                .addComponent(arg[9])
                .addComponent(arg[10])
                .addComponent(arg[11])
                .addComponent(arg[12])
                .addComponent(arg[13])
                .addComponent(arg[14])
                .addComponent(arg[15])
                .addComponent(arg[16])
                .addComponent(arg[17])
                .addComponent(arg[18])
                .addComponent(arg[19])
                .addComponent(arg[20])
                .addComponent(arg[21])
                .addComponent(arg[22])
                .addComponent(arg[23])
                .addComponent(arg[24])
                .addGap(50)
        );
        pack();
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
                setNbDossier(resultSet.getInt("NumeroDossier"));
                setNom(resultSet.getString("Nom"));
                setPrenom(resultSet.getString("Prenom"));
                setDateNaissance(resultSet.getString("DateDeNaissance"));
                setSexe(resultSet.getString("Sexe").charAt(0));
                setAdresse(resultSet.getString("Adresse"));
                setMoyenConnaissance(resultSet.getString("MoyenConnaissance"));
                setEmail(resultSet.getString("Email"));
                setMdp(resultSet.getString("MotDePasse"));
                setClassification(resultSet.getString("Classification"));
                setNbTelephone(resultSet.getInt("NumeroTel"));
                setProfession(new Profession(resultSet.getInt("NumeroDossier"), resultSet.getString("Profession")));
            }
        } catch (SQLException e) {
            System.out.println("Exception is " + e.getMessage());
        }
    }

    public void WritePatientInfo() throws SQLException {
        Connection connect = DriverManager.getConnection(url, user, password);
        Statement statement = connect.createStatement();
        String request = "Select Nom, Prenom from Patient where Email = '" + userField.getText() + "' and MotDePasse =  '" + mdpField.getText() + "'";
        ResultSet result = statement.executeQuery(request);
        result.next();
        if (!result.next()) {
            String sql = "insert into Patient (Nom, Prenom, DateDeNaissance, Sexe, Adresse, MoyenConnaissance, NumeroTel," +
                    "Email, MotDePasse, Classification) values ('" + nomField.getText() + "','" + prenomField.getText() + "','" +
                    dateNaissanceField.getText() + "','" + sexeField.getText() + "','" + adresseField.getText() + "','" +
                    moyenConnaissanceField.getText() + "','" + nbTelephoneField.getText() + "','" + userField.getText() + "','" +
                    mdpField.getText() + "','" + classificationField.getText() + "')";
            statement.executeUpdate(sql);
            Statement statement1 = connect.createStatement();
            String sql2 = "Select NumeroDossier, Nom, Prenom, DateDeNaissance, Sexe, Adresse, MoyenConnaissance, NumeroTel, Email, MotDePasse" +
                    " from Patient WHERE Email = '" + userField.getText() + "' and MotDePasse = '" + mdpField.getText() + "'";
            ResultSet resultSet = statement1.executeQuery(sql2);

            while (resultSet.next()) {
                setNbDossier(resultSet.getInt("NumeroDossier"));
                setNom(resultSet.getString("Nom"));
                setPrenom(resultSet.getString("Prenom"));
                setDateNaissance(resultSet.getString("DateDeNaissance"));
                setSexe(resultSet.getString("Sexe").charAt(0));
                setAdresse(resultSet.getString("Adresse"));
                setMoyenConnaissance(resultSet.getString("MoyenConnaissance"));
                setNbTelephone(resultSet.getInt("NumeroTel"));
                setEmail(resultSet.getString("Email"));
                setMdp(resultSet.getString("MotDePasse"));
            }
            new Patient(nom, prenom, moyenConnaissance, adresse, dateNaissance, classification, sexe, nbTelephone);
            Profession profession = new Profession(professionField.getText(), dateProfessionField.getText(), getNbDossier());
            setProfession(profession);

            String sql3 = "insert into Profession (NumeroDossier, Titre, DateProfession) values ('" + getNbDossier() + "', '" + professionField.getText() + "', '" + dateProfessionField.getText() + "')";
            statement.executeUpdate(sql3);
        } else {
            String sql = "Select NumeroDossier from Patient" +
                    " WHERE Nom = '" + nomField.getText() + "' and Prenom = '" + prenomField.getText() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            int num = resultSet.getInt("NumeroDossier");
            String sql2 = "update Patient set Email = '" + userField.getText() + "', MotDePasse = '" + mdpField.getText() + "'" +
                    " where NumeroDossier = '" + num + "'";
            statement.executeUpdate(sql2);

            String sql3 = "Select NumeroDossier, Nom, Prenom, DateDeNaissance, Sexe, Adresse, MoyenConnaissance, NumeroTel, Email, MotDePasse" +
                    " from Patient WHERE Email = '" + userField.getText() + "' and MotDePasse = '" + mdpField.getText() + "'";
            ResultSet resultSet1 = statement.executeQuery(sql3);

            while (resultSet1.next()) {
                setNbDossier(resultSet1.getInt("NumeroDossier"));
                setNom(resultSet1.getString("Nom"));
                setPrenom(resultSet1.getString("Prenom"));
                setDateNaissance(resultSet1.getString("DateDeNaissance"));
                setSexe(resultSet1.getString("Sexe").charAt(0));
                setAdresse(resultSet1.getString("Adresse"));
                setMoyenConnaissance(resultSet1.getString("MoyenConnaissance"));
                setNbTelephone(resultSet1.getInt("NumeroTel"));
                setEmail(resultSet1.getString("Email"));
                setMdp(resultSet1.getString("MotDePasse"));
            }
        }
    }

    public void chooseAction() {
        setTitle("Que vouslez-vous faire ?");
        setSize(150, 350);
        setResizable(false);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        JButton rdv = new JButton("Prendre rendez-vous");
        JButton consult = new JButton("Consulter mes rendez-vous");
        JButton modif = new JButton("Modifier mes informations");
        container.add(rdv);
        container.add(consult);
        container.add(modif);
        setVisible(true);
        rdv.addActionListener(e -> {
            dispose();
            container.removeAll();
            setRDV();
        });
        consult.addActionListener(e -> {
            dispose();
            container.removeAll();
            seeHistorique();
        });
        modif.addActionListener(e -> {
            dispose();
            container.removeAll();
            try {
                modifInfo();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
        createLayoutChoice(rdv, consult, modif);
    }

    private void createLayoutChoice(Component... arg) {
        container = getContentPane();
        var groupLayout = new GroupLayout(container);
        container.setLayout(groupLayout);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGap(50)
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                        .addGap(50)
                ));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGap(50)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1], GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(arg[2])
                        .addGap(50)
                ));
        pack();
    }

    public void seeHistorique() {
        JFrame frame = new JFrame("Historique des rendez-vous");
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        DefaultTableModel calendrier = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        calendrier.addColumn("Date");
        calendrier.addColumn("Heure");
        calendrier.addColumn("Prix");
        calendrier.addColumn("Reglement");
        calendrier.addColumn("Indicateur Anxiété");

        JTable table = new JTable(calendrier);
        table.setCellSelectionEnabled(true);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        pack();
        frame.setVisible(true);

        try {
            Connection connect = DriverManager.getConnection(url, user, mdp);
            Statement statement = connect.createStatement();
            String sql = "Select DateConsultation, Heure, Prix, Reglement, IndicateurAnxiete " +
                    "from Consultation c inner join Patientenconsult e on c.NumeroConsultation = e.NumeroConsultation " +
                    "inner join Patient p on e.NumeroDossier = p.NumeroDossier " +
                    "where Email = '" + userField.getText() + "' " +
                    "Order by DateConsultation";
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                calendrier.addRow(new Object[]{result.getString("DateConsultation"), result.getString("Heure"),
                        result.getInt("Prix"), result.getString("Reglement"), result.getInt("IndicateurAnxiete")});
            }
        } catch (SQLException e) {
            System.out.println("Exception is " + e.getMessage());
        }
    }

    public void modifInfo() throws SQLException {
        JFrame frame = new JFrame("Modifier vos informations");
        frame.setSize(400, 480);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);

        JLabel TelephoneLabel = new JLabel("Numéro de téléphone");
        JLabel AdresseLabel = new JLabel("Adresse");
        JLabel UserLabel = new JLabel("Email");
        JLabel MdpLabel = new JLabel("Mot de Passe");
        JLabel ProfessionLabel = new JLabel("Profession actuelle");
        JLabel ClassificationLabel = new JLabel("Classification");

        JTextField TelephoneField = new JTextField(nbTelephone);
        JTextField AdresseField = new JTextField(adresse);
        JTextField EmailField = new JTextField(email);
        JTextField passwordField = new JTextField(password);
        JTextField ProfessionField = new JTextField(profession.getNom());
        JTextField ClassificationField = new JTextField(classification);

        JComboBox<String> histProfession = new JComboBox<>();
        JLabel HistProfessionLabel = new JLabel("Historique des professions");
        histProfession.setSelectedItem(null);
        histProfession.setPreferredSize(new Dimension(100, 20));

        Connection connect = DriverManager.getConnection(url, user, mdp);
        Statement statement = connect.createStatement();
        String sql = "Select Titre from Profession where NumeroDossier = '" + getNbDossier() + "'" +
                " Order by DateProfession";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            histProfession.addItem(resultSet.getString("Titre"));
        }

        JButton valider = new JButton("Valider");
        valider.addActionListener(e -> {
            try {
                Connection connection = DriverManager.getConnection(url, user, mdp);
                Statement statement2 = connection.createStatement();
                String sql2 = "Update Patient " +
                        " set NumeroTel = '" + TelephoneField.getText() + "', Adresse = '" + AdresseField.getText() + "', Email = '" + EmailField.getText() + "', MotDePasse = '" + passwordField.getText() + "', Profession = '" + ProfessionField.getText() + "', Classification = '" + ClassificationField.getText() + "'" +
                        " where NumeroDossier = '" + getNbDossier() + "'";
                statement2.executeUpdate(sql2);
                frame.dispose();
                frame.removeAll();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });

        JPanel panel = new JPanel(new GridLayout(14, 1));
        panel.add(TelephoneLabel);
        panel.add(TelephoneField);

        panel.add(AdresseLabel);
        panel.add(AdresseField);

        panel.add(UserLabel);
        panel.add(EmailField);

        panel.add(MdpLabel);
        panel.add(passwordField);

        panel.add(ProfessionLabel);
        panel.add(ProfessionField);

        panel.add(HistProfessionLabel);
        panel.add(histProfession);

        panel.add(ClassificationLabel);
        panel.add(ClassificationField);

        JPanel panel1 = new JPanel();
        panel1.add(valider);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(panel1, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void setRDV() {
        JFrame frame = new JFrame("Prendre rendez-vous");
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);

        JLabel nbPatient = new JLabel("Nombre de patients");
        JComboBox<Integer> nbPatientBox = new JComboBox<>(new Integer[]{1, 2, 3});
        nbPatientBox.setSelectedItem(null);

        JPanel panel1 = new JPanel(new GridLayout(3, 1));
        JButton valider = new JButton("Valider");
        panel1.add(nbPatient);
        panel1.add(nbPatientBox);
        panel1.add(valider);

        JPanel panel2 = new JPanel();

        JLabel patientNb2 = new JLabel("Second patient");
        JComboBox<String> patient2 = new JComboBox<>();
        patient2.setPreferredSize(new Dimension(150, 20));

        valider.addActionListener(e -> {
            int nbPatientRDV = (int) nbPatientBox.getEditor().getItem();
            switch (nbPatientRDV) {
                case 1:
                    frame.dispose();
                    frame.removeAll();
                    setCalendar(1);
                    break;
                case 2:
                    try {
                        Connection connect = DriverManager.getConnection(url, user, mdp);
                        Statement statement = connect.createStatement();
                        String sql = "select concat(Prenom, ' ', Nom) as Name from Patient" +
                                " where Prenom != '" + prenom + "' and Nom != '" + nom + "'";
                        ResultSet resultSet = statement.executeQuery(sql);
                        while (resultSet.next()) {
                            patient2.addItem(resultSet.getString("Name"));
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }

                    patient2.setSelectedItem(null);
                    JButton validate = new JButton("Valider");
                    validate.addActionListener(e1 -> {
                        String user2 = (String) patient2.getEditor().getItem();
                        String[] tab = user2.split(" ");
                        String prenom2 = tab[0];
                        String nom2 = tab[1];

                        setCalendar(2, prenom2, nom2);
                        frame.removeAll();
                        frame.dispose();
                    });

                    panel2.add(patientNb2);
                    panel2.add(patient2);
                    panel2.add(validate);

                    frame.add(panel2, BorderLayout.CENTER);
                    frame.setVisible(true);
                    break;
                case 3:
                    try {
                        Connection connection = DriverManager.getConnection(url, user, mdp);
                        Statement statement = connection.createStatement();
                        String sql = "select concat(Prenom, ' ', Nom) as Name from Patient" +
                                " where Prenom != '" + prenom + "' and Nom != '" + nom + "'";
                        ResultSet resultSet = statement.executeQuery(sql);
                        while (resultSet.next()) {
                            patient2.addItem(resultSet.getString("Name"));
                        }

                        JPanel panel3 = new JPanel();
                        panel3.add(patientNb2);
                        panel3.add(patient2);

                        JLabel patientNb3 = new JLabel("Troisième patient");
                        JComboBox<String> patient3 = new JComboBox<>();
                        patient3.setSelectedItem(null);
                        patient2.setSelectedItem(null);

                        try {
                            Connection connect2 = DriverManager.getConnection(url, user, mdp);
                            Statement statement1 = connect2.createStatement();
                            String sql2 = "select concat(Prenom, ' ', Nom) as Name from Patient" +
                                    " where Prenom != '" + prenom + "' and Nom != '" + nom + "'";
                            ResultSet resultSet1 = statement1.executeQuery(sql2);
                            while (resultSet1.next()) {
                                patient3.addItem(resultSet1.getString("Name"));
                            }
                        } catch (SQLException exception) {
                            exception.printStackTrace();
                        }
                        panel3.add(patientNb3);
                        panel3.add(patient3);

                        JButton validate2 = new JButton("Valider");
                        validate2.addActionListener(e2 -> {
                            String user2 = (String) patient2.getEditor().getItem();
                            String[] tab = user2.split(" ");
                            String prenom2 = tab[0];
                            String nom2 = tab[1];

                            String user3 = (String) patient3.getEditor().getItem();
                            String[] tab2 = user3.split(" ");
                            String prenom3 = tab2[0];
                            String nom3 = tab2[1];
                            if (patient2.getEditor().getItem().equals(patient3.getEditor().getItem())) {
                                setCalendar(2, prenom2, nom2);
                                frame.removeAll();
                                frame.dispose();
                            } else {
                                setCalendar(3, prenom2, nom2, prenom3, nom3);
                                frame.removeAll();
                                frame.dispose();
                            }
                        });
                        panel3.add(validate2);
                        frame.add(panel3, BorderLayout.CENTER);
                        frame.setVisible(true);

                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                    break;
            }
        });

        frame.add(panel1, BorderLayout.NORTH);
    }

    public void setCalendar(int nbpatient, String... args) {
        JFrame frame = new JFrame("Ajout de rendez-vous");
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { return true; }
        };

        tableModel.addColumn("Date");
        tableModel.addColumn("Heure");
        tableModel.addColumn("Reglement");

        tableModel.addRow(new Object[]{});
        tableModel.addRow(new Object[]{});

        JTable table = new JTable(tableModel);
        table.setCellSelectionEnabled(true);

        JPanel panel = new JPanel();
        JButton val = new JButton("Valider");

        val.addActionListener(e -> {
            if(tableModel.getValueAt(0, 0).equals("") || tableModel.getValueAt(0,1).equals("") || tableModel.getValueAt(0, 2).equals("") || tableModel.getValueAt(0,3).equals(""))
                JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs");
            else {
                String date = tableModel.getValueAt(0, 0).toString(); //Récupération des informations dans des variables locales
                try { date = formatDate(date);
                } catch (ParseException parseException) { parseException.printStackTrace(); }
                String heure = tableModel.getValueAt(0, 1).toString();
                String reglement = tableModel.getValueAt(0, 2).toString();

                Consultation consultation = new Consultation();
                consultation.setDateReglement(date);
                consultation.setHeureReglement(heure);
                consultation.setMoyenReglement(reglement);

                if(heure.equals("00:00:00") || heure.equals("00:30:00") ||
                        heure.equals("01:00:00") || heure.equals("01:30:00") ||
                        heure.equals("02:00:00") || heure.equals("02:30:00") ||
                        heure.equals("03:00:00") || heure.equals("03:30:00") ||
                        heure.equals("04:00:00") || heure.equals("04:30:00") ||
                        heure.equals("05:00:00") || heure.equals("05:30:00") ||
                        heure.equals("06:00:00") || heure.equals("06:30:00") ||
                        heure.equals("07:00:00") || heure.equals("07:30:00") ||

                        heure.equals("12:00:00") || heure.equals("12:30:00") ||
                        heure.equals("13:00:00") || heure.equals("13:30:00") ||

                        heure.equals("20:00:00") || heure.equals("20:30:00") ||
                        heure.equals("21:00:00") || heure.equals("21:30:00") ||
                        heure.equals("22:00:00") || heure.equals("22:30:00") ||
                        heure.equals("23:00:00") || heure.equals("23:30:00"))
                    JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas prendre rendez-vous sur ces heures");
                else{
                    boolean check = false, sunday = false;
                    try{
                        check = checkAvailability(date, heure);
                    } catch (SQLException exception) { exception.printStackTrace(); }
                    if(check){
                        try{
                            Connection connect = DriverManager.getConnection(url, user, mdp);
                            Statement statement = connect.createStatement();
                            String sql = "insert into Consultation (DateConsultation, Heure, Reglement)" +
                                    " values ('" + date + "','" + heure + "', '" + reglement + "')";
                            statement.executeUpdate(sql);

                            int numdossier1, numconsult, numdossier2;
                            switch(nbpatient){
                                case 1:
                                    String sql2 = "select NumeroDossier from Patient where Nom = '" + nom + "' and Prenom = '" + prenom + "'";
                                    ResultSet resultSet1 = statement.executeQuery(sql2);
                                    resultSet1.next();
                                    numdossier1 = resultSet1.getInt("NumeroDossier");

                                    String sql3 = "select NumeroConsultation from Consultation where DateConsultation = '" + date + "' and Heure = '" + heure + "'";
                                    ResultSet resultSet2 = statement.executeQuery(sql3);
                                    resultSet2.next();
                                    numconsult = resultSet2.getInt("NumeroConsultation");

                                    String sql4 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier1 + "', '" + numconsult + "')";
                                    statement.executeUpdate(sql4);
                                    break;
                                case 2:
                                    String sqlPat = "select NumeroDossier from Patient where Nom = '" + nom + "' and Prenom = '" + prenom + "'";
                                    ResultSet resultSetPat = statement.executeQuery(sqlPat);
                                    resultSetPat.next();
                                    numdossier1 = resultSetPat.getInt("NumeroDossier");

                                    String sql5 = "select NumeroDossier from Patient where Prenom = '" + args[0] + "' and Nom = '" + args[1] + "'";
                                    ResultSet resultSet = statement.executeQuery(sql5);
                                    resultSet.next();
                                    numdossier2 = resultSet.getInt("NumeroDossier");

                                    String sql100 = "select NumeroConsultation from Consultation where DateConsultation = '" + date + "' and Heure = '" + heure + "'";
                                    ResultSet resultSet100 = statement.executeQuery(sql100);
                                    resultSet100.next();
                                    numconsult = resultSet100.getInt("NumeroConsultation");

                                    String sql6 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier1 + "', '" + numconsult + "' )";
                                    statement.executeUpdate(sql6);

                                    String sql7 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier2 + "', '" + numconsult + "' )";
                                    statement.executeUpdate(sql7);
                                    break;
                                case 3:
                                    String sqlPat2 = "select NumeroDossier from Patient where Nom = '" + nom + "' and Prenom = '" + prenom + "'";
                                    ResultSet resultSetPat2 = statement.executeQuery(sqlPat2);
                                    resultSetPat2.next();
                                    numdossier1 = resultSetPat2.getInt("NumeroDossier");

                                    String sql8 = "select NumeroDossier from Patient where Prenom = '" + args[0] + "' and Nom = '" + args[1] + "'";
                                    ResultSet resultSet3 = statement.executeQuery(sql8);
                                    resultSet3.next();
                                    numdossier2 = resultSet3.getInt("NumeroDossier");

                                    String sql9 = "select NumeroDossier from Patient where Prenom = '" + args[2] + "' and Nom = '" + args[3] + "'";
                                    ResultSet resultSet4 = statement.executeQuery(sql9);
                                    resultSet4.next();
                                    int numdossier3 = resultSet4.getInt("NumeroDossier");

                                    String sql1000 = "select NumeroConsultation from Consultation where DateConsultation = '" + date + "' and Heure = '" + heure + "'";
                                    ResultSet resultSet1000 = statement.executeQuery(sql1000);
                                    resultSet1000.next();
                                    numconsult = resultSet1000.getInt("NumeroConsultation");

                                    String sql10 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier1 + "', '" + numconsult + "' )";
                                    statement.executeUpdate(sql10);

                                    String sql11 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier2 + "', '" + numconsult + "' )";
                                    statement.executeUpdate(sql11);

                                    String sql12 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier3 + "', '" + numconsult + "' )";
                                    statement.executeUpdate(sql12);
                                    break;
                            }
                            frame.dispose();
                            frame.removeAll();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else{
                        if(!check)
                            JOptionPane.showMessageDialog(frame, "Ce rendez-vous est déjà pris");
                        if(sunday)
                            JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas prendre rendez-vous un dimanche");
                    }
                }

            }
        });

        panel.add(val);

        JPanel disclaimer = new JPanel();
        JLabel label = new JLabel("Les rendez-vous se prennent par créneau de 30 min" +
                " - de 08:00 à 12:00 et de 14:00 à 20:00");
        disclaimer.add(label);

        frame.add(disclaimer, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public boolean checkAvailability(String date, String heure) throws SQLException {
        Connection connect = DriverManager.getConnection(url, user, mdp);
        Statement statement = connect.createStatement();
        String sql = "select DateConsultation, Heure from Consultation Order by DateConsultation ";
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<String> arrayDate = new ArrayList<>();
        ArrayList<String> arrayHeure = new ArrayList<>();
        while(resultSet.next()){
            arrayDate.add(resultSet.getString("DateConsultation"));
            arrayHeure.add(resultSet.getString("Heure"));
        }

        for(int i=0;i<arrayDate.size();i++){
            if(arrayDate.get(i).equals(date) && arrayHeure.get(i).equals(heure)){
                return false;
            }
        }
        return true;
    }

    public String formatDate(String input) throws ParseException {
        boolean isformatted = false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
        simpleDateFormat.setLenient(false);
        try{
            simpleDateFormat.parse(input);
            isformatted = true;
        } catch (ParseException ignored) { }
        if(!isformatted){
            SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            return myFormat.format(fromUser.parse(input));
        } else
            return input;
    }
}
