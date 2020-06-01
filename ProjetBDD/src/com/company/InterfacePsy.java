package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class InterfacePsy extends JFrame {
    private final String URL = "url";
    private final String user = "admin";
    private final String mdp = "password";
    private Consultation consultation;

    public InterfacePsy(){
        JFrame settings = new JFrame("Paramètres");
        settings.setSize(200,400);
        settings.setLocationRelativeTo(null);
        settings.setResizable(false);
        settings.setAlwaysOnTop(true);
        settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JButton newRDV = new JButton("Nouveau rendez-vous");
        JButton seeRDV = new JButton("Voir les rendez-vous");
        JButton changeRDV = new JButton("Modifier un rendez-vous");
        JButton newPatient = new JButton("Créer un nouveau patient");
        JButton fillFiche = new JButton("Remplir la fiche bilan");
        JButton cancelRDV = new JButton("Annuler un rendez-vous");

        JPanel panel1 = new JPanel();
        panel1.add(newRDV);
        panel1.add(seeRDV);
        JPanel panel2 = new JPanel();
        panel2.add(changeRDV);
        panel2.add(newPatient);
        JPanel panel3 = new JPanel();
        panel3.add(fillFiche);
        panel3.add(cancelRDV);

        newRDV.addActionListener(e ->{
            settings.dispose();
            settings.removeAll();
            try {
                NewRdvFrame();
            }catch (SQLException exception) {exception.printStackTrace();}
        });

        seeRDV.addActionListener(e ->{
            settings.dispose();
            settings.removeAll();
            try {
                SeeRdvFrame();
            }catch (SQLException exception) {exception.printStackTrace();}
        });

        changeRDV.addActionListener(e ->{
            settings.dispose();
            settings.removeAll();
            ChangeRdvFrame();
        });

        newPatient.addActionListener(e ->{
            settings.dispose();
            settings.removeAll();
            NewPatientFrame();
        });

        fillFiche.addActionListener(e ->{
            settings.dispose();
            settings.removeAll();
            FillFicheFrame();
        });

        cancelRDV.addActionListener(e ->{
            settings.dispose();
            settings.removeAll();
            cancelRdvFrame();
        });

        settings.add(panel1, BorderLayout.NORTH);
        settings.add(panel2, BorderLayout.CENTER);
        settings.add(panel3, BorderLayout.SOUTH);
        settings.pack();
        settings.setVisible(true);
    }

    public void NewRdvFrame() throws SQLException{
        JFrame addRDV = new JFrame("Nouveau rendez-vous");
        addRDV.setSize(350, 250);
        addRDV.setResizable(false);
        addRDV.setLocationRelativeTo(null);

        JLabel nbPatients = new JLabel("Nombre de patients pour le rendez-vous");
        JComboBox<Integer> nbPa = new JComboBox<>(new Integer[]{1, 2, 3});
        nbPa.setSelectedItem(null);

        JPanel panel1 = new JPanel(new GridLayout(3, 1));
        JButton valider = new JButton("Valider");
        panel1.add(nbPatients);
        panel1.add(nbPa);
        panel1.add(valider);

        JPanel panel2 = new JPanel();

        JLabel patient1 = new JLabel("Premier patient");
        JComboBox<String> patientnb1 = new JComboBox<>();
        patientnb1.setPreferredSize(new Dimension(150, 20));

        JLabel patient2= new JLabel("Second patient");
        JComboBox<String> patientnb2 = new JComboBox<>();
        patientnb2.setPreferredSize(new Dimension(150, 20));

        valider.addActionListener(e -> {
            int nbConsult = (int) patientnb1.getEditor().getItem();
            switch (nbConsult){
                case 1:
                    addRDV.dispose();
                    addRDV.removeAll();
                    JFrame newRDV = new JFrame("Nouveau rendez-vous");
                    newRDV.setLocationRelativeTo(null);
                    newRDV.setSize(300, 180);

                    try {
                        Connection connection = DriverManager.getConnection(URL, user, mdp);
                        Statement statement = connection.createStatement();
                        String sql = "select concat(Prenom, ' ', Nom) as Name from Patient";
                        ResultSet resultSet = statement.executeQuery(sql);
                        while(resultSet.next()){
                            patientnb1.addItem(resultSet.getString("Nom"));
                        }
                        patientnb1.setSelectedItem(null);
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }

                    JPanel panel100 = new JPanel();
                    JLabel label1 = new JLabel("Patient");
                    panel100.add(label1);
                    panel100.add(patientnb1);

                    JPanel panelbtn = new JPanel();
                    JButton button = new JButton("Valider");
                    panelbtn.add(button);

                    newRDV.add(panel100, BorderLayout.CENTER);
                    newRDV.add(panelbtn, BorderLayout.SOUTH);
                    newRDV.setVisible(true);

                    button.addActionListener(al -> {
                        String user = (String) patientnb1.getEditor().getItem();
                        String[] tab = user.split(" ");
                        String prenom = tab[0];
                        String nom = tab[1];
                        CreateConsult(1, prenom, nom);
                    });
                    break;
                case 2:
                    try {
                        Connection connection = DriverManager.getConnection(URL, user, mdp);
                        Statement statement = connection.createStatement();
                        String sql = "select concat(Prenom, ' ', Nom) as Name from Patient";
                        ResultSet resultSet = statement.executeQuery(sql);
                        while(resultSet.next()){
                            patientnb1.addItem(resultSet.getString("Nom"));
                            patientnb2.addItem(resultSet.getString("Nom"));
                        }
                        patientnb1.setSelectedItem(null);
                        patientnb2.setSelectedItem(null);
                        JButton valider2 = new JButton("Valider");
                        valider2.addActionListener(e1 -> {
                            String user1 = (String) patientnb1.getEditor().getItem();
                            String[] tab1 = user1.split(" ");
                            String prenom1 = tab1[0];
                            String nom1 = tab1[1];
                            addRDV.dispose();
                            addRDV.removeAll();

                            String user2 = (String) patientnb2.getEditor().getItem();
                            String[] tab2 = user2.split(" ");
                            String prenom2 = tab2[0];
                            String nom2 = tab2[1];

                            if(patientnb1.getEditor().getItem().equals(patientnb2.getEditor().getItem())){
                                CreateConsult(1, prenom1, nom1);
                                addRDV.removeAll();
                                addRDV.dispose();
                            } else {
                                CreateConsult(2, prenom1, nom1, prenom2, nom2);
                                addRDV.removeAll();
                                addRDV.dispose();
                            }
                        });

                        panel1.add(patient1);
                        panel1.add(patientnb1);

                        panel1.add(patient2);
                        panel1.add(patientnb2);
                        panel1.add(valider2);

                        addRDV.add(panel1, BorderLayout.CENTER);
                        addRDV.setVisible(true);
                        break;

                    } catch (SQLException exception) {exception.printStackTrace();}
                    break;
                case 3:
                    JLabel patient3 = new JLabel("Troisième patient");
                    JComboBox<String> patientnb3 = new JComboBox<>();
                    patientnb3.setSelectedItem(null);

                    try {
                        Connection connection = DriverManager.getConnection(URL, user, mdp);
                        Statement statement = connection.createStatement();
                        String sql = "select concat(Prenom, ' ', Nom) as Name from Patient";
                        ResultSet resultSet = statement.executeQuery(sql);
                        while(resultSet.next()){
                            patientnb1.addItem(resultSet.getString("Name"));
                            patientnb2.addItem(resultSet.getString("Name"));
                            patientnb3.addItem(resultSet.getString("Name"));
                        }
                        patientnb1.setSelectedItem(null);
                        patientnb2.setSelectedItem(null);
                        patientnb3.setSelectedItem(null);

                        JPanel panel3 = new JPanel();
                        panel3.add(patient1);
                        panel3.add(patientnb1);
                        panel3.add(patient2);
                        panel3.add(patientnb2);
                        panel3.add(patient3);
                        panel3.add(patientnb3);

                        JButton valider3 = new JButton("Valider");
                        valider3.addActionListener(e1 -> {
                            String User = (String) patientnb1.getEditor().getItem();
                            String[] tab1 = User.split(" ");
                            String prenom1 = tab1[0];
                            String nom1 = tab1[1];

                            String user2 = (String) patientnb2.getEditor().getItem();
                            String[] tab2 = user2.split(" ");
                            String prenom2 = tab2[0];
                            String nom2 = tab2[1];

                            String user3 = (String) patientnb3.getEditor().getItem();
                            String[] tab3 = user3.split(" ");
                            String prenom3 = tab3[0];
                            String nom3 = tab3[1];

                            if (patientnb1.getEditor().getItem().equals(patientnb2.getEditor().getItem()) || patientnb1.getEditor().getItem().equals(patientnb3.getEditor().getItem())) {
                                CreateConsult(1, prenom1, nom1);
                                addRDV.removeAll();
                                addRDV.dispose();
                            } else if(patientnb1.getEditor().getItem().equals(patientnb2.getEditor().getItem()) || patientnb1.getEditor().getItem().equals(patientnb3.getEditor().getItem()) || patientnb2.getEditor().getItem().equals(patientnb3.getEditor().getItem())){
                                if(patientnb1.getEditor().getItem().equals(patientnb2.getEditor().getItem())){
                                    CreateConsult(2, prenom1, nom1, prenom3, nom3);
                                } else if(patientnb1.getEditor().getItem().equals(patientnb3.getEditor().getItem())){
                                    CreateConsult(2, prenom1, nom1, prenom2, nom2);
                                } else {
                                    CreateConsult(2, prenom1, nom1, prenom3, nom3);
                                }
                                addRDV.removeAll();
                                addRDV.dispose();
                            } else {
                                CreateConsult(3, prenom1, nom1, prenom2, nom2, prenom3, nom3);
                                addRDV.removeAll();
                                addRDV.dispose();
                            }
                        });
                        panel3.add(valider3);
                        addRDV.add(panel3, BorderLayout.CENTER);
                        addRDV.setVisible(true);
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                    break;
            }
        });

        addRDV.add(panel1, BorderLayout.NORTH);
        addRDV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addRDV.setVisible(true);

        addRDV.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
    }

    public void CreateConsult(int nbPatients, String... args){
        JFrame consult = new JFrame("Nouveau rendez-vous");
        consult.setSize(350,450);
        consult.setLocationRelativeTo(null);
        consult.setResizable(false);

        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { return true; }
        };

        tableModel.addColumn("Date");
        tableModel.addColumn("Heure");
        tableModel.addColumn("Prix");
        tableModel.addColumn("Reglement");

        tableModel.addRow(new Object[]{});
        tableModel.addRow(new Object[]{});

        JTable table = new JTable(tableModel);
        table.setCellSelectionEnabled(true);

        JPanel panel = new JPanel();
        JButton val = new JButton("Valider");
        val.addActionListener(e -> {
            if(tableModel.getValueAt(0, 0).equals("") || tableModel.getValueAt(0,1).equals("") || tableModel.getValueAt(0, 2).equals("") || tableModel.getValueAt(0,3).equals(""))
                JOptionPane.showMessageDialog(consult, "Veuillez remplir tous les champs");
            else {
                String date = tableModel.getValueAt(0, 0).toString(); //Récupération des informations dans des variables locales
                try { date = formatDate(date);
                } catch (ParseException parseException) { parseException.printStackTrace(); }
                String heure = tableModel.getValueAt(0, 1).toString();
                int prix = Integer.parseInt(tableModel.getValueAt(0, 2).toString());
                String reglement = tableModel.getValueAt(0, 3).toString();
                new Consultation(date, heure, prix, reglement);

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
                    JOptionPane.showMessageDialog(consult, "Vous ne pouvez pas prendre rendez-vous sur vos heures non travaillées");
                else {
                    boolean check = false, sunday = false;
                    try {
                        check = checkFree(date, heure);
                    } catch (SQLException exception) { exception.printStackTrace(); }
                    if(check){
                        try {
                            Connection connect = DriverManager.getConnection(URL, user, mdp);
                            Statement statement = connect.createStatement();
                            String sql = "insert into Consultation (DateConsultation, Heure, Prix, Reglement)" +
                                    " values ('" + date + "','" + heure + "', '" + prix + "', '" + reglement + "')";
                            statement.executeUpdate(sql);

                            int numdossier1, numconsult, numdossier2;
                            switch(nbPatients){
                                case 1:
                                    String sql2 = "select NumeroDossier from Patient where Prenom = '" + args[0] + "' and Nom = '" + args[1] + "'";
                                    ResultSet resultSet = statement.executeQuery(sql2);
                                    resultSet.next();
                                    numdossier1 = resultSet.getInt("NumeroDossier");

                                    String sql3 = "select NumeroConsultation from Consultation where DateConsultation = '" + date + "' and Heure = '" + heure + "'";
                                    ResultSet resultSet2 = statement.executeQuery(sql3);
                                    resultSet2.next();
                                    numconsult = resultSet2.getInt("NumeroConsultation");

                                    String sql4 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier1 + "', '" + numconsult + "')";
                                    statement.executeUpdate(sql4);
                                    break;
                                case 2:
                                    String sql5 = "select NumeroDossier from Patient where Prenom = '" + args[0] + "' and Nom = '" + args[1] + "'";
                                    ResultSet resultSet3 = statement.executeQuery(sql5);
                                    resultSet3.next();
                                    numdossier1 = resultSet3.getInt("NumeroDossier");

                                    String sql6 = "select NumeroDossier from Patient where Prenom = '" + args[2] + "' and Nom = '" + args[3] + "'";
                                    ResultSet resultSet4 = statement.executeQuery(sql6);
                                    resultSet4.next();
                                    numdossier2 = resultSet4.getInt("NumeroDossier");

                                    String sql7 = "select NumeroConsultation from Consultation where DateConsultation = '" + date + "' and Heure = '" + heure + "'";
                                    ResultSet resultSet5 = statement.executeQuery(sql7);
                                    resultSet5.next();
                                    numconsult = resultSet5.getInt("NumeroConsultation");

                                    String sql8 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier1 + "', '" + numconsult + "' )";
                                    statement.executeUpdate(sql8);

                                    String sql9 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier2 + "', '" + numconsult + "' )";
                                    statement.executeUpdate(sql9);
                                    break;
                                case 3:
                                    String sql10 = "select NumeroDossier from Patient where Prenom = '" + args[0] + "' and Nom = '" + args[1] + "'";
                                    ResultSet resultSet6 = statement.executeQuery(sql10);
                                    resultSet6.next();
                                    numdossier1 = resultSet6.getInt("NumeroDossier");

                                    String sql11 = "select NumeroDossier from Patient where Prenom = '" + args[2] + "' and Nom = '" + args[3] + "'";
                                    ResultSet resultSet7 = statement.executeQuery(sql11);
                                    resultSet7.next();
                                    numdossier2 = resultSet7.getInt("NumeroDossier");

                                    String sql12 = "select NumeroDossier from Patient where Prenom = '" + args[4] + "' and Nom = '" + args[5] + "'";
                                    ResultSet resultSet8 = statement.executeQuery(sql12);
                                    resultSet8.next();
                                    int numdossier3 = resultSet8.getInt("NumeroDossier");

                                    String sql13 = "select NumeroConsultation from Consultation where DateConsultation = '" + date + "' and Heure = '" + heure + "'";
                                    ResultSet resultSet9 = statement.executeQuery(sql13);
                                    resultSet9.next();
                                    numconsult = resultSet9.getInt("NumeroConsultation");

                                    String sql14 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier1 + "', '" + numconsult + "' )";
                                    statement.executeUpdate(sql14);

                                    String sql15 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier2 + "', '" + numconsult + "' )";
                                    statement.executeUpdate(sql15);

                                    String sql16 = "insert into patientenconsult (NumeroDossier, NumeroConsultation) values ('" + numdossier3 + "', '" + numconsult + "' )";
                                    statement.executeUpdate(sql16);
                                    break;
                            }

                            consult.dispose();
                            consult.removeAll();
                            System.exit(0);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }else{
                        if(!check)
                            JOptionPane.showMessageDialog(consult, "Ce rendez-vous est déjà pris");
                    }
                }
            }
        });

        panel.add(val);

        JPanel disclaimer = new JPanel();
        JLabel label = new JLabel("Les rendez-vous se prennent par créneau de 30 min" +
                " - de 08:00 à 12:00 et de 14:00 à 20:00");
        disclaimer.add(label);

        consult.add(disclaimer, BorderLayout.NORTH);

        consult.add(new JScrollPane(table), BorderLayout.CENTER); //Ajout des éléments sur la fenêtre
        consult.add(panel, BorderLayout.SOUTH);

        consult.pack();
        consult.setVisible(true);
    }

    public void SeeRdvFrame() throws SQLException{
        JFrame see = new JFrame("Voir les rendez-vous");
        see.setSize(300, 200);
        see.setLocationRelativeTo(null);

        JLabel patient = new JLabel("Selectionner le patient");
        JComboBox<String> list = new JComboBox<>();
        list.setPreferredSize(new Dimension(100,50));
        list.setSelectedItem(null);

        Connection connect = DriverManager.getConnection(URL, user, mdp);
        Statement statement = connect.createStatement();
        String sql = "SELECT CONCAT(Prenom, ' ', Nom) as Name from Patient";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            list.addItem(resultSet.getString("Nom"));
        }

        JButton valider = new JButton("Valider");
        valider.addActionListener(e->{
            String user = (String) list.getEditor().getItem();
            String [] tabPatients = user.split(" ");
            String prenom = tabPatients[0];
            String nom = tabPatients[1];
            displayRDV(prenom, nom);
        });

        JPanel center = new JPanel();
        center.add(patient);
        center.add(list);

        JPanel south = new JPanel();
        south.add(valider);

        JPanel all = new JPanel();
        all.setLayout(new BorderLayout());
        all.add(center, BorderLayout.CENTER);
        all.add(south, BorderLayout.SOUTH);

        see.setContentPane(all);
        see.setVisible(true);
    }

    public void displayRDV(String prenom, String nom){
        JFrame rdv = new JFrame();
        rdv.setTitle("Vos rendez-vous");
        rdv.setSize(800,600);
        rdv.setLocationRelativeTo(null);
        rdv.setResizable(false);

        DefaultTableModel cal = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cal.addColumn("Date");
        cal.addColumn("Heure");
        cal.addColumn("Prix");
        cal.addColumn("Type de reglement");
        cal.addColumn("Effectué");
        cal.addColumn("Indicateur d'anxiété");

        JTable calendrier = new JTable(cal);
        calendrier.setCellSelectionEnabled(true);
        rdv.add(new JScrollPane(calendrier), BorderLayout.CENTER);
        rdv.setVisible(true);

        try {
            Connection connect = DriverManager.getConnection(URL, user, mdp);
            Statement statement = connect.createStatement();
            if(statement == null){
                throw new SQLException("La connexion n'a pu être établie");
            } else {
                String sql = "select DateConsultation, Heure, Prix, Reglement, Effectue, IndicateurAnxiete" +
                        " from Patient p inner join patientenconsult e on p.NumeroDossier = e.NumeroDossier" +
                        " inner join consultation c on e.NumeroConsultation = c.NumeroConsultation" +
                        " where Prenom = '"+ prenom + "' and Nom = '"+ nom + "' Order by DateConsultation";
                ResultSet result = statement.executeQuery(sql);
                while(result.next()){
                    cal.addRow(new Object[]{ result.getString("DateConsultation"), result.getString("Heure"),
                            result.getInt("Prix"), result.getString("Reglement"), result.getString("Effectue")});
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception is " + e.getMessage());
        }
    }

    public void ChangeRdvFrame(){
        JFrame modif = new JFrame("Changer un rendez-vous");
        modif.setSize(350, 200);
        modif.setLocationRelativeTo(null);
        modif.setResizable(false);

        JComboBox<String> modifier = new JComboBox<>();
        modifier.setPreferredSize(new Dimension(150, 50));
        modifier.setSelectedItem(null);
        try {
            Connection connect = DriverManager.getConnection(URL, user, mdp);
            Statement statement = connect.createStatement();
            String sql = "SELECT CONCAT(Prenom, ' ', Nom) as Name from Patient";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){modifier.addItem(resultSet.getString("Name"));}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JButton valider = new JButton("Valider");
        valider.addActionListener(e->{
            String user = (String) modifier.getEditor().getItem();
            String[] tab = user.split(" ");
            String prenom = tab[0];
            String nom = tab[1];
            modifRDV(prenom, nom);
        });

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel2.add(modifier);
        panel1.add(valider);
        modif.add(panel2, BorderLayout.CENTER);
        modif.add(panel1, BorderLayout.SOUTH);
        modif.setVisible(true);
    }

    public void modifRDV(String prenom, String nom){
        JFrame modif = new JFrame("Changer le rendez-vous");
        modif.setSize(300, 150);
        modif.setLocationRelativeTo(null);
        modif.setResizable(false);

        JComboBox<String> modifier = new JComboBox<>();
        modifier.setPreferredSize(new Dimension(150,50));
        modifier.setSelectedItem(null);
        try {
            Connection connect = DriverManager.getConnection(URL, user, mdp);
            Statement statement = connect.createStatement();
            String sql = "Select concat(DateConsultation, ' ', Heure) as Rdv from Consultation c" +
                    " inner join patientenconsult e on c.NumeroConsultation = e.NumeroConsultation" +
                    " inner join patient p on p.NumeroDossier = e.NumeroDossier" +
                    " where Nom = '"+ nom + "' and Prenom = '" + prenom + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                modifier.addItem(resultSet.getString("Rdv"));
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }

        JButton valider = new JButton("Valider");
        valider.addActionListener(e->{
            String rdv = (String) modifier.getEditor().getItem();
            String[] tab = rdv.split(" ");
            String date = tab[0];
            String heure = tab[1];
            doChange(date, heure);
        });

        JPanel panel1 = new JPanel();
        panel1.add(modifier);
        JPanel panel2 = new JPanel();
        panel2.add(valider);
        modif.add(panel1, BorderLayout.CENTER);
        modif.add(panel2, BorderLayout.SOUTH);
        modif.setVisible(true);
    }

    public void doChange(String date, String heure){
        JFrame change = new JFrame("Change le rendez-vous");
        change.setSize(300, 400);
        change.setLocationRelativeTo(null);
        change.setResizable(false);

        DefaultTableModel defaultTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return true;
            }
        };

        defaultTableModel.addColumn("Date");
        defaultTableModel.addColumn("Heure");
        defaultTableModel.addColumn("Prix");
        defaultTableModel.addColumn("Reglement");
        defaultTableModel.addColumn("Effectué");

        JTable table = new JTable(defaultTableModel);
        table.setCellSelectionEnabled(true);

        try {
            Connection connect = DriverManager.getConnection(URL, user, mdp);
            Statement statement = connect.createStatement();
            String sql = "select DateConsultation, Heure, Prix, Reglement" +
                    " from Consultation where DateConsultation = '" + date + "' and Heure = '" + heure + "'";
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                defaultTableModel.addRow(new Object[]{ result.getString("DateConsultation"), result.getString("Heure"),
                        result.getInt("Prix"), result.getString("Reglement"), result.getString("Effectué")});
            }
            defaultTableModel.addRow(new Object[]{});
        }catch (SQLException exception){
            exception.printStackTrace();
        }

        JPanel panel = new JPanel();
        JButton valider = new JButton("Valider");
        panel.add(valider);
        valider.addActionListener(e->{
            String Date = defaultTableModel.getValueAt(0, 0).toString();
            try { Date = formatDate(Date);
            } catch (ParseException parseException) { parseException.printStackTrace(); }
            String Heure = defaultTableModel.getValueAt(0, 1).toString();
            int prix = Integer.parseInt(defaultTableModel.getValueAt(0, 2).toString());
            String reglement = defaultTableModel.getValueAt(0, 3).toString();
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
                JOptionPane.showMessageDialog(change, "Impossible de prendre un rendez-vous à cette heure-ci.");
            else{
                boolean check = false;
                try {
                    check = checkFree(Date, Heure);
                } catch (SQLException exception) { exception.printStackTrace(); }
                if(check){
                    try{
                        Connection connect = DriverManager.getConnection(URL, user, mdp);
                        Statement statement = connect.createStatement();
                        String sql = "update consultation " +
                                "set DateConsultation = '" + Date + "', Heure = '" + Heure + "', Prix = '" + prix + "', Reglement = '" + reglement + "' where" +
                                " DateConsultation = '" + date + "' and Heure = '" + heure + "'";
                        statement.executeUpdate(sql);
                        JOptionPane.showMessageDialog(change, "Changements enregistrés");
                        change.dispose();
                        change.removeAll();
                        System.exit(0);
                    }catch(SQLException exception){
                        exception.printStackTrace();
                    }
                }else{
                    if(!check)
                        JOptionPane.showMessageDialog(change,"Cette plage horaire est déjà prise.");
                }
            }
        });

        change.add(new JScrollPane(table), BorderLayout.CENTER);
        change.add(panel, BorderLayout.SOUTH);
        change.setVisible(true);
    }

    public boolean checkFree(String date, String heure) throws SQLException {
        Connection connect = DriverManager.getConnection(URL, user, mdp);
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


    public void NewPatientFrame(){
        JFrame create = new JFrame("Nouveau patient");
        create.setSize(400, 500);
        create.setLocationRelativeTo(null);

        JLabel nom = new JLabel("Nom");
        JLabel prenom = new JLabel("Prenom");
        JLabel moyenDeConnaissance = new JLabel("Moyen de connaissance");
        JLabel adresse = new JLabel("Adresse");
        JLabel sexe = new JLabel("Sexe");
        JLabel dateNaissance = new JLabel("Date de Naissance (YYYY-MM-DD)");
        JLabel profession = new JLabel("Profession");
        JLabel classification = new JLabel("Classification (Homme, Femme, Adolescent, Enfant)");
        JLabel nbTelephone = new JLabel("Numéro de téléphone");

        JTextField NomField = new JTextField(15);
        JTextField PrenomField = new JTextField(15);
        JTextField moyenDeConnaissanceField = new JTextField(15);
        JTextField AdresseField = new JTextField(15);
        JTextField SexeField = new JTextField(15);
        JTextField DateNaissanceField = new JTextField(15);
        JTextField ProfessionField = new JTextField(15);
        JTextField ClassificationField = new JTextField(15);
        JTextField nbTelephoneField = new JTextField(15);

        JButton validerPatient = new JButton("Valider le patient");
        validerPatient.addActionListener(e->{
            if(NomField.getText().equals("") || PrenomField.getText().equals("") ||
                    moyenDeConnaissanceField.getText().equals("") || AdresseField.getText().equals("") ||
                    SexeField.getText().equals("") || DateNaissanceField.getText().equals("") ||
                    DateNaissanceField.getText().equals("") || ProfessionField.getText().equals("") ||
                    ClassificationField.getText().equals("") || nbTelephoneField.getText().equals("")) {
                JOptionPane.showMessageDialog(create, "Vous n'avez pas rempli toutes les informations");
            } else{
                try{
                    Connection connect = DriverManager.getConnection(URL, user, mdp);
                    Statement statement = connect.createStatement();
                    DateNaissanceField.setText(formatDate(DateNaissanceField.getText()));
                    String sql = "insert into Patient (Nom, Prenom, DateDeNaissance, Sexe, Adresse, MoyenConnaissance, Classification, Profession)" +
                            " values ('" + NomField.getText() + "','" + PrenomField.getText() + "','" + DateNaissanceField.getText() + "','" + SexeField.getText() + "','" + AdresseField.getText() + "','" + moyenDeConnaissanceField.getText() + "', '" + ClassificationField.getText() + "', '" + ProfessionField.getText() + "')";
                    statement.executeUpdate(sql);
                    String sql2 = "select NumeroDossier from Patient where Nom = '" + NomField.getText() + "' and Prenom = '" + PrenomField.getText() + "'";
                    ResultSet resultSet = statement.executeQuery(sql2);
                    resultSet.next();
                    int num = resultSet.getInt("NumeroDossier");
                    String sql3 = "insert into Profession (Titre, NumeroDossier) values ('"+ ProfessionField.getText() + "','" + num + "')";
                    statement.executeUpdate(sql3);

                    new Patient(NomField.getText(), PrenomField.getText(), moyenDeConnaissanceField.getText(),
                            AdresseField.getText(), DateNaissanceField.getText(), ClassificationField.getText(),
                            SexeField.getText().charAt(0), Integer.parseInt(nbTelephoneField.getText()));

                    create.dispose();
                    create.removeAll();

                    System.exit(0);
                } catch (SQLException | ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(18, 1));

        panel.add(nom);
        panel.add(NomField);
        panel.add(prenom);
        panel.add(PrenomField);
        panel.add(moyenDeConnaissance);
        panel.add(moyenDeConnaissanceField);
        panel.add(sexe);
        panel.add(SexeField);
        panel.add(adresse);
        panel.add(AdresseField);
        panel.add(dateNaissance);
        panel.add(DateNaissanceField);
        panel.add(profession);
        panel.add(ProfessionField);
        panel.add(classification);
        panel.add(ClassificationField);
        panel.add(nbTelephone);
        panel.add(nbTelephoneField);

        JPanel panel2 = new JPanel();
        panel2.add(validerPatient);

        create.add(panel, BorderLayout.CENTER);
        create.add(panel2, BorderLayout.SOUTH);

        create.setVisible(true);
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

    public void FillFicheFrame(){
        JFrame fiche = new JFrame();
        fiche.setTitle("Selectionner le patient");
        fiche.setLocationRelativeTo(null);
        fiche.setSize(new Dimension(300, 150));
        fiche.setResizable(true);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(100, 50));
        comboBox.setSelectedItem(null);
        try{
            Connection connect = DriverManager.getConnection(URL, user, mdp);
            Statement statement = connect.createStatement();
            String sql = "Select concat(Prenom, ' ', Nom) as Name from Patient";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                comboBox.addItem(resultSet.getString("Name"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        JButton valider = new JButton("Valider");
        valider.addActionListener(e->{
            String user = (String) comboBox.getEditor().getItem();
            String[] tab = user.split(" ");
            String prenom = tab[0];
            String nom = tab[1];
            checkPatient(prenom, nom);
            dispose();
            removeAll();
        });

        JPanel centre = new JPanel();
        centre.add(comboBox);
        JPanel south = new JPanel();
        south.add(valider);

        fiche.add(centre, BorderLayout.CENTER);
        fiche.add(south, BorderLayout.SOUTH);
        fiche.setVisible(true);
    }

    public void checkPatient(String prenom, String nom){
        JFrame check = new JFrame("Selectionner la consultation");
        check.setLocationRelativeTo(null);
        check.setSize(new Dimension(300, 150));
        check.setResizable(true);

        JComboBox<String> consultations = new JComboBox<>();
        consultations.setPreferredSize(new Dimension(100, 50));
        consultations.setSelectedItem(null);
        try{
            Connection connect = DriverManager.getConnection(URL, user, mdp);
            Statement statement = connect.createStatement();
            String sql = "select concat (DateConsultation, ' ', Heure) as Consult from Consultation c" +
                    " inner join patientenconsult e on c.NumeroConsultation = e.NumeroConsultation" +
                    " inner join patient p on e.NumeroDossier = p.NumeroDossier" +
                    " where Prenom = '"+prenom+ "' and Nom = '"+nom+ "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                consultations.addItem(resultSet.getString("Consultation"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        JPanel panel1 = new JPanel();
        panel1.add(consultations);

        JPanel panel2 = new JPanel();
        JButton valider = new JButton("Valider");
        panel2.add(valider);
        valider.addActionListener(e->{
            check.dispose();
            check.removeAll();
            String consulter = (String) consultations.getEditor().getItem();
            String[] tab = consulter.split(" ");
            String date = tab[0];
            String heure = tab[1];
            int num = 0;
            try{
                Connection connect = DriverManager.getConnection(URL, user, mdp);
                Statement statement = connect.createStatement();
                String sql = "select NumeroConsultation from Consultation where DateConsultation = '"+ date + "' and Heure = '" + heure + "'";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                num = resultSet.getInt("Numéro de consultation");
            }catch(Exception exception){
                exception.printStackTrace();
            }
            doFiche(num);
        });

        check.add(panel1, BorderLayout.CENTER);
        check.add(panel2, BorderLayout.SOUTH);
        check.setVisible(true);
    }

    public void doFiche(int nbConsultation){
        JFrame fichePatient = new JFrame("Fiche du patient");
        fichePatient.setLocationRelativeTo(null);
        fichePatient.setSize(new Dimension(300,200));
        fichePatient.setResizable(false);

        JLabel motCle = new JLabel("Mot clé");
        JLabel posture = new JLabel("Posture");
        JLabel comportement = new JLabel("Comportement");
        JLabel indicateurAnxiete = new JLabel("Indicateur d'anxiété");

        JTextField motCleField = new JTextField(15);
        JTextField postureField = new JTextField(15);
        JTextField comportementField = new JTextField(15);
        JTextField indicateurAnxieteField = new JTextField(15);

        JButton valider = new JButton("Valider");
        valider.addActionListener(e->{
            if (motCleField.getText().equals("") || postureField.getText().equals("") || indicateurAnxieteField.getText().equals("")){
                JOptionPane.showMessageDialog(fichePatient, "Veuillez remplir tous les champs");
            }
            else{
                try{
                    Connection connect = DriverManager.getConnection(URL, user, mdp);
                    Statement statement = connect.createStatement();
                    String sql = "update BilanConsultation " +
                            "set MotCles = '" + motCleField.getText() + "', " +
                            "Posture = '" + postureField.getText() + "', " +
                            "Comportement = '" + comportementField.getText() + "' " +
                            "where NumeroConsultation = '"+ nbConsultation + "'";
                    statement.executeUpdate(sql);
                    new BilanConsultation (motCleField.getText(), postureField.getText(), comportementField.getText(), nbConsultation);
                    String sql2 = "update Consultation" +
                            " set IndicateurAnxiete = '" + indicateurAnxieteField.getText() + "'" +
                            " where NumeroConsultation = '" + nbConsultation + "'";
                    statement.executeUpdate(sql2);
                    fichePatient.dispose();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

        JPanel panel1 = new JPanel(new GridLayout(8, 1));
        panel1.add(motCle);
        panel1.add(motCleField);
        panel1.add(posture);
        panel1.add(postureField);
        panel1.add(comportement);
        panel1.add(comportementField);
        panel1.add(indicateurAnxiete);
        panel1.add(indicateurAnxieteField);

        JPanel panel2 = new JPanel();
        panel2.add(valider);

        fichePatient.add(panel1, BorderLayout.CENTER);
        fichePatient.add(panel2, BorderLayout.SOUTH);
        fichePatient.setVisible(true);
    }

    public void cancelRdvFrame(){
        JFrame patient = new JFrame("Selectionner le patient");
        patient.setSize(300, 150);
        patient.setLocationRelativeTo(null);
        patient.setResizable(false);

        JComboBox<String> selection = new JComboBox<>();
        selection.setPreferredSize(new Dimension(150,50));
        selection.setSelectedItem(null);
        try{
            Connection connect = DriverManager.getConnection(URL, user, mdp);
            Statement statement = connect.createStatement();
            String sql = "Select concat(Prenom, ' ', Nom) as Name from Patient";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                selection.addItem(resultSet.getString("Name"));
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }

        JButton valider = new JButton("Valider");
        valider.addActionListener(e->{
            String user = (String) selection.getEditor().getItem();
            String[] tab = user.split(" ");
            String prenom = tab[0];
            String nom = tab [1];
            patient.dispose();
            patient.removeAll();
            cancel(prenom, nom);
        });

        JPanel panel1 = new JPanel();
        panel1.add(selection);
        JPanel panel2 = new JPanel();
        panel2.add(valider);

        patient.setVisible(true);
    }

    public void cancel(String prenom, String nom){
        JFrame cancelRDV = new JFrame("Annuler le rendez-vous");
        cancelRDV.setSize(300,150);
        cancelRDV.setLocationRelativeTo(null);
        cancelRDV.setResizable(false);

        JComboBox<String> rdvBox = new JComboBox<>();
        rdvBox.setPreferredSize(new Dimension(150, 50));
        rdvBox.setSelectedItem(null);
        try{
            Connection connect = DriverManager.getConnection(URL, user, mdp);
            Statement statement = connect.createStatement();
            String sql = "Select concat(DateConsultation, ' ', Heure) as Rdv from Consultation c" +
                    " inner join patientenconsult e on c.NumeroConsultation = e.NumeroConsultation" +
                    " inner join patient p on p.NumeroDossier = e.NumeroDossier" +
                    " where Nom = '"+ nom + "' and Prenom = '" + prenom + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                rdvBox.addItem(resultSet.getString("Rdv"));
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }

        JButton valider = new JButton("Valider");
        valider.addActionListener(e->{
            String rdv = (String) rdvBox.getEditor().getItem();
            String[] tab = rdv.split(" ");
            String date = tab[0];
            String heure = tab[1];
            try{
                Connection connect = DriverManager.getConnection(URL, user, mdp);
                Statement statement = connect.createStatement();
                String sql = "select NumeroConsultation from Consultation" +
                        " where DateConsultation = '"+ date + "' and Heure = '"+ heure + "'";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                int num = resultSet.getInt("NumeroConsultation");
                String sql2 = "delete from patientenconsult where NumeroConsultation = '" + num + "'";
                statement.executeUpdate(sql2);
                String sql3 = "delete from Consultation where NumeroConsultation = '" + num + "'";
                statement.executeUpdate(sql3);
                cancelRDV.dispose();
                cancelRDV.removeAll();
                System.exit(0);
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        });

        JPanel panel1 = new JPanel();
        panel1.add(rdvBox);
        JPanel panel2 = new JPanel();
        panel2.add(valider);
        cancelRDV.add(panel1, BorderLayout.CENTER);
        cancelRDV.add(panel2, BorderLayout.SOUTH);
        cancelRDV.setVisible(true);
    }
}
